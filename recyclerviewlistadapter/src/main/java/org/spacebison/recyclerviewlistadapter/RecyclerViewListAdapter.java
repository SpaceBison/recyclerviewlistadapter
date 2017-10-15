package org.spacebison.recyclerviewlistadapter;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * An {@link RecyclerView.Adapter} representing a {@link List} of objects.
 *
 * @param <T> Type of objects being held in the list.
 * @param <V> Type of the ViewHolder representing the items.
 */
public abstract class RecyclerViewListAdapter<T, V extends BindableViewHolder<T>> extends RecyclerView.Adapter<V> implements List<T> {
    private final LinkedList<T> mData = new LinkedList<>();
    private OnItemClickListener<T, V> mOnItemClickListener;
    private OnItemLongClickListener<T, V> mOnItemLongClickListener;
    private UpdateStrategy mUpdateStrategy = UpdateStrategy.UPDATE_ALL;

    /**
     * Returns the element represented by the passed view holder.
     *
     * @param viewHolder view holder representing the element
     * @return T the element represented by the view holder
     */
    public T get(V viewHolder) {
        return mData.get(viewHolder.getAdapterPosition());
    }

    /**
     * Creates an instance of a view holder. See {@link #onCreateViewHolder(ViewGroup, int)}.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new BindableViewHolder that holds a View of the given view type.
     */
    public abstract V onCreateBindableViewHolder(ViewGroup parent, int viewType);

