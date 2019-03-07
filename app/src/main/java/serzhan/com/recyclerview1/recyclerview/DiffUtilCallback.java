package serzhan.com.recyclerview1.recyclerview;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class DiffUtilCallback extends DiffUtil.Callback {

    private List<DummyValue> oldList;
    private List<DummyValue> newList;

    public DiffUtilCallback(List<DummyValue> oldList, List<DummyValue> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int i, int i1) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int i, int i1) {
        return oldList.get(i).equals(newList.get(i1));
    }
}
