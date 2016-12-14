package org.spacebison.recyclerviewlistadapter.sample;

import android.view.ViewGroup;

import org.spacebison.recyclerviewlistadapter.RecyclerViewListAdapter;

/**
 * Sample {@link RecyclerViewListAdapter} implementation.
 */
class SampleRecyclerViewListAdapter extends RecyclerViewListAdapter<Integer, SampleBindableViewHolder> {
    @Override
    public SampleBindableViewHolder onCreateBindableViewHolder(ViewGroup parent, int viewType) {
        /* Instantiate view holder. That's it! */
        return new SampleBindableViewHolder(parent);
    }
}
