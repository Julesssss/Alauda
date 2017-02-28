package com.gmail.julianrosser91.alauda.data.model;

import io.realm.RealmObject;

public class Favourite extends RealmObject {

    private String uid;
    private boolean favourite;

    public Favourite() {
    }

    public Favourite(String uid, boolean favourite) {
        this.uid = uid;
        this.favourite = favourite;
    }

    public String getUid() {
        return uid;
    }

    public boolean isFavourite() {
        return favourite;
    }
}
