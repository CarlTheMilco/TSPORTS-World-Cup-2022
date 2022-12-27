package com.streaming.aisoccer.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.streaming.aisoccer.R;
import com.streaming.aisoccer.VideoActivity;
import com.streaming.aisoccer.data.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context context;
    private List<Video> videoList;

    public VideoAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, int position) {

        Video video = videoList.get(position);

        holder.videoPosition.setText("Video " + (position + 1));
        holder.title.setText(video.getTitle());

        holder.btnWatch.setOnClickListener(view -> {
            Intent intent = new Intent(context, VideoActivity.class);
            intent.putExtra("embed",video.getEmbed());
            context.startActivity(intent);
        });


        YoYo.with(Techniques.Flash).duration(1000).repeat(0).playOn(holder.btnWatch);


    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title, videoPosition;
        private ImageView btnWatch;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.vidTitle);
            videoPosition = itemView.findViewById(R.id.videoPosition);
            btnWatch = itemView.findViewById(R.id.btnWatch);

        }
    }
}
