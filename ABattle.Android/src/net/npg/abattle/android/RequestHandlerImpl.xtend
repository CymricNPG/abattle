package net.npg.abattle.android

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import com.badlogic.gdx.backends.android.AndroidApplication
import net.npg.abattle.client.dependent.BackPressed
import net.npg.abattle.client.dependent.ConfirmInterface
import net.npg.abattle.client.dependent.RequestHandler
import net.npg.abattle.client.dependent.ReturnControl
import net.npg.abattle.common.utils.Validate

import static net.npg.abattle.common.utils.Asserts.*

class RequestHandlerImpl implements RequestHandler, BackPressed {

	private View gameView

	private AndroidApplication app

	private WebView webView

	private ReturnControl switchBackFunction;

	new(View gameView, AndroidApplication app, RelativeLayout layout) {
		Validate.notNulls(gameView, app, layout)
		this.gameView = gameView
		this.app = app
		webView = new WebView(app.getApplicationContext());

		webView.setWebViewClient(new WebViewClient() {

			override boolean shouldOverrideUrlLoading(WebView view, String url) {
				val context = view.getContext();
				val browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(browserIntent);
				return true;
			}
		});

		val webViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
			RelativeLayout.LayoutParams.WRAP_CONTENT);
		webViewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		webViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		webView.getSettings().setJavaScriptEnabled(false);
		layout.addView(webView, webViewParams);

	}

	override void confirm(ConfirmInterface confirmInterface) {
		assertIt(gameView != null)
		assertIt(app != null)
		gameView.post(
			new Runnable() {
				override void run() {
					new AlertDialog.Builder(app).setTitle(confirmInterface.headerText()).setMessage(
						confirmInterface.questionText()).setPositiveButton(confirmInterface.yesText(),
						new DialogInterface.OnClickListener() {
							override void onClick(DialogInterface dialog, int which) {
								confirmInterface.yes();
								dialog.cancel();
							}
						}).setNegativeButton(confirmInterface.noText(), new DialogInterface.OnClickListener() {
						override
							 void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					}).create().show();
				}
			});
	}

	override void showHTMLView(String text, ReturnControl switchBackFunction) {
		assertIt(gameView != null)
		this.switchBackFunction = switchBackFunction;
		gameView.post(new Runnable() {
			override void run() {
				webView.loadDataWithBaseURL("file:///android_asset/", text, "text/html", "utf-8", null);
				webView.setVisibility(View.VISIBLE);
				gameView.setVisibility(View.GONE);
			}
		});

	}

	override void onBackPressed() {
		assertIt(gameView != null)
		assertIt(webView != null)

		webView.setVisibility(View.GONE);
		gameView.setVisibility(View.VISIBLE);
		switchBackFunction.switchBack();
	}
}