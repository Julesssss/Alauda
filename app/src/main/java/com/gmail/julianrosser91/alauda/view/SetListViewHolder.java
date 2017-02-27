package com.gmail.julianrosser91.alauda.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.data.model.Set;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textview_title) TextView mTextViewTitle;
    @BindView(R.id.imageview) ImageView mImageView;

    private View view;

    public SetListViewHolder(View view, View.OnClickListener clickListener) {
        super(view);
        this.view = view;
        view.setOnClickListener(clickListener);
        ButterKnife.bind(this, this.view);
    }

    public void setTag(Set set) {
        view.setTag(set);
    }

    public void setTitle(String title) {
        mTextViewTitle.setText(title);
    }

    public void setImage(String imageUrl) {
        if (imageUrl == null) {
            Picasso.with(mImageView.getContext()).load(R.drawable.placeholder).into(mImageView);
        } else {
            Picasso.with(mImageView.getContext()).load(imageUrl).placeholder(R.drawable.placeholder).into(mImageView);
        }
    }
}