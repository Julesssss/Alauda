package com.gmail.julianrosser91.alauda;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.julianrosser91.alauda.objects.Set;

public class SetViewHolder extends RecyclerView.ViewHolder {

    private View view;
    private TextView mTextviewTitle;
    private ImageView mImageView;

    public SetViewHolder(View v, View.OnClickListener clickListener) {
        super(v);
        v.setOnClickListener(clickListener);
        this.view = v;
        mImageView = (ImageView) itemView.findViewById(R.id.imageview);
        mTextviewTitle = (TextView) itemView.findViewById(R.id.textview_title);
    }

    public void setTag(Set set) {
        view.setTag(set);
    }

    public void setTitle(String title) {
        mTextviewTitle.setText(title);
    }

    public void setImage(String imageUrl) {
        // set image mImageView
    }
}