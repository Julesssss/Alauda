package com.gmail.julianrosser91.alauda.view;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.mvp.SetDetailInterface;
import com.gmail.julianrosser91.alauda.presenter.SetDetailPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetDetailActivity extends AppCompatActivity implements SetDetailInterface.View {

    @BindView(R.id.textview_title)
    TextView textViewTitle;

    @BindView(R.id.textview_body)
    TextView textViewBody;

    @BindView(R.id.textview_quoter)
    TextView textViewQuoter;

    @BindView(R.id.textview_summary)
    TextView textViewSummary;

    private SetDetailInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_detail);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle(title);

        attachPresenter();
    }

    private void attachPresenter() {
        presenter = (SetDetailInterface.Presenter) getLastCustomNonConfigurationInstance();
        if (presenter == null) {
            presenter = new SetDetailPresenter(this, getIntent().getExtras());
        } else {
            presenter.reattachView(this);
        }
    }

    /*
     * This is how we maintain the Presenter state through rotation. Simple but fine for our usage
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

    @Override
    public void setData(final Set set) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textViewTitle.setText(set.getTitle());
                textViewBody.setText(set.getBody());
                textViewSummary.setText(set.getSummary());
                textViewQuoter.setText(set.getQuoter());
            }
        });
    }

    @Override
    public void setMessage(String message) {
        Snackbar.make(textViewTitle, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
