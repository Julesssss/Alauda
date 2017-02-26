package com.gmail.julianrosser91.alauda.data.model;

import io.realm.RealmObject;

/*
 * This object holds the Image object returned from server. Rather than maintaining a separate table
 * for images, we take the url and add it to the Set object.
 */
public class ImageObject extends RealmObject {

    private String url;
    private String uid;

    public String getUid() {
        return uid;
    }

    public String getUrl() {
        return url;
    }
}
