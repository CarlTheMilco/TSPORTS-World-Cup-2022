package com.streaming.aisoccer.data;

public class Video {

    private String title ;
    private String embed;

    public Video() {
    }

    public Video(String title, String embed) {
        this.title = title;
        this.embed = embed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmbed() {
        return embed;
    }

    public void setEmbed(String embed) {
        this.embed = embed;
    }
}
