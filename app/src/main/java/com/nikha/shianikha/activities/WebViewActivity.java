package com.nikha.shianikha.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Session;
import com.nikha.App;
import com.nikha.shianikha.R;
import com.nikha.shianikha.commen.P;

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
                    url = url.toLowerCase();
                    if (url.contains("listIsPrepared") || url.contains("fail"))
                    {
                        if (url.contains("listIsPrepared"))
                        {
                            Session session = new Session(WebViewActivity.this);
                            session.addInt(P.showName,1);
                            App.showName = true;
                        }

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                                Intent intent = new Intent(WebViewActivity.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        },1230);
                    }
                }
            });

            webView.loadUrl(string);
        }
    }
}
