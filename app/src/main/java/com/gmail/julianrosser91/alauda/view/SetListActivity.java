package com.gmail.julianrosser91.alauda.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.mvp.SetListInterface;
import com.gmail.julianrosser91.alauda.presenter.SetListPresenter;

import java.util.ArrayList;

public class SetListActivity extends AppCompatActivity implements SetListInterface.View {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SetListAdapter setListAdapter;
    private SetListInterface.Presenter presenter;

    /*
     * Lifecycle methods
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initialiseViews();
        attachPresenter();
    }

    private void initialiseViews() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        setListAdapter = new SetListAdapter(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSetClicked(view);
            }
        });
        recyclerView.setAdapter(setListAdapter);
    }

    private void attachPresenter() {
        presenter = (SetListInterface.Presenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new SetListPresenter(this);
        } else {
            presenter.reattachView(this);
        }
    }

    /*
        This is how we maintain the Presenter state through rotation
     */
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    /*
     * Interface methods called from SetListPresenter
     */

    @Override
    public void setData(ArrayList<Set> sets) {
        setListAdapter.setData(sets);
    }

    @Override
    public void setMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(final Boolean visible) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressBar != null) {
                    if (visible) {
                        progressBar.setVisibility(View.VISIBLE);
                    } else {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /*
     * Options menu methods
     */

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
            presenter.testPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