    @Override
    public final V onCreateViewHolder(ViewGroup parent, int viewType) {
        final V holder = onCreateBindableViewHolder(parent, viewType);

        holder.setOnClickListener(new BindableViewHolder.OnClickListener<T>() {
            @Override
            public void onViewHolderClick(BindableViewHolder<T> holder) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(RecyclerViewListAdapter.this, (V) holder);
                }
            }

            @Override
            public boolean onViewHolderLongClick(BindableViewHolder<T> holder) {
                return mOnItemLongClickListener != null && mOnItemLongClickListener.onItemLongClick(RecyclerViewListAdapter.this, (V) holder);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return size();
    }

    @UiThread
    public void set(List<T> items) {
        set(items, mUpdateStrategy);
    }

    @UiThread
    public void set(List<T> items, UpdateStrategy updateStrategy) {
        switch (updateStrategy) {
            case RESET:
                setReset(items);
                break;
            case UPDATE_ALL:
                setUpdateAll(items);
                break;
            case UPDATE_CHANGED:
                setUpdateChanged(items);
                break;
        }
    }

    /**
     * Registers a callback that will be called when an item is clicked.
     *
     * @param onItemClickListener The callback.
     */
    public void setOnItemClickListener(OnItemClickListener<T, V> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * Registers a callback that will be called when an item is long clicked.
     *
     * @param onItemLongClickListener The callback.
     */
    public void setOnItemLongClickListener(OnItemLongClickListener<T, V> onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public int size() {
        return mData.size();
    }

    @Override
    public boolean isEmpty() {
        return mData.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return mData.contains(o);
    }

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return listIterator();
    }

    @NonNull
    @Override
    public Object[] toArray() {
        return mData.toArray();
    }

    @NonNull
    @Override
    public <T1> T1[] toArray(@NonNull T1[] t1s) {
        return mData.toArray(t1s);
    }

    @UiThread
    @Override
    public boolean add(T t) {
        boolean modified = mData.add(t);
        notifyItemInserted(mData.size() - 1);
        return modified;
    }

    @UiThread
    @Override
    public boolean remove(Object o) {
        final int index = mData.indexOf(o);

        if (index >= 0) {
            mData.remove(index);
            notifyItemRemoved(index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsAll(@NonNull Collection<?> collection) {
        return mData.containsAll(collection);
    }

    @UiThread
    @Override
    public boolean addAll(@NonNull Collection<? extends T> collection) {
        final int oldSize = mData.size();
        boolean modified = mData.addAll(collection);
        notifyItemRangeInserted(oldSize, collection.size());
        return modified;
    }

    @UiThread
    @Override
    public boolean addAll(int i, @NonNull Collection<? extends T> collection) {
        boolean modified = mData.addAll(i, collection);
        notifyItemRangeInserted(i, collection.size());
        return modified;
    }

    @UiThread
    @Override
    public boolean removeAll(@NonNull Collection<?> collection) {
        int oldSize = mData.size();
        final Iterator<T> iterator = mData.iterator();
        int index = 0;

        while (iterator.hasNext()) {
            if (collection.contains(iterator.next())) {
                iterator.remove();
                notifyItemRemoved(index);
            } else {
                ++index;
            }
        }

        return mData.size() < oldSize;
    }

    @UiThread
    @Override
    public boolean retainAll(@NonNull Collection<?> collection) {
        int oldSize = mData.size();
        final Iterator<T> iterator = mData.iterator();
        int index = 0;

        while (iterator.hasNext()) {
            if (!collection.contains(iterator.next())) {
                iterator.remove();
                notifyItemRemoved(index);
            } else {
                ++index;
            }
        }

        return mData.size() < oldSize;
    }

    @UiThread
    @Override
    public void clear() {
        int oldSize = mData.size();
        mData.clear();
        notifyItemRangeRemoved(0, oldSize);
    }

    @Override
    public T get(int i) {
        return mData.get(i);
    }

    @UiThread
    @Override
    public T set(int i, T t) {
        final T oldValue = mData.set(i, t);
        notifyItemChanged(i);
        return oldValue;
    }

    @UiThread
    @Override
    public void add(int i, T t) {
        mData.add(i, t);
        notifyItemInserted(i);
    }

    @UiThread
    @Override
    public T remove(int i) {
        final T removed = mData.remove(i);
        notifyItemRemoved(i);
        return removed;
    }

    @Override
    public int indexOf(Object o) {
        return mData.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return mData.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return new AdapterIterator(mData.listIterator());
    }

    @NonNull
    @Override
    public ListIterator<T> listIterator(int i) {
        return new AdapterIterator(mData.listIterator(i));
    }

    @NonNull
    @Override
    public List<T> subList(int i, int i1) {
        return mData.subList(i, i1);
    }

    @UiThread
    public void swap(int i1, int i2) {
        int first, second;
        if (i1 < i2) {
            first = i1;
            second = i2;
        } else {
            first = i2;
            second = i1;
        }

        mData.add(first, mData.remove(second));
        mData.add(second, mData.remove(first + 1));
        notifyItemChanged(first);
        notifyItemChanged(second);
    }

    @UiThread
    private void setReset(List<T> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    private void setUpdateAll(List<T> items) {
        final int oldSize = mData.size();
        final int newSize = items.size();
        final int sizeDiff = newSize - oldSize;
        final int changed = Math.min(oldSize, newSize);

        mData.clear();
        mData.addAll(items);

        notifyItemRangeChanged(0, changed);

        if (sizeDiff >= 0) {
            notifyItemRangeInserted(oldSize, sizeDiff);
        } else {
            notifyItemRangeRemoved(newSize, -sizeDiff);
        }
    }

    @UiThread
    private void setUpdateChanged(List<T> items) {
        final int oldSize = mData.size();
        final int newSize = items.size();
        final int sizeDiff = newSize - oldSize;
        final int changed = Math.min(oldSize, newSize);

        final HashSet<Integer> changedItems = new HashSet<>(changed);
        for (int i = 0; i < changed; ++i) {
            if (!mData.get(i).equals(items.get(i))) {
                changedItems.add(i);
            }
        }

        mData.clear();
        mData.addAll(items);

        for (Integer changedItem : changedItems) {
            notifyItemChanged(changedItem);
        }

        if (sizeDiff >= 0) {
            notifyItemRangeInserted(oldSize, sizeDiff);
        } else {
            notifyItemRangeRemoved(newSize, -sizeDiff);
        }
    }

    public enum UpdateStrategy {
        RESET,
        UPDATE_ALL,
        UPDATE_CHANGED;
    }

    private class AdapterIterator implements ListIterator<T> {
        private final ListIterator<T> mDelegate;
        private int mCurrentIndex;

        private AdapterIterator(ListIterator<T> delegate) {
            mDelegate = delegate;
        }

        @Override
        public boolean hasNext() {
            return mDelegate.hasNext();
        }

        @Override
        public T next() {
            mCurrentIndex = nextIndex();
            return mDelegate.next();
        }

        @Override
        public boolean hasPrevious() {
            return mDelegate.hasPrevious();
        }

        @Override
        public T previous() {
            mCurrentIndex = previousIndex();
            return mDelegate.previous();
        }

        @Override
        public int nextIndex() {
            return mDelegate.nextIndex();
        }

        @Override
        public int previousIndex() {
            return mDelegate.previousIndex();
        }

        @UiThread
        @Override
        public void remove() {
            mDelegate.remove();
            notifyItemRemoved(mCurrentIndex);
        }

        @UiThread
        @Override
        public void set(T t) {
            mDelegate.set(t);
            notifyItemChanged(mCurrentIndex);
        }

        @UiThread
        @Override
        public void add(T t) {
            mDelegate.add(t);
            notifyItemInserted(mDelegate.nextIndex());
        }
    }
}

