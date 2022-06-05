package com.SampleProject.Company.Model;

public class User {

    private long id;
    private String name;

    public User() {
    }

    public User(long userId, String name){
        this.id = userId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
