package com.meizu.fp;

import java.util.List;

public class Album {
    //专辑名字
    private String name;
    //歌曲
    private List<Track> trackList;
    //主唱
    private String mainMusician;
    
    public Album(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public String getMainMusician() {
        return mainMusician;
    }

    public void setMainMusician(String mainMusician) {
        this.mainMusician = mainMusician;
    }
    
    
}
