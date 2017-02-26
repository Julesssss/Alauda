package com.gmail.julianrosser91.alauda.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.data.model.Set;

public class SetListViewHolder extends RecyclerView.ViewHolder {

    private View view;
    private TextView mTextViewTitle;
    private ImageView mImageView;

    public SetListViewHolder(View v, View.OnClickListener clickListener) {
        super(v);
        v.setOnClickListener(clickListener);
        this.view = v;
        mImageView = (ImageView) itemView.findViewById(R.id.imageview);
        mTextViewTitle = (TextView) itemView.findViewById(R.id.textview_title);
    }

    public void setTag(Set set) {
        view.setTag(set);
    }

    public void setTitle(String title) {
        mTextViewTitle.setText(title);
    }

    public void setImage(String imageUrl) {
//        Picasso.with()
        // set image mImageView
    }
}