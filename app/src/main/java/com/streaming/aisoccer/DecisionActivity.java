package com.streaming.aisoccer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.streaming.aisoccer.utils.AdManager;

public class DecisionActivity extends AppCompatActivity {

    SpinKitView spinKit;
    private TextView btnStart;
    private TextView msg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        LinearLayout adContainer = findViewById(R.id.banner_container);

        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.loadBannerAd(DecisionActivity.this, adContainer);
            AdManager.loadInterAd(DecisionActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.maxBanner(DecisionActivity.this, adContainer);
            AdManager.maxInterstital(DecisionActivity.this);
        }



        btnStart = findViewById(R.id.btnStart);
        TextView btnStart1 = findViewById(R.id.btnStart1);
        TextView btnStart2 = findViewById(R.id.btnStart2);
        TextView btnStart3 = findViewById(R.id.btnStart3);

        msg = findViewById(R.id.msg);
        spinKit = findViewById(R.id.spinKit);

        spinKit.setVisibility(View.VISIBLE);
        Sprite doubleBounce = new WanderingCubes();
        spinKit.setIndeterminateDrawable(doubleBounce);



        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if (!AdManager.isloadFbAd) {
                        AdManager.adCounter++;
                        AdManager.showMaxInterstitial(DecisionActivity.this, new Intent(DecisionActivity.this, MainActivity.class));
                    }
                    else {
                        startActivityes(new Intent(DecisionActivity.this, MainActivity.class));
                    }
                }
            }

            void startActivityes(Intent intent) {
                if (!AdManager.isloadFbAd) {
                    AdManager.adCounter++;
                    AdManager.showInterAd(DecisionActivity.this, intent);
                } else {
                    AdManager.adCounter++;
                    AdManager.showMaxInterstitial(DecisionActivity.this, intent);
                }
            }

        });

        btnStart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {

                    if (!AdManager.isloadFbAd) {
                        AdManager.adCounter++;
                        AdManager.showMaxInterstitial(DecisionActivity.this, new Intent(DecisionActivity.this, LiveScoreActivity.class));

//                        startActivityes(new Intent(DecisionActivity.this, LiveScoreActivity.class));

                    }


                    else {

                        startActivityes(new Intent(DecisionActivity.this, LiveScoreActivity.class));
                    }
                }
            }

            void startActivityes(Intent intent) {
                if (!AdManager.isloadFbAd) {
                    AdManager.adCounter++;
                    AdManager.showInterAd(DecisionActivity.this, intent);
                } else {
                    AdManager.adCounter++;
                    AdManager.showMaxInterstitial(DecisionActivity.this, intent);
                }
            }







        });

        btnStart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
//                    if (mInterstitialAd != null) {
//                        mInterstitialAd.show(SplashActivity.this);
//                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
//                            @Override
//                            public void onAdDismissedFullScreenContent() {
//                                // Called when fullscreen content is dismissed.

                    if (!AdManager.isloadFbAd) {
                        AdManager.adCounter++;
                        AdManager.showMaxInterstitial(DecisionActivity.this, new Intent(DecisionActivity.this, LiveScoreActivity.class));

                    }

                    else {
                        startActivityes(new Intent(DecisionActivity.this, LiveScoreActivity.class));
                    }
                }
            }

            void startActivityes(Intent intent) {
                if (!AdManager.isloadFbAd) {
                    AdManager.adCounter++;
                    AdManager.showInterAd(DecisionActivity.this, intent);
                } else {
                    AdManager.adCounter++;
                    AdManager.showMaxInterstitial(DecisionActivity.this, intent);
                }
            }

        });


        btnStart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if (!AdManager.isloadFbAd) {
                        AdManager.adCounter++;
                        AdManager.showMaxInterstitial(DecisionActivity.this, new Intent(DecisionActivity.this, MainActivity.class));


                    }

                    else {

                        startActivityes(new Intent(DecisionActivity.this, MainActivity.class));
                    }
                }
            }

            void startActivityes(Intent intent) {
                if (!AdManager.isloadFbAd) {
                    AdManager.adCounter++;
                    AdManager.showInterAd(DecisionActivity.this, intent);
                } else {
                    AdManager.adCounter++;
                    AdManager.showMaxInterstitial(DecisionActivity.this, intent);
                }
            }

        });




        new Handler().postDelayed(() -> {

            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {


                msg.setText(getResources().getString(R.string.are_you_ready));
//                    isConn = true;
                btnStart.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.Bounce).duration(500).repeat(1).playOn(btnStart);
                YoYo.with(Techniques.ZoomInLeft).duration(500).repeat(0).playOn(msg);

                spinKit.setVisibility(View.GONE);
                Sprite doubleBounce1 = new WanderingCubes();
                spinKit.setIndeterminateDrawable(doubleBounce1);



            }else{
//                    isConn = false;
                msg.setText(getResources().getString(R.string.try_again));
                btnStart.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.ZoomInLeft).duration(500).repeat(0).playOn(msg);
                spinKit.setVisibility(View.GONE);
                Sprite doubleBounce1 = new WanderingCubes();
                spinKit.setIndeterminateDrawable(doubleBounce1);

                btnStart.setText(getResources().getString(R.string.btn_try_again));
                btnStart.setOnClickListener(view -> recreate());

            }

        },3000);

    }
    @Override
    public void onBackPressed() {
        AdManager.showMaxInterstitial(DecisionActivity.this,null);
        super.onBackPressed();
    }


}