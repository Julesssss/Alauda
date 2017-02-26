package com.gmail.julianrosser91.alauda.data;

import android.content.Intent;
import android.net.Uri;

import com.gmail.julianrosser91.alauda.Alauda;

import java.io.File;

import io.realm.Realm;

public class DatabaseHelper {

    /*
     *  Use this to export Realm DB so we can view it with Realm Browser. Copied from here -
     *  http://stackoverflow.com/questions/28478987/how-to-view-my-realm-file-in-the-realm-browser
     */
    public static void exportDatabase() {

        // init realm
        Realm realm = Realm.getDefaultInstance();

        File exportRealmFile = null;
        // get or create an "export.realm" file
        exportRealmFile = new File(Alauda.getInstance().getExternalCacheDir(), "export.realm");

        // if "export.realm" already exists, delete
        exportRealmFile.delete();

        // copy current realm to "export.realm"
        realm.writeCopyTo(exportRealmFile);

        realm.close();

        // init email intent and add export.realm as attachment
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_EMAIL, "EMAIL");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Realm Database");
        intent.putExtra(Intent.EXTRA_TEXT, " ");
        Uri u = Uri.fromFile(exportRealmFile);
        intent.putExtra(Intent.EXTRA_STREAM, u);

        // start email intent
        Alauda.getInstance().startActivity(Intent.createChooser(intent, "Export Realm Database"));
    }
}
