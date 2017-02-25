package com.gmail.julianrosser91.alauda;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.gmail.julianrosser91.alauda.api.ApiRequests;
import com.gmail.julianrosser91.alauda.objects.Set;

import java.util.ArrayList;

public class SetListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.set_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_test) {
            loadData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Testing methods
     */

    private void loadData() {
        Snackbar.make(recyclerView, "loading...", Snackbar.LENGTH_SHORT).show();
        ApiRequests.getAllSets(new ApiRequests.APIResponseListener() {
            @Override
            public void onDataLoaded(ArrayList<Set> data) {
                // use data
                Snackbar.make(recyclerView, "Data Loaded", Snackbar.LENGTH_SHORT).show();
                for (Set s : data) {
                    Log.i("DATA", s.getTitle());
                }
            }

            @Override
            public void onFailure(String message) {
                Snackbar.make(recyclerView, "" + message, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
