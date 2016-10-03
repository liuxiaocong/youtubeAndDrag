package liuxiaocong.com.floatlayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by LiuXiaocong on 9/26/2016.
 */
public class YoutubePlayerManager {
    private static YoutubePlayerManager gInstance = null;
    WindowManager mWindowManager;
    WindowManager.LayoutParams mWindowParams;
    View mView;
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    WebView mWebView;
    public static synchronized YoutubePlayerManager getInst() {
        if (gInstance == null) {
            gInstance = new YoutubePlayerManager();
        }
        return gInstance;
    }

    private YoutubePlayerManager() {

    }

    public void addToWindows(Context context) {
        Context mContext = context.getApplicationContext();

        mWindowManager = (WindowManager) mContext
                .getSystemService(Context.WINDOW_SERVICE);

        mWindowParams = new WindowManager.LayoutParams();

        mWindowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mWindowParams.format = PixelFormat.RGBA_8888;
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mWindowParams.width = Util.getPxFromDp(context, 300);
        mWindowParams.height = Util.getPxFromDp(context, 200);
        mWindowParams.gravity = Gravity.LEFT | Gravity.TOP;
        mView = LayoutInflater.from(mContext).inflate(R.layout.float_layout,
                null);

        mWindowManager.addView(mView, mWindowParams);
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                x = motionEvent.getRawX();
                y = motionEvent.getRawY() - 25;

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchStartX = motionEvent.getX();
                        mTouchStartY = motionEvent.getY();

                        break;

                    case MotionEvent.ACTION_MOVE:
                        updateViewPosition();
                        break;

                    case MotionEvent.ACTION_UP:
                        updateViewPosition();
                        mTouchStartX = mTouchStartY = 0;
                        break;
                }
                return true;
            }
        });
        mWebView = (WebView) mView.findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setDomStorageEnabled(true);

        mWebView.requestFocus();
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setBackgroundColor(Color.parseColor("#ffffff"));

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
                        + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");

            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

        });
    }
    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            System.out.println("====>html=" + html);
        }
    }
    private void updateViewPosition() {
        if (mWindowParams == null) return;
        mWindowParams.x = (int) (x - mTouchStartX);
        mWindowParams.y = (int) (y - mTouchStartY);
        mWindowManager.updateViewLayout(mView, mWindowParams);
    }

    public void playYoutube(Context context, String id) {
        mWebView.loadUrl("file:///android_asset/youtubePlayer.html?id="+id);
    }
}
