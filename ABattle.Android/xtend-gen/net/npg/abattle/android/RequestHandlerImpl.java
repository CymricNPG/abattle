package net.npg.abattle.android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.common.base.Objects;
import net.npg.abattle.client.dependent.BackPressed;
import net.npg.abattle.client.dependent.ConfirmInterface;
import net.npg.abattle.client.dependent.RequestHandler;
import net.npg.abattle.client.dependent.ReturnControl;
import net.npg.abattle.common.utils.Validate;

@SuppressWarnings("all")
public class RequestHandlerImpl implements RequestHandler, BackPressed {
  private View gameView;
  
  private AndroidApplication app;
  
  private WebView webView;
  
  private ReturnControl switchBackFunction;
  
  public RequestHandlerImpl(final View gameView, final AndroidApplication app, final RelativeLayout layout) {
    Validate.notNulls(gameView, app, layout);
    this.gameView = gameView;
    this.app = app;
    Context _applicationContext = app.getApplicationContext();
    WebView _webView = new WebView(_applicationContext);
    this.webView = _webView;
    this.webView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
        final Context context = view.getContext();
        Uri _parse = Uri.parse(url);
        final Intent browserIntent = new Intent(Intent.ACTION_VIEW, _parse);
        browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(browserIntent);
        return true;
      }
    });
    final RelativeLayout.LayoutParams webViewParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 
      RelativeLayout.LayoutParams.WRAP_CONTENT);
    webViewParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
    webViewParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    WebSettings _settings = this.webView.getSettings();
    _settings.setJavaScriptEnabled(false);
    layout.addView(this.webView, webViewParams);
  }
  
  @Override
  public void confirm(final ConfirmInterface confirmInterface) {
    boolean _notEquals = (!Objects.equal(this.gameView, null));
    assert _notEquals;
    boolean _notEquals_1 = (!Objects.equal(this.app, null));
    assert _notEquals_1;
    this.gameView.post(
      new Runnable() {
        @Override
        public void run() {
          AlertDialog.Builder _builder = new AlertDialog.Builder(RequestHandlerImpl.this.app);
          String _headerText = confirmInterface.headerText();
          AlertDialog.Builder _setTitle = _builder.setTitle(_headerText);
          String _questionText = confirmInterface.questionText();
          AlertDialog.Builder _setMessage = _setTitle.setMessage(_questionText);
          String _yesText = confirmInterface.yesText();
          AlertDialog.Builder _setPositiveButton = _setMessage.setPositiveButton(_yesText, 
            new DialogInterface.OnClickListener() {
              @Override
              public void onClick(final DialogInterface dialog, final int which) {
                confirmInterface.yes();
                dialog.cancel();
              }
            });
          String _noText = confirmInterface.noText();
          AlertDialog.Builder _setNegativeButton = _setPositiveButton.setNegativeButton(_noText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int which) {
              dialog.cancel();
            }
          });
          AlertDialog _create = _setNegativeButton.create();
          _create.show();
        }
      });
  }
  
  @Override
  public void showHTMLView(final String text, final ReturnControl switchBackFunction) {
    boolean _notEquals = (!Objects.equal(this.gameView, null));
    assert _notEquals;
    this.switchBackFunction = switchBackFunction;
    this.gameView.post(new Runnable() {
      @Override
      public void run() {
        RequestHandlerImpl.this.webView.loadDataWithBaseURL("file:///android_asset/", text, "text/html", "utf-8", null);
        RequestHandlerImpl.this.webView.setVisibility(View.VISIBLE);
        RequestHandlerImpl.this.gameView.setVisibility(View.GONE);
      }
    });
  }
  
  @Override
  public void onBackPressed() {
    boolean _notEquals = (!Objects.equal(this.gameView, null));
    assert _notEquals;
    boolean _notEquals_1 = (!Objects.equal(this.webView, null));
    assert _notEquals_1;
    this.webView.setVisibility(View.GONE);
    this.gameView.setVisibility(View.VISIBLE);
    this.switchBackFunction.switchBack();
  }
}
