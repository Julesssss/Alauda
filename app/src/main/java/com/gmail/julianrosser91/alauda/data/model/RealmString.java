package com.gmail.julianrosser91.alauda.data.model;

import io.realm.RealmObject;

/*
 * This class is required in order to retrieve the list of urls, as Realm does not allow List<Object>,
 * we must use RealmList<RealmString>. See issue - https://github.com/realm/realm-java/issues/575
 */
public class RealmString extends RealmObject {

    public String value;

    public String getValue() {
        return value;
    }
}
