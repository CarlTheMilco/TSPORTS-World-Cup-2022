package com.streaming.aisoccer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.streaming.aisoccer.R;
import com.streaming.aisoccer.utils.AdManager;


public class LiveScoreActivity extends AppCompatActivity {

    private WebView webView;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_score);

        LinearLayout adContainer = findViewById(R.id.banner_container);

        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.loadBannerAd(LiveScoreActivity.this, adContainer);
            AdManager.loadInterAd(LiveScoreActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.maxBanner(LiveScoreActivity.this, adContainer);
            AdManager.maxInterstital(LiveScoreActivity.this);
        }


        webView = findViewById(R.id.webView);
        TextView btnBack = findViewById(R.id.btnBack);



        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.scorebat.com/embed/livescore/");


        btnBack.setOnClickListener(view -> {
            if (!AdManager.isloadFbAd) {
                AdManager.adCounter++;
                AdManager.showInterAd(LiveScoreActivity.this, null);
            } else {
                AdManager.adCounter++;
                AdManager.showMaxInterstitial(LiveScoreActivity.this, null);
            }
            webView.destroy();
            finish();

        });


    }
    @Override
    public void onBackPressed() {
        AdManager.showMaxInterstitial(LiveScoreActivity.this,null);
        super.onBackPressed();
    }
}