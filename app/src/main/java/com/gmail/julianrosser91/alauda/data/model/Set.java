package com.gmail.julianrosser91.alauda.data.model;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
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
    private String url;

    @SerializedName("image_urls")
    private RealmList<RealmString> imageUrls;

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

    public String getImageUrl() {
        return url;
    }

    public void setImageUrl(String url) {
        this.url = url;
    }

    public String getImageObjectEndpoint() {
        if (imageUrls != null && imageUrls.size() > 1) {
            return imageUrls.get(0).getValue();
        } else {
            return null;
        }
    }


}
