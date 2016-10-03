package liuxiaocong.com.floatlayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    WindowManager mWindowManager;
    private static final String YOUTUBE_API_KEY = "AIzaSyC1rMU-mkhoyTvBIdTnYU0dss0tU9vtK48";
    private static final String VIDEO_KEY = "gsjtg7m1MMM";
    private YouTubePlayer youtubePlayer;
    private YouTubePlayerSupportFragment youtubeFragment;
    WebView mWebView;
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    WindowManager.LayoutParams params;
    View winView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YoutubePlayerManager.getInst().addToWindows(MainActivity.this);
                YoutubePlayerManager.getInst().playYoutube(MainActivity.this, "gsjtg7m1MMM");
            }
        });
//        mContext = getApplicationContext();
//        // 获取WindowManager
//        mWindowManager = (WindowManager) mContext
//                .getSystemService(Context.WINDOW_SERVICE);
//
//        params = new WindowManager.LayoutParams();
//
//        // 类型
//        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//        params.format = PixelFormat.RGBA_8888;
//        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//
//
//        params.width = Util.getPxFromDp(this, 200);
//        params.height = Util.getPxFromDp(this, 160);
//
//        params.gravity = Gravity.LEFT | Gravity.TOP;
//
//        winView = LayoutInflater.from(this).inflate(R.layout.float_layout,
//                null);
//
//        mWindowManager.addView(winView, params);
//        winView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                x = motionEvent.getRawX();
//                y = motionEvent.getRawY() - 25;   //25是系统状态栏的高度
//                //Log.i("currP", "currX"+x+"====currY"+y);
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_DOWN:    //捕获手指触摸按下动作
//                        //获取相对View的坐标，即以此View左上角为原点
//                        mTouchStartX = motionEvent.getX();
//                        mTouchStartY = motionEvent.getY();
//
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:    //捕获手指触摸移动动作
//                        updateViewPosition();
//                        break;
//
//                    case MotionEvent.ACTION_UP:    //捕获手指触摸离开动作
//                        updateViewPosition();
//                        mTouchStartX = mTouchStartY = 0;
//                        break;
//                }
//                return true;
//            }
//        });
//
//
//        mWebView = (WebView) winView.findViewById(R.id.webview);
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
//        mWebView.getSettings().setSupportZoom(true);
//        mWebView.getSettings().setDomStorageEnabled(true);
//
//        mWebView.requestFocus();
//        mWebView.getSettings().setUseWideViewPort(true);
//        mWebView.getSettings().setLoadWithOverviewMode(true);
//        mWebView.getSettings().setSupportZoom(true);
//        mWebView.getSettings().setBuiltInZoomControls(true);
//        mWebView.setBackgroundColor(Color.parseColor("#ffffff"));
//
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//                view.loadUrl("javascript:window.local_obj.showSource('<head>'+"
//                        + "document.getElementsByTagName('html')[0].innerHTML+'</head>');");
//
//            }
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode,
//                                        String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//            }
//
//        });
//        mWebView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mWebView.loadUrl("file:///android_asset/youtubePlayer.html");
//            }
//        }, 1000);
    }

    private void updateViewPosition() {
        //更新浮动窗口位置参数
        params.x = (int) (x - mTouchStartX);
        params.y = (int) (y - mTouchStartY);
        mWindowManager.updateViewLayout(winView, params);  //刷新显示
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            System.out.println("====>html=" + html);
        }
    }

}
