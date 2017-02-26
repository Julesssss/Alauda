package com.gmail.julianrosser91.alauda.data.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A set is a collection of video content.
 */
public class Set extends RealmObject {

    @PrimaryKey
    private String uid;
    private String title;
    private String body;
    private String quoter;
    private String summary;

    public String getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getQuoter() {
        return quoter;
    }

    public String getSummary() {
        return summary;
    }
}
