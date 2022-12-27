package com.streaming.aisoccer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.streaming.aisoccer.adapters.VideoAdapter;
import com.streaming.aisoccer.data.Video;
import com.streaming.aisoccer.utils.AdManager;

import java.util.ArrayList;
import java.util.List;


public class InfoActivity extends AppCompatActivity {

    private TextView name1, name2, date,cup,btnBack, title;
    private ImageView image;

    private List<Video> videoList;

    private LinearLayout blackContain;


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        initialize();
        LinearLayout adContainer = findViewById(R.id.banner_container);

        if (!AdManager.isloadFbAd) {
            //admob
            AdManager.loadBannerAd(InfoActivity.this, adContainer);
            AdManager.loadInterAd(InfoActivity.this);
        } else {
            //MAX + Fb banner Ads
            AdManager.maxBanner(InfoActivity.this, adContainer);
            AdManager.maxInterstital(InfoActivity.this);
        }




        YoYo.with(Techniques.BounceIn).duration(500).repeat(0).playOn(title);
        YoYo.with(Techniques.FadeIn).duration(1000).repeat(0).playOn(blackContain);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        videoList = new ArrayList<>();
        VideoAdapter videoAdapter = new VideoAdapter(InfoActivity.this, videoList);
        recyclerView.setAdapter(videoAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String title = extras.getString("title");
            String competition = extras.getString("competition");
            String getdate = extras.getString("date");
            String thumbnail = extras.getString("thumbnail");

            String[] name = title.split("-");
            name1.setText(name[0]);
            name2.setText(name[1]);
            cup.setText(competition);
            String[] get2date = getdate.split("T");
            date.setText(get2date[0]);
            Glide.with(InfoActivity.this).load(thumbnail).into(image);

            int size = extras.getInt("size");
            for(int x=0 ; x < size ; x++){
                Log.d("mytag",extras.getString("title"+x) + " " + extras.getString("embed"+x));
                Video video = new Video();
                video.setTitle(extras.getString("title"+x));
                video.setEmbed(extras.getString("embed"+x));
                videoList.add(video);
            }
            videoAdapter.notifyDataSetChanged();
        }


        btnBack.setOnClickListener(view -> {
            if (!AdManager.isloadFbAd) {
                AdManager.adCounter++;
                AdManager.showInterAd(InfoActivity.this, null);
            } else {
                AdManager.adCounter++;
                AdManager.showMaxInterstitial(InfoActivity.this, null);
            }

            finish();
        });


    }
    @Override
    public void onBackPressed() {
        AdManager.showMaxInterstitial(InfoActivity.this,null);
        super.onBackPressed();
    }


    private void initialize() {

        videoList = new ArrayList<>();
        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        date = findViewById(R.id.date);
        cup = findViewById(R.id.cup);
        image = findViewById(R.id.image);
        btnBack = findViewById(R.id.btnBack);
        title = findViewById(R.id.title);
        blackContain = findViewById(R.id.blackContain);

    }

}