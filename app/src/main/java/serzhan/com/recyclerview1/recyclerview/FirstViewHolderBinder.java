package serzhan.com.recyclerview1.recyclerview;

import android.support.v7.widget.RecyclerView;

public class FirstViewHolderBinder extends ViewHolderBinder {

    private DummyValue value;

    public FirstViewHolderBinder(DummyValue value, int viewType) {
        super(viewType);
        this.value = value;
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder) {
        CustomAdapter.FirstViewHolder firstViewHolder = (CustomAdapter.FirstViewHolder) holder;
        firstViewHolder.mTextView.setText(value.getValue());
    }
}
