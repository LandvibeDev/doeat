package com.seoul.appcontest.doeatdoeat;

import com.facebook.share.widget.LikeView;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

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
    public FoodData(FoodData f){
        this.id=f.getId();
        this.name=f.getName();
        this.shortcontents=f.getShortContents();
        this.longcontents=f.getLongContents();
        this.like=f.getLike();
        this.count=f.getCount();
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

    @Exclude
    public Map<String,Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("like", like);
        result.put("count", count);
        result.put("shortcontents", shortcontents);
        result.put("longcontents", longcontents);
        return result;
    }

}
