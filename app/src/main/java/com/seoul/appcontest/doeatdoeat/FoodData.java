package com.seoul.appcontest.doeatdoeat;

/**
 * Created by a on 2016-10-28.
 */

public class FoodData {
    private int id;
    private String name;
    private String shortcontents;
    private String longcontents;
    private int like;
    private int count;

    public FoodData() {
    }

    public FoodData(int id, String name, String shortcontents, String longcontents, int like, int count) {
        this.id = id;
        this.name = name;
        this.shortcontents = shortcontents;
        this.longcontents = longcontents;
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
        return shortcontents;
    }

    public void setShortContents(String shortContents) {
        this.shortcontents = shortContents;
    }

    public String getLongContents() {
        return longcontents;
    }

    public void setLongContents(String longContents) {
        this.longcontents = longContents;
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
