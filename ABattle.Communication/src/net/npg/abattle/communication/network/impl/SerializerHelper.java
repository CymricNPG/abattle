/**
 *
 */
package net.npg.abattle.communication.network.impl;

import net.npg.abattle.communication.command.commands.BoardUpdateCommand;
import net.npg.abattle.communication.command.commands.ChangeLinkCommand;
import net.npg.abattle.communication.command.commands.GameCountdownCommand;
import net.npg.abattle.communication.command.commands.GameFinishedCommand;
import net.npg.abattle.communication.command.commands.GameUpdateCommand;
import net.npg.abattle.communication.command.commands.InitBoardCommand;
import net.npg.abattle.communication.command.commands.LeaveCommand;
import net.npg.abattle.communication.command.commands.LoginCommand;
import net.npg.abattle.communication.command.commands.LoginResultCommand;
import net.npg.abattle.communication.command.commands.PingCommand;
import net.npg.abattle.communication.command.commands.PlayerJoinedCommand;
import net.npg.abattle.communication.command.commands.PlayerLeftCommand;
import net.npg.abattle.communication.command.commands.ServerReplyCommand;
import net.npg.abattle.communication.command.data.BoardUpdateData;
import net.npg.abattle.communication.command.data.CellData;
import net.npg.abattle.communication.command.data.CellUpdateData;
import net.npg.abattle.communication.command.data.InitBoardData;
import net.npg.abattle.communication.command.data.LinkUpdateData;
import net.npg.abattle.communication.command.data.PlayerData;
import net.npg.abattle.communication.command.data.StatisticsData;
import net.npg.abattle.communication.service.ServerService;
import net.npg.abattle.communication.service.common.BooleanResult;
import net.npg.abattle.communication.service.common.ClientInfo;
import net.npg.abattle.communication.service.common.ClientInfoResult;
import net.npg.abattle.communication.service.common.GameInfo;
import net.npg.abattle.communication.service.common.GameInfoResult;
import net.npg.abattle.communication.service.common.GameParameterInfo;
import net.npg.abattle.communication.service.common.MutableIntPoint;

import com.esotericsoftware.kryo.Kryo;

/**
 * @author Cymric TODO ->make annotation for kryoclasses an let xtend create this
 */
@SuppressWarnings("rawtypes")
public class SerializerHelper {

	private static Class[] kryoClasses = { PlayerData[].class, ServerService.class, GameInfoResult.class, GameInfoResult[].class, GameInfo.class,
			ClientInfo.class, ClientInfo[].class, ClientInfoResult.class, MutableIntPoint.class, BooleanResult.class, LoginResultCommand.class,
			LoginCommand.class, GameParameterInfo.class, PlayerJoinedCommand.class, PlayerData.class, BoardUpdateCommand.class, BoardUpdateData.class,
			LinkUpdateData.class, CellUpdateData.class, CellUpdateData[][].class, CellUpdateData[].class, LinkUpdateData[].class, GameUpdateCommand.class,
			StatisticsData[].class, StatisticsData.class, ChangeLinkCommand.class, ServerReplyCommand.class, GameFinishedCommand.class,
			GameCountdownCommand.class, InitBoardCommand.class, InitBoardData.class, CellData.class, CellData[][].class, CellData[].class, LeaveCommand.class,
			PingCommand.class, PlayerLeftCommand.class };

	protected static void initializeKryo(final Kryo kryo) {
		for (final Class clazz : kryoClasses) {
			kryo.register(clazz);
		}
	}
}
