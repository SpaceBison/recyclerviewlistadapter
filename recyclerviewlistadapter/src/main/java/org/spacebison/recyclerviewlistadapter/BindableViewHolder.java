package org.spacebison.recyclerviewlistadapter;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cmb on 12.12.16.
 */

abstract public class BindableViewHolder<T, V extends BindableViewHolder<T, V>> extends RecyclerView.ViewHolder {
    private OnClickListener<T, V> mOnClickListener;

    public BindableViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutRes) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onViewHolderClick((V) BindableViewHolder.this);
                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return mOnClickListener != null && mOnClickListener.onViewHolderLongClick((V) BindableViewHolder.this);
            }
        });
    }

    final public View findViewById(@IdRes int viewId) {
        return itemView.findViewById(viewId);
    }

    abstract public void bind(T data);

    void setOnClickListener(OnClickListener<T, V> onClickListener) {
        mOnClickListener = onClickListener;
    }

    interface OnClickListener<T, V extends BindableViewHolder<T, V>> {
        void onViewHolderClick(V holder);
        boolean onViewHolderLongClick(V holder);
    }
}
