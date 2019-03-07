package serzhan.com.recyclerview1.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import serzhan.com.recyclerview1.R;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DummyValue> mValues;
    private final List<ViewHolderBinder> mBinders;
    private SparseArray<ViewHolderFactory> mFactoryMap;

    public CustomAdapter() {
        mValues = new ArrayList<>();
        mBinders = new ArrayList<>();
        initFactory();
    }

    private void initFactory() {
        mFactoryMap = new SparseArray<>();
        mFactoryMap.put(ItemTypes.FIRST_VIEW_HOLDER.type, new FirstViewHolderFactory());
        mFactoryMap.put(ItemTypes.SECOND_VIEW_HOLDER.type, new SecondViewHolderFactory());
        mFactoryMap.put(ItemTypes.THIRD_VIEW_HOLDER.type, new ThirdViewHolderFactory());
    }

    public void setItems(List<DummyValue> values) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilCallback(mValues, values));
        diffResult.dispatchUpdatesTo(this);
        mBinders.clear();
        for(DummyValue value : values) {
            mBinders.add(generateBinder(value));
        }
        mValues.clear();
        mValues.addAll(values);
    }

    private ViewHolderBinder generateBinder(DummyValue value) {
        if(value.getType() == ItemTypes.FIRST_VIEW_HOLDER.type) {
            return new FirstViewHolderBinder(value, ItemTypes.FIRST_VIEW_HOLDER.type);
        } else if(value.getType() == ItemTypes.SECOND_VIEW_HOLDER.type) {
            return new SecondViewHolderBinder(value, ItemTypes.SECOND_VIEW_HOLDER.type);
        } else if(value.getType() == ItemTypes.THIRD_VIEW_HOLDER.type) {
            return new ThirdViewHolderBinder(value, ItemTypes.THIRD_VIEW_HOLDER.type);
        }
        return null;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewHolderFactory factory = mFactoryMap.get(i);
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return factory.createViewHolder(viewGroup, inflater);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolderBinder binder = mBinders.get(i);
        if(binder != null) {
            binder.bindViewHolder(viewHolder);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(mValues.get(position).getType() == ItemTypes.FIRST_VIEW_HOLDER.type) {
            return ItemTypes.FIRST_VIEW_HOLDER.type;
        } else if(mValues.get(position).getType() == ItemTypes.SECOND_VIEW_HOLDER.type) {
            return ItemTypes.SECOND_VIEW_HOLDER.type;
        } else if(mValues.get(position).getType() == ItemTypes.THIRD_VIEW_HOLDER.type) {
            return ItemTypes.THIRD_VIEW_HOLDER.type;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class FirstViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        ImageView mImageView;

        public FirstViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.first_text_view);
            mImageView = itemView.findViewById(R.id.first_image_view);
        }
    }

    public static class SecondViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        ImageView mImageView;

        public SecondViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.second_text_view);
            mImageView = itemView.findViewById(R.id.second_image_view);
        }
    }

    public static class ThirdViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;
        ImageView mImageView;

        public ThirdViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.third_text_view);
            mImageView = itemView.findViewById(R.id.third_image_view);
        }
    }
}
