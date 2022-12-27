package com.streaming.aisoccer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.streaming.aisoccer.adapters.FootAdapter;
import com.streaming.aisoccer.data.Foot;
import com.streaming.aisoccer.data.Video;
import com.streaming.aisoccer.utils.AdManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.ionbit.ionalert.IonAlert;

public class MainActivity extends AppCompatActivity {

    private FootAdapter footAdapter;

    private List<Foot> footList;
    private List<Video> videoList;

    SpinKitView spinKit;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout adContainer = findViewById(R.id.banner_container);

        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.loadBannerAd(MainActivity.this, adContainer);
            AdManager.loadInterAd(MainActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.maxBanner(MainActivity.this, adContainer);
            AdManager.maxInterstital(MainActivity.this);
        }


        ImageView btnSearch = findViewById(R.id.btnSearch);
        ImageView btnPrivacy = findViewById(R.id.btnPrivacy);
        TextView title = findViewById(R.id.title);
        TextView btnLiveScore = findViewById(R.id.btnLiveScore);

        spinKit = findViewById(R.id.spinKit);

        spinKit.setVisibility(View.VISIBLE);
        Sprite doubleBounce = new WanderingCubes();
        spinKit.setIndeterminateDrawable(doubleBounce);


        YoYo.with(Techniques.FadeIn).duration(1000).repeat(0).playOn(title);
        YoYo.with(Techniques.Bounce).duration(1000).repeat(0).playOn(btnPrivacy);
        YoYo.with(Techniques.Bounce).duration(1000).repeat(5).playOn(btnSearch);


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        footList = new ArrayList<>();
        videoList = new ArrayList<>();

        footAdapter = new FootAdapter(MainActivity.this,footList);
        recyclerView.setAdapter(footAdapter);



        jsonParse();



        //jsonParse();

        btnSearch.setOnClickListener(view -> {
            try {
                startActivityes(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
            } catch (android.content.ActivityNotFoundException e) {
                startActivityes(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
            }
        });
        btnPrivacy.setOnClickListener(view -> {
            String url = "https://sites.google.com/view/football-app";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivityes(i);
        });


        btnLiveScore.setOnClickListener(view -> {


            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            }


            IonAlert alert =  new IonAlert(MainActivity.this, IonAlert.SUCCESS_TYPE)
                    .setTitleText("MESSAGE")
                    .setContentText("Are you sure?")
                    .setConfirmText("Show")
                    .setCancelText("No")
                    .setConfirmClickListener(sDialog -> {
                        startActivityes(new Intent(getApplicationContext(), LiveScoreActivity.class));
                        sDialog.dismissWithAnimation();
                    }).setCancelClickListener(IonAlert::cancel);
            alert.setCancelable(true);
            alert.show();


        });


    }

    private void jsonParse() {

        RequestQueue mQueue = Volley.newRequestQueue(this);
        String url = "https://www.scorebat.com/video-api/v3/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("response");

                        String title1 = "";
                        String competition = "";
                        String thumbnail = "";
                        String date = "";

                        String title2 = "";
                        String embed = "";

                        footList.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.d("mytag",videoList.size()+"");
                            videoList = new ArrayList<>();

                            JSONObject employee = jsonArray.getJSONObject(i);

                            title1 = employee.getString("title");
                            competition = employee.getString("competition");
                            thumbnail = employee.getString("thumbnail");
                            date = employee.getString("date");


                            Log.d("mytag",title1);



                            JSONArray jsonArray2 = employee.getJSONArray("videos");


                            for (int x = 0; x < jsonArray2.length(); x++) {
                                JSONObject videos = jsonArray2.getJSONObject(x);
                                embed = videos.getString("embed");
                                title2 = videos.getString("title");
                                Video video = new Video();
                                video.setTitle(title2);
                                video.setEmbed(embed);
                                videoList.add(video);
                                Log.d("mytag",embed);
                            }

                            Foot foot = new Foot();
                            foot.setTitle(title1);
                            foot.setCompetition(competition);
                            foot.setThumbnail(thumbnail);
                            foot.setDate(date);
                            foot.setVideos(videoList);

                            footList.add(foot);
                        }
                        footAdapter.notifyDataSetChanged();
                        spinKit.setVisibility(View.GONE);
                        Log.d("mytag", footList.size() + " " + footList.get(0).getVideos().get(0).getTitle() +" "+ footList.get(0).getVideos().get(0).getEmbed());
                        Log.d("mytag",  footList.get(0).getTitle());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

        mQueue.add(request);

    }
    @Override
    public void onBackPressed() {
        AdManager.showMaxInterstitial(MainActivity.this,null);
        super.onBackPressed();
    }



    void startActivityes(Intent intent) {
        if (!AdManager.isloadFbAd) {
            AdManager.adCounter++;
            AdManager.showInterAd(MainActivity.this, intent);
        } else {
            AdManager.adCounter++;
            AdManager.showMaxInterstitial(MainActivity.this, intent);
        }
    }

}