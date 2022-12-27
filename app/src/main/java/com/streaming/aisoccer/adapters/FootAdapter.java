package com.streaming.aisoccer.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.streaming.aisoccer.InfoActivity;
import com.streaming.aisoccer.R;
import com.streaming.aisoccer.data.Foot;
import com.streaming.aisoccer.data.Video;

import java.util.List;

import id.ionbit.ionalert.IonAlert;

public class FootAdapter extends RecyclerView.Adapter<FootAdapter.ViewHolder> {

    private final Context context;
    private final List<Foot> footList;
    private InterstitialAd mInterstitialAd;
    private boolean bool = true;

    public FootAdapter(Context context, List<Foot> footList) {
        this.context = context;
        this.footList = footList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foot_item,parent,false);

        if (bool){
            MobileAds.initialize(context, initializationStatus -> {
            });


            AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(context, context.getResources().getString(R.string.interstitial), adRequest,
                    new InterstitialAdLoadCallback() {
                        @Override
                        public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                            mInterstitialAd = interstitialAd;
                        }
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            mInterstitialAd = null;
                            Log.d("TAG00", "inter: code :" + loadAdError.getCode());
                            Log.d("TAG00", "inter: message :" + loadAdError.getMessage());
                            Toast.makeText(context, "Code:"+loadAdError.getCode(), Toast.LENGTH_SHORT).show();
                        }
                    });


            bool = false;
        }







        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FootAdapter.ViewHolder holder, int position) {




        Foot f = footList.get(position);

        String[] name = f.getTitle().split("-");
        holder.name1.setText(name[0]);
        holder.name2.setText(name[1]);

        String[] date = f.getDate().split("T");
        holder.date.setText(date[0]);

        holder.cup.setText(f.getCompetition());

        Glide.with(context).load(f.getThumbnail()).into(holder.image);

        holder.itemView.setOnClickListener(view -> {

            if (mInterstitialAd != null) {
                mInterstitialAd.show((Activity) context);
            }

            IonAlert alert =  new IonAlert(context, IonAlert.SUCCESS_TYPE)
                    .setTitleText("MESSAGE")
                    .setContentText("Are you sure?")
                    .setConfirmText("Show")
                    .setCancelText("No")
                    .setConfirmClickListener(sDialog -> {

                        Intent intent = new Intent(context, InfoActivity.class);
                        intent.putExtra("title", f.getTitle());
                        intent.putExtra("competition", f.getCompetition());
                        intent.putExtra("date", f.getDate());
                        intent.putExtra("thumbnail", f.getThumbnail());
                        intent.putExtra("size", f.getVideos().size());
                        for (int x = 0; x < f.getVideos().size(); x++) {
                            Video video = f.getVideos().get(x);
                            intent.putExtra("embed" + x, video.getEmbed());
                            intent.putExtra("title" + x, video.getTitle());
                        }
                        context.startActivity(intent);

                        sDialog.dismissWithAnimation();
                    }).setCancelClickListener(IonAlert::cancel);
            alert.setCancelable(true);
            alert.show();






        });

        YoYo.with(Techniques.ZoomInLeft).duration(500).repeat(0).playOn(holder.itemView);


    }


    @Override
    public int getItemCount() {
        return footList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name1;
        private final TextView name2;
        private final TextView date;
        private final TextView cup;
        private final ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name1 = itemView.findViewById(R.id.name1);
            name2 = itemView.findViewById(R.id.name2);
            date = itemView.findViewById(R.id.date);
            cup = itemView.findViewById(R.id.cup);
            image = itemView.findViewById(R.id.image);

        }
    }
}
