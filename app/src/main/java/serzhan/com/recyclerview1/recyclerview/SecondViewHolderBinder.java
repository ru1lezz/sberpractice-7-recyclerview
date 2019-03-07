package serzhan.com.recyclerview1.recyclerview;

import android.support.v7.widget.RecyclerView;

public class SecondViewHolderBinder extends ViewHolderBinder {
    private DummyValue value;

    public SecondViewHolderBinder(DummyValue value, int viewType) {
        super(viewType);
        this.value = value;
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder) {
        CustomAdapter.SecondViewHolder SecondViewHolder = (CustomAdapter.SecondViewHolder) holder;
        SecondViewHolder.mTextView.setText(value.getValue());
    }
}
