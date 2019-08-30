package com.nikha.shianikha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.adoisstudio.helper.H;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        String string = getIntent().getStringExtra("url");
        H.log("urlIs",string);

        if (string!=null && !string.isEmpty() )
        {
            WebView webView = findViewById(R.id.webView);
            webView.loadUrl(string);

            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(true);
        }

    }
}
