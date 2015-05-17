package net.npg.abattle;

import net.npg.abattle.android.AndroidConfiguration;
import net.npg.abattle.android.GoogleServices;
import net.npg.abattle.android.RequestHandlerImpl;
import net.npg.abattle.android.UIDialogComponentImpl;
import net.npg.abattle.client.view.screens.MenuControl;
import net.npg.abattle.common.CommonConstants;
import net.npg.abattle.common.component.ComponentLookup;
import net.npg.abattle.common.component.ExternalRegisterComponent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
//import android.support.multidex.MultiDex;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;

/**
 * a bit messy ..TODO
 */
public class ABattleActivity extends AndroidApplication implements ExternalRegisterComponent, GoogleServices {

	private UIDialogComponentImpl uiDialogComponent;
	// private GameHelper gameHelper;
	private final static int REQUEST_CODE_UNUSED = 9002;

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		checkPlayServices();

		String version;
		try {
			version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
			if (!CommonConstants.VERSION.equals(version)) {
				throw new RuntimeException("Version mismatch:" + version + "!=" + CommonConstants.VERSION);
			}

		} catch (final NameNotFoundException e) {

		}
		createGameHelper();

		final MenuControl menuControl = new MenuControl(this);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		final View gameView = initializeForView(menuControl, AndroidConfiguration.getConfig());
		final RelativeLayout layout = new RelativeLayout(this);
		layout.addView(gameView);

		final RequestHandlerImpl requestHandler = new RequestHandlerImpl(gameView, this, layout);
		uiDialogComponent = new UIDialogComponentImpl(requestHandler);

		setContentView(layout);
	}

	private void createGameHelper() {
		// // Create the GameHelper.
		// gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		// gameHelper.enableDebugLog(false);
		//
		// final GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
		// @Override
		// public void onSignInSucceeded() {
		// }
		//
		// @Override
		// public void onSignInFailed() {
		// }
		// };
		//
		// gameHelper.setup(gameHelperListener);
	}

	private void checkPlayServices() {
		// final int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		// if (result == ConnectionResult.SUCCESS) {
		// return;
		// }
		// GooglePlayServicesUtil.getErrorDialog(result, this, 0).show();
	}

	@Override
	public void onConfigurationChanged(final Configuration config) {
		super.onConfigurationChanged(config);
	}

	@Override
	public void onBackPressed() {
		uiDialogComponent.getBackPressedHandler().onBackPressed();
	}

	@Override
	public void registerComponents(final ComponentLookup lookup) {
		lookup.registerComponent(uiDialogComponent);
	}

	@Override
	protected void onStart() {
		super.onStart();
		// gameHelper.onStart(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
		// gameHelper.onStop();
	}

	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// gameHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void attachBaseContext(final Context base) {
		super.attachBaseContext(base);
		// MultiDex.install(this);
	}

	@Override
	public void signIn() {
		try {
			runOnUiThread(new Runnable() {
				// @Override
				@Override
				public void run() {
					// gameHelper.beginUserInitiatedSignIn();
				}
			});
		} catch (final Exception e) {
			Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void signOut() {
		try {
			runOnUiThread(new Runnable() {
				// @Override
				@Override
				public void run() {
					// gameHelper.signOut();
				}
			});
		} catch (final Exception e) {
			Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage() + ".");
		}
	}

	@Override
	public void rateGame() {
		// Replace the end of the URL with the package of your game
		final String str = "https://play.google.com/store/apps/details?id=net.npg.abattle";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void submitScore(final long score) {
		if (isSignedIn()) {
			// Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(R.string.leaderboard_id), score);
			// startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
			// getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);

		} else {
			// Maybe sign in here then redirect to submitting score?
		}
	}

	@Override
	public void showScores() {
		if (isSignedIn()) {
			// startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
			// getString(R.string.leaderboard_id)), REQUEST_CODE_UNUSED);
		} else {
			// Maybe sign in here then redirect to showing scores?
		}
	}

	@Override
	public boolean isSignedIn() {
		return false;
		// return gameHelper.isSignedIn();
	}
}
