package com.streaming.aisoccer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.streaming.aisoccer.utils.AdManager;

public class VideoActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video);
        AdManager.maxInterstital(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String embed = extras.getString("embed");
            WebView webview = findViewById(R.id.webview);
            webview.getSettings().setJavaScriptEnabled(true);

            String[] part = embed.split("'");

            for (String s : part) {
                Log.d("VideoActivity", s);
            }
            webview.loadUrl(part[3]);
        }

    }

    @Override
    public void onBackPressed() {
        AdManager.showMaxInterstitial(VideoActivity.this,null);
        super.onBackPressed();
    }
}