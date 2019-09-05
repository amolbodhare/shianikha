package com.nikha.shianikha;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adoisstudio.helper.H;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String string = getIntent().getStringExtra("url");
        H.log("urlIs", string);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final TextView textView = findViewById(R.id.textView);

        if (string != null && !string.isEmpty()) {
            WebView webView = findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);

           /* webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(true);*/

            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    //super.onProgressChanged(view, newProgress);
                    if (newProgress < 100) {
                        progressBar.setProgress(newProgress);
                        textView.setText(newProgress+"%");
                    }
                }
            });

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    //super.onPageFinished(view, url);
                    findViewById(R.id.linearLayout).setVisibility(View.GONE);
                    H.log("loadedUrlIs",url);
                    if (url.contains("success") || url.contains("fail"))
                    {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        },1230);
                    }
                }
            });

            webView.loadUrl(string);
        }
    }
}
