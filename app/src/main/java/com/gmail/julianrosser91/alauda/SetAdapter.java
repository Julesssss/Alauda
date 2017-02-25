package com.gmail.julianrosser91.alauda;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.julianrosser91.alauda.objects.Set;

import java.util.ArrayList;

public class SetAdapter extends RecyclerView.Adapter<SetViewHolder> {

    private View.OnClickListener onClickListener;
    private ArrayList<Set> mSets;

    public SetAdapter(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.mSets = new ArrayList<>();
    }

    public void setData(ArrayList<Set> sets) {
        this.mSets = sets;
        notifyDataSetChanged();
    }

    @Override
    public SetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.set_list_view, parent, false);
        SetViewHolder vh = new SetViewHolder(view, onClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(SetViewHolder holder, int position) {
        Set set = mSets.get(position);
        holder.setTag(set);
        holder.setTitle(set.getTitle());
//        holder.setTitle(set.getImage());
    }

    @Override
    public int getItemCount() {
        return mSets.size();
    }
}
