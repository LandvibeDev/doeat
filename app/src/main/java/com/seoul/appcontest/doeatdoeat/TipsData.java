package com.seoul.appcontest.doeatdoeat;

/**
 * Created by user on 2016-10-31.
 */

public class TipsData {
    String title;
    String contents;

    public TipsData() {
    }

    public TipsData(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
