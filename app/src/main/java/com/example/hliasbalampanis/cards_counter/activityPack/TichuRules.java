package com.example.hliasbalampanis.cards_counter.activityPack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.hliasbalampanis.cards_counter.R;

public class TichuRules extends AppCompatActivity {

    private WebView webViewDil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tichu_rules);

        webViewDil = (WebView) findViewById(R.id.webViewDil);
        webViewDil = new WebView(this);
        webViewDil.getSettings().setJavaScriptEnabled(true);
        webViewDil.loadUrl("file:///android_asset/tichuRules.html");
        setContentView(webViewDil);
    }
}
