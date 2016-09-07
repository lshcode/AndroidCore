package com.lsh.lib.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * RecycleView适配器基类
 * author:liush
 * version: 2016/6/1  0:39
 */
public abstract class BAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {
    public List<T> list = new ArrayList<>();
    public Context context;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list.clear();
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void setMore(List<T> list) {
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public View createView(Context context, int layoutId) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return view;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public T getItem(int position) {
        return list.get(position);
    }

    /**
     * 删除磨一位置数据
     *
     * @param pos
     */
    public void delete(int pos) {
        if (pos < 0) {
            return;
        }
        notifyItemRemoved(pos);
        list.remove(pos);
        notifyItemRangeChanged(pos, getItemCount());
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    /**
     * 更新某一位置数据
     *
     * @param pos
     */
    public void updateInPosition(int pos) {
        notifyItemChanged(pos);
    }
}
