package com.meizu.fp;

import java.util.List;

public class Artist {
    private String name;
    //单人 还是 乐队
    private boolean solo;
    //成员
    private List<String> members;
    //专辑
    private List<Album> album;
    
    public Artist(String name) {
        this.name = name;
    }
    
    public boolean isSolo() {
        return solo;
    }

    public void setSolo(boolean solo) {
        this.solo = solo;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
