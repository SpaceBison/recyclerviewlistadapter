package org.spacebison.recyclerviewlistadapter;

/**
 * Callback interface for {@link BindableViewHolder} click events.
 *
 * @param <T> Type of data that the holder is bound to.
 * @param <V> Type of view holder.
 */
public interface OnItemLongClickListener<T, V extends BindableViewHolder<T>> {
    /**
     * Called when the view held by the holder is clicked.
     *
     * @param adapter Adapter that the holder belongs to.
     * @param holder Holder holding the clicked view.
     * @return true if the callback consumed the long click, false otherwise.
     */
    boolean onItemLongClick(RecyclerViewListAdapter<T, V> adapter, V holder);
}
