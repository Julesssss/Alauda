package com.gmail.julianrosser91.alauda.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.julianrosser91.alauda.Alauda;
import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.Utils;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.imageview)
    ImageView mImageView;

    @BindView(R.id.textview_title)
    TextView mTextViewTitle;

    @BindView(R.id.textview_summary)
    TextView mTextViewSummary;

    @BindView(R.id.imageview_favourite)
    ImageView mImageViewFavourite;

    private View view;

    public SetListViewHolder(View view, View.OnClickListener clickListener) {
        super(view);
        this.view = view;
        ButterKnife.bind(this, this.view);
        view.setOnClickListener(clickListener);
        mImageViewFavourite.setOnClickListener(clickListener);
    }

    public void setTag(Set set) {
        view.setTag(set);
        mImageViewFavourite.setTag(set);
    }

    public void setTitle(String title) {
        mTextViewTitle.setText(title);
    }

    public void setSummary(String summary) {
        if (Utils.isEmpty(summary)) {
            mTextViewSummary.setVisibility(View.GONE);
        } else {
            mTextViewSummary.setVisibility(View.VISIBLE);
            mTextViewSummary.setText(summary);
        }
    }

    public void setIsFavourite(boolean isFavourite) {
        if (isFavourite) {
            mImageViewFavourite.setImageDrawable(Alauda.getInstance().getResources().getDrawable(R.drawable.ic_favorite_pink_a200_24dp));
        } else {
            mImageViewFavourite.setImageDrawable(Alauda.getInstance().getResources().getDrawable(R.drawable.ic_favorite_border_pink_a200_24dp));
        }
    }

    public void setImage(String imageUrl) {
        if (imageUrl == null) {
            Picasso.with(mImageView.getContext()).load(R.drawable.placeholder).into(mImageView);
        } else {
            Picasso.with(mImageView.getContext()).load(imageUrl).placeholder(R.drawable.placeholder).into(mImageView);
        }
    }
}