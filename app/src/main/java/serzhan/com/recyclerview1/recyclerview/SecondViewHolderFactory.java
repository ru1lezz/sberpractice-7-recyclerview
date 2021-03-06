package serzhan.com.recyclerview1.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import serzhan.com.recyclerview1.R;

public class SecondViewHolderFactory implements ViewHolderFactory {
    @Override
    public RecyclerView.ViewHolder createViewHolder(ViewGroup parent, LayoutInflater inflater) {
        View itemView = inflater.inflate(R.layout.second_list_item, parent, false);
        RecyclerView.ViewHolder holder = new CustomAdapter.SecondViewHolder(itemView);
        return holder;
    }
}
