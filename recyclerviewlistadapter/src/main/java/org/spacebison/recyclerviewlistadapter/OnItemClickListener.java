package org.spacebison.recyclerviewlistadapter;

/**
 * Created by cmb on 12.12.16.
 */

public interface OnItemClickListener<T, V extends BindableViewHolder<T, V>> {
    void onItemClick(RecyclerViewListAdapter<T, V> adapter, V holder);
}
