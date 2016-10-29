package com.seoul.appcontest.doeatdoeat;

/**
 * Created by a on 2016-10-28.
 */

public class FoodData {
    private int id;
    private String name;
    private String shortContents;
    private String longContents;
    private int like;
    private int count;

    public FoodData() {
    }

    public FoodData(int id, String name, String shortContents, String longContents, int like, int count) {
        this.id = id;
        this.name = name;
        this.shortContents = shortContents;
        this.longContents = longContents;
        this.like = like;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortContents() {
        return shortContents;
    }

    public void setShortContents(String shortContents) {
        this.shortContents = shortContents;
    }

    public String getLongContents() {
        return longContents;
    }

    public void setLongContents(String longContents) {
        this.longContents = longContents;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
