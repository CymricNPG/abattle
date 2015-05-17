package net.npg.abattle.communication.command.impl

import net.npg.abattle.communication.command.CommandProcessor
import net.npg.abattle.communication.command.CommandType
import com.google.common.base.Optional

@Data package class CommandProcessorStore {
	CommandProcessor<?> processor
	Optional<Class<?>> processedClass
	CommandType processedType
}