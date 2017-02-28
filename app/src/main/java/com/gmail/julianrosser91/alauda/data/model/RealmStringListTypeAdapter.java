package com.gmail.julianrosser91.alauda.data.model;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import io.realm.RealmList;
import io.realm.internal.IOException;

/*
 * This is required because using RealmString causes problems when using Retrofit & GSON. We must
 * let GSON know that RealmString is just a wrapper class. See - https://nullpointer.wtf/android/using-retrofit-realm-parceler/
 */
public class RealmStringListTypeAdapter extends TypeAdapter<RealmList<RealmString>> {

    public static final TypeAdapter<RealmList<RealmString>> INSTANCE =
            new RealmStringListTypeAdapter().nullSafe();

    private RealmStringListTypeAdapter() {
    }

    @Override
    public void write(JsonWriter out, RealmList<RealmString> src) throws IOException, java.io.IOException {
        out.beginArray();
        for (RealmString realmString : src) {
            out.value(realmString.value);
        }
        out.endArray();
    }

    @Override
    public RealmList<RealmString> read(JsonReader in) throws IOException, java.io.IOException {
        RealmList<RealmString> realmStrings = new RealmList<>();
        in.beginArray();
        while (in.hasNext()) {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
            } else {
                RealmString realmString = new RealmString();
                realmString.value = in.nextString();
                realmStrings.add(realmString);
            }
        }
        in.endArray();
        return realmStrings;
    }
}
