package net.npg.abattle.communication.command.impl

import com.google.common.base.Optional
import java.util.HashSet
import java.util.List
import java.util.Set
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.utils.MyRunnable
import net.npg.abattle.common.utils.SingleList
import net.npg.abattle.common.utils.Validate
import net.npg.abattle.common.utils.impl.DisposeableImpl
import net.npg.abattle.communication.CommunicationConstants
import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.CommandQueueClient
import net.npg.abattle.communication.command.CommandQueueServer
import net.npg.abattle.communication.command.CommandType
import net.npg.abattle.communication.command.DeadCommand
import net.npg.abattle.communication.command.ErrorCommand
import net.npg.abattle.communication.command.GameCommand
import net.npg.abattle.communication.command.commands.DeadCommandImpl
import net.npg.abattle.communication.command.commands.OriginatedPlayerCommand

import static net.npg.abattle.common.utils.Controls.*

class CommandQueueImpl extends DisposeableImpl implements CommandQueueClient, CommandQueueServer {

	/** The Constant MAX_CAPACITY. */
	public static final int MAX_CAPACITY = 256;
	private final BlockingQueue<CommandStore> queue
	private final Set<CommandProcessorStore> processors
	private final CommandType localType
	private volatile boolean running = true

	private CommandQueueRunner runner

	Optional<CommandProcessor<DeadCommand>> deadConnectionProcessor

	/**
 * localType, deadConnectionProcessor can be null
 */
	new(CommandType localType, CommandProcessor<DeadCommand> deadConnectionProcessor) {
		this.deadConnectionProcessor = Optional.fromNullable(deadConnectionProcessor)
		queue = new LinkedBlockingQueue<CommandStore>(MAX_CAPACITY)
		processors = new HashSet<CommandProcessorStore>
		this.localType = localType
		this.runner = new CommandQueueRunner(this);
		new Thread(this.runner).start
	}

	override void addCommand(GameCommand command, CommandType commandType, List<Integer> destination) {
		Validate.notNulls(command, commandType);
		if(CommunicationConstants.DEBUG_ENABLED) {
			CommunicationConstants.LOG.debug("[" + localType + "] Command added:" + command.class.simpleName + " Destination:" + destination.toString)
		}
		addCommand(new CommandStore(commandType, command, destination))
	}

	override <T extends GameCommand> registerCommandProcessor(CommandProcessor<T> processor, Optional<Class<T>> processedClass, CommandType processedType) {
		Validate.notNulls(processor, processedClass, processedType)
		processors.add(new CommandProcessorStore(processor, processedClass as Optional, processedType))
	}

	override void dispose() {
		super.dispose
		runner.stop
		processors.clear
	}

	override void pause() {
		running = false
	}

	override void resume() {
		running = true
	}

	override getHandledType() {
		if(localType == null) {
			throw new IllegalArgumentException("Not available on single player mode.");
		}
		localType
	}

	private def void addCommand(CommandStore command) {
		Validate.notNulls(command, queue);
		if(isQueueFull()) {
			drainQueue()
		}
		if(!queue.offer(command)) {
			handleDeadConsumer
		}
	}

	synchronized private def void drainQueue() {
		val iterator = queue.iterator
		while(iterator.hasNext) {
			val command = iterator.next
			if(command.getCommand().isDropable()) {
				iterator.remove();
			}
		}
		if(isQueueFull()) {
			handleDeadConsumer
		}
	}

	private def void handleDeadConsumer() {
		throw new RuntimeException("Queue overrun, shutdown system.");
	}

	private def boolean isQueueFull() {
		return queue.remainingCapacity() == 0;
	}

	synchronized private def void processCommands() {
		returnif(!running)
		while(!queue.empty) {
			processCommand(queue.take)
		}
	}

	override flush() {
		processCommands()
	}

	private def void processCommand(CommandStore commandStore) {
		val processor = processors.findFirst[isMatchingProcessor(commandStore, it)]
		if(processor == null) {
			throw new IllegalArgumentException("Cannot find processor for:" + commandStore.command.class + " and " + commandStore.commandType.toString)
		}
		if(commandStore.destination.empty) {
			processCommand(commandStore.command, 0,  (processor.processor as CommandProcessor<GameCommand>))
		} else {
			for (Integer destination : commandStore.destination) {
				processCommand(commandStore.command, destination,  (processor.processor as CommandProcessor<GameCommand>))
			}
		}
	}

	private def processCommand(GameCommand command, Integer destination,  CommandProcessor<GameCommand> processor) {

		try {
			val returnValue = processor.execute(command, destination)
			processReturnValue(returnValue,  command)
		} catch(Exception e) {
			CommunicationConstants.LOG.error("Error executing command:" + command, e)
			val deadCommand = new DeadCommandImpl(e.message, false, command.game)
			if(deadConnectionProcessor.present) {
				deadConnectionProcessor.get.execute(deadCommand, destination)
			}
			throw e
		}
	}

	private def processReturnValue(Optional<ErrorCommand> value, GameCommand sourceCommand) {
		if(value.isPresent()) {
			val destination = if(sourceCommand instanceof OriginatedPlayerCommand) {
					(sourceCommand as OriginatedPlayerCommand).originatedPlayer
				} else {
					0
				}
			addCommand(new CommandStore(CommandType.TOCLIENT, value.get as GameCommand, SingleList.create(destination)))
		}
	}

	private def matchesType(CommandStore commandStore, CommandProcessorStore processor) {
		processor.processedType.equals(commandStore.commandType)
	}

	private def matchesClass(CommandStore commandStore, CommandProcessorStore processor) {
		!processor.processedClass.present || processor.processedClass.get.isAssignableFrom(commandStore.command.class)
	}

	private def isMatchingProcessor(CommandStore commandStore, CommandProcessorStore processor) {
		return matchesType(commandStore, processor) && matchesClass(commandStore, processor)
	}

	private static class CommandQueueRunner extends MyRunnable {

		private long fps

		private CommandQueueImpl queue

		new(CommandQueueImpl queue) {
			super("Runner:"+queue.class.canonicalName)
			Validate.notNull(queue)
			this.fps = ComponentLookup.getInstance().getComponent(ConfigurationComponent).gameLoopConfiguration.processCommandsPerSecond
			this.queue = queue
		}

		override execute() throws Exception {
			while(!isStopped) {
				queue.processCommands()
				Thread.sleep(1000L / fps);
				lifeCycleWait
			}
		}
	}
}
