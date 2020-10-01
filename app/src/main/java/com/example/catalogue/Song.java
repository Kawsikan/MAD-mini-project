package com.example.catalogue;

public class Song {
    private String id;
    private String songName;
    private String artist;

    public Song() {

    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtist() {
        return artist;
    }


}
