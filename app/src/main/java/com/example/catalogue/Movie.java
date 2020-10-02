package com.example.catalogue;

public class Movie {
    private String id;
    private String movieName;
    private String writerName;


    public Movie() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public String getId() {
        return id;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getWriterName() {
        return writerName;
    }
}
