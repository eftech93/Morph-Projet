package com.tunaprojects.morph.View.Bases;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.tunaprojects.morph.Controller.Dialog.SuperDialog;
import com.tunaprojects.morph.R;
import android.view.ScaleGestureDetector;
import android.webkit.CookieManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class test extends AppCompatActivity {
    private ImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;
    private ImageView iv_;
    // Hold a reference to the current animator,
    // so that it can be canceled mid-way.
    private Animator mCurrentAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*final LinearLayout rl = (LinearLayout) findViewById(R.id.mainactivity);
        imageView = new ImageView(this);
        LinearLayout.LayoutParams lllp = new LinearLayout.LayoutParams(200, 200);
        rl.addView(imageView, lllp);
        String url = "http://1.bp.blogspot.com/-ZIRfkrDkGW4/Tv8Ub8en78I/AAAAAAAAMj8/m8kLexEeAXk/s640/Roy+McCrohan+mugshot+1983.jpg";
        LoadImageTask lit = new LoadImageTask(imageView);
        lit.execute(new String[]{url});
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperDialog.openViewAsDialog(test.this, view, this);
            }
        });
        iv_ = new ImageView(this);
        rl.addView(iv_);
        TextView t = new TextView(this);
        t.setText("Hola mundo");
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperDialog.openViewAsDialog(test.this, view, this);
            }
        });
        rl.addView(t);
        TextView t_ = new TextView(this);
        t_.setText("Hola mundo _ _ 3");
        t_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuperDialog.openViewAsDialog(test.this, view, this);
            }
        });
        rl.addView(t_);
        AsyncUrlCall auc = new AsyncUrlCall("", new ArrayList());
        auc.execute("https://tryxml.000webhostapp.com/zyro/testing.php", "act=gasf");*/
        final WebView wv = new WebView(this);
        WebSettings settings = wv.getSettings();
        // Enable Javascript
        settings.setJavaScriptEnabled(true);
        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //settings.setBuiltInZoomControls(true);
        //settings.setDomStorageEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptThirdPartyCookies(wv, true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        settings.setPluginState(WebSettings.PluginState.ON);
        wv.setWebChromeClient(new WebChromeClient() {
            // Need to accept permissions to use the camera
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                test.this.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        request.grant(request.getResources());
                    }
                });
            }

        });
        wv.loadUrl("https://tryxml.000webhostapp.com/prueba.html?hola" );
        AlertDialog ad = SuperDialog.openDialog(this, "Video llamadda", wv);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                wv.destroy();
            }
        });
    }
}