package org.spacebison.recyclerviewlistadapter;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A {@link RecyclerView.ViewHolder} that can be bound using {@link #bind(Object)}.
 *
 * @param <T> Type of objects represented by the holder.
 */
abstract public class BindableViewHolder<T> extends RecyclerView.ViewHolder {
    private OnClickListener<T> mOnClickListener;

    /**
     * Constructor.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param layoutRes Layout resource used to inflate the view.
     */
    public BindableViewHolder(@NonNull ViewGroup parent, @LayoutRes int layoutRes) {
        super(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onViewHolderClick(BindableViewHolder.this);
                }
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return mOnClickListener != null && mOnClickListener.onViewHolderLongClick(BindableViewHolder.this);
            }
        });
    }

    /**
     * Look for a child view with the given id. If this view has the given id, return this view.
     *
     * @param viewId The id to search for.
     * @return The view that has the given id in the hierarchy or null
     */
    final public View findViewById(@IdRes int viewId) {
        return itemView.findViewById(viewId);
    }

    /**
     * Binds holder with given object.
     *
     * @param data Object that the holder should be bound to.
     */
    abstract public void bind(T data);

    /**
     * Register a callback to be invoked when the item view is clicked.
     *
     * @param onClickListener The callback.
     */
    void setOnClickListener(OnClickListener<T> onClickListener) {
        mOnClickListener = onClickListener;
    }

    /**
     * Internal click listener.
     *
     * @param <T> Data type.
     */
    interface OnClickListener<T> {
        void onViewHolderClick(BindableViewHolder<T> holder);
        boolean onViewHolderLongClick(BindableViewHolder<T> holder);
    }
}
