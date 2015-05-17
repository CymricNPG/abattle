package net.npg.abattle.android

import com.badlogic.gdx.scenes.scene2d.Stage
import net.npg.abattle.client.dependent.MultiplayerScreen
import net.npg.abattle.client.view.screens.BasicScreen
import net.npg.abattle.client.view.screens.GameParameterPartScreen
import net.npg.abattle.client.view.screens.Icons
import net.npg.abattle.client.view.screens.Layout
import net.npg.abattle.client.view.screens.ScreenSwitcher
import net.npg.abattle.client.view.screens.Screens
import net.npg.abattle.client.view.screens.Widgets
import net.npg.abattle.common.component.ComponentLookup
import net.npg.abattle.common.configuration.ConfigurationComponent
import net.npg.abattle.common.i18n.I18N
import net.npg.abattle.common.utils.Validate

//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.TextView;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.games.Games;
//import com.google.android.gms.games.GamesStatusCodes;
//import com.google.android.gms.games.GamesActivityResultCodes;
//import com.google.android.gms.games.multiplayer.Invitation;
//import com.google.android.gms.games.multiplayer.Multiplayer;
//import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
//import com.google.android.gms.games.multiplayer.Participant;
//import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
//import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
//import com.google.android.gms.games.multiplayer.realtime.Room;
//import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
//import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
//import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;
//import com.google.android.gms.plus.Plus;
class MultiplayerScreenImpl implements MultiplayerScreen {

//	private GoogleApiClient mGoogleApiClient

	private ScreenSwitcher switcher
	GameParameterPartScreen parameterScreen

	override create(Widgets widgets, Stage stage, BasicScreen screen) {
		widgets.addButton(Layout.MULTI_QUICK, Icons.Quick, [quickStart])
		widgets.addButton(Layout.MULTI_INVITE, Icons.Invites, [joinInvites])
		parameterScreen = new GameParameterPartScreen(widgets, true, screen)
		parameterScreen.create([|newGame], stage)
	}

	def newGame() {
		// request code for the "select players" UI
		// can be any number as long as it's unique
//		val RC_SELECT_PLAYERS = 10000;

		// launch the player selection screen
		// minimum: 1 other player; maximum: 3 other players
//		val intent = Games.RealTimeMultiplayer.getSelectOpponentsIntent(mGoogleApiClient, 1, 3);
		// startActivityForResult(intent, RC_SELECT_PLAYERS);
		if(switcher != null) {
			switcher.switchToScreen(Screens.Main)
		}
	}

	private def joinInvites() {
		if(switcher != null) {
			switcher.switchToScreen(Screens.Main)
		}
	}

	private def quickStart() {
		if(switcher != null) {
			switcher.switchToScreen(Screens.Main)
		}
		parameterScreen.finish
		ComponentLookup.instance.getComponent(ConfigurationComponent).save

//		// auto-match criteria to invite one random automatch opponent.
//		// You can also specify more opponents (up to 3).
//		val am = RoomConfig.createAutoMatchCriteria(1, 1, 0);
//
//		// build the room config:
//		val roomConfigBuilder = makeBasicRoomConfigBuilder();
//		roomConfigBuilder.setAutoMatchCriteria(am);
//		val roomConfig = roomConfigBuilder.build();
//
//		// create room:
//		Games.RealTimeMultiplayer.create(mGoogleApiClient, roomConfig);
//
//		// prevent screen from sleeping during handshake
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//	// go to game screen
	}

	override getDefaultText() {
		return I18N.get("multi.default")
	}

	override setSwitcher(ScreenSwitcher arg0) {
		Validate.notNull(arg0)
		switcher = arg0
	}
}