package com.streaming.aisoccer.data;

import java.util.List;

public class Foot {


    private String title;
    private String competition;
    private String thumbnail;
    private String date;
    private List<Video> videos;

    public Foot() {
    }

    public Foot(String title, String competition, String thumbnail, String date, List<Video> videos) {
        this.title = title;
        this.competition = competition;
        this.thumbnail = thumbnail;
        this.date = date;
        this.videos = videos;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompetition() {
        return competition;
    }

    public void setCompetition(String competition) {
        this.competition = competition;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
