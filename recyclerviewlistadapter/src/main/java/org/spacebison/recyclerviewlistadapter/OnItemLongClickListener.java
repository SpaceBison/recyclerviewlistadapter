package org.spacebison.recyclerviewlistadapter;

/**
 * Created by cmb on 12.12.16.
 */

public interface OnItemLongClickListener<T, V extends BindableViewHolder<T, V>> {
    boolean onItemLongClick(RecyclerViewListAdapter<T, V> adapter, V holder);
}
