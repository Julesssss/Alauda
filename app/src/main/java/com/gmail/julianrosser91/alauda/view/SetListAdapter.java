package com.gmail.julianrosser91.alauda.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.julianrosser91.alauda.R;
import com.gmail.julianrosser91.alauda.data.model.Set;

import java.util.ArrayList;

public class SetListAdapter extends RecyclerView.Adapter<SetListViewHolder> {

    private View.OnClickListener onClickListener;
    private ArrayList<Set> mSets;

    public SetListAdapter(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.mSets = new ArrayList<>();
    }

    public void setData(ArrayList<Set> sets) {
        this.mSets = sets;
        notifyDataSetChanged();
    }

    @Override
    public SetListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.set_list_view, parent, false);
        SetListViewHolder vh = new SetListViewHolder(view, onClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(SetListViewHolder holder, int position) {
        Set set = mSets.get(position);
        holder.setTag(set);
        holder.setTitle(set.getTitle());
        holder.setImage(set.getUrl());
    }

    @Override
    public int getItemCount() {
        return mSets.size();
    }
}
