package serzhan.com.recyclerview1.recyclerview;

import android.support.v7.widget.RecyclerView;

public class ThirdViewHolderBinder extends ViewHolderBinder {
    private DummyValue value;

    public ThirdViewHolderBinder(DummyValue value, int viewType) {
        super(viewType);
        this.value = value;
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder) {
        CustomAdapter.ThirdViewHolder ThirdViewHolder = (CustomAdapter.ThirdViewHolder) holder;
        ThirdViewHolder.mTextView.setText(value.getValue());
    }
}
