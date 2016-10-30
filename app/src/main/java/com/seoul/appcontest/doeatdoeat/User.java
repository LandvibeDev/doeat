package com.seoul.appcontest.doeatdoeat;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2016-10-30.
 */

public class User {
    private String email;
    private String language;
    private String likeFoods;

    public User() {
    }

    public User(String email, String language, String likeFoods) {
        this.email = email;
        this.language = language;
        this.likeFoods = likeFoods;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLikeFoods() {
        return likeFoods;
    }

    public void setLikeFoods(String likeFoods) {
        this.likeFoods = likeFoods;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", this.email);
        result.put("language", this.language);
        result.put("likeFoods", this.likeFoods);
        return result;
    }
}
