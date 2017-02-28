package com.gmail.julianrosser91.alauda.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.Utils;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.gmail.julianrosser91.alauda.mvp.SetDetailInterface;
import com.gmail.julianrosser91.alauda.presenter.SetDetailPresenter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetDetailActivity extends AppCompatActivity implements SetDetailInterface.View {

    @BindView(R.id.imageview_detail)
    ImageView imageViewDetail;

    @BindView(R.id.textview_body)
    TextView textViewBody;

    @BindView(R.id.textview_quoter)
    TextView textViewQuoter;

    @BindView(R.id.textview_summary)
    TextView textViewSummary;

    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    private SetDetailInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_detail);

        initialiseViews();
        attachPresenter();
    }

    private void initialiseViews() {
        ButterKnife.bind(this);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onFavouriteToggled();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(""); // Necessary so that future setTitle's won't be ignored
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
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(set.getTitle());
                }
                textViewBody.setText(set.getBody());
                textViewSummary.setText(set.getSummary());
                setQuoterView(set.getQuoter());
                setIsFavourite(set.isFavourite());
                Picasso.with(SetDetailActivity.this).load(set.getImageUrl()).into(imageViewDetail);
            }
        });
    }

    /*
     * Should use getDrawable(drawable, theme) here instead
     */
    @Override
    public void setIsFavourite(final boolean isFavourite) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFavourite) {
                    floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                } else {
                    floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                }
            }
        });
    }

    private void setQuoterView(String quoter) {
        if (Utils.isEmpty(quoter)) {
            textViewQuoter.setVisibility(View.GONE);
            textViewQuoter.setText("");
        } else {
            textViewQuoter.setText(quoter);
            textViewQuoter.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setMessage(String message) {
        Snackbar.make(textViewBody, message, Snackbar.LENGTH_SHORT).show();
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
