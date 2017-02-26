package com.gmail.julianrosser91.alauda.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.mvp.SetListMVPInterface;
import com.gmail.julianrosser91.alauda.presenter.SetListPresenter;

import java.util.ArrayList;

public class SetListActivity extends AppCompatActivity implements SetListMVPInterface.View {

    private RecyclerView recyclerView;
    private SetListAdapter setListAdapter;
    private SetListMVPInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initialiseViews();
        attachPresenter();
    }

    private void initialiseViews() {
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

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    private void attachPresenter() {
        presenter = (SetListMVPInterface.Presenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new SetListPresenter();
        }
        presenter.attachView(this);
    }

    @Override
    public void setData(ArrayList<Set> sets) {
        setListAdapter.setData(sets);
    }

    @Override
    public void setMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
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
            presenter.testPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
