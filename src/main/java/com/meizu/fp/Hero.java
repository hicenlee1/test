package com.meizu.fp;

public class Hero {
    private String name;
    private String gender;
    private String type;

    public Hero(String name, String gender, String type) {
        this.name = name;
        this.gender = gender;
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "name:" + this.name + ";gender:" + this.gender + "; type:" + this.type;
    }
}