# RecyclerViewListAdapter

[![Release](https://jitpack.io/v/SpaceBison/recyclerviewlistadapter.svg)](https://jitpack.io/#SpaceBison/recyclerviewlistadapter) [![Build Status](https://travis-ci.org/SpaceBison/recyclerviewlistadapter.svg?branch=master)](https://travis-ci.org/SpaceBison/recyclerviewlistadapter)

RecyclerView.Adapter subclass that implements java.util.List and provides a simple click and
long click callback interface.

Installation
------------

Download
----------

```groovy

    allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}

```

```groovy

	dependencies {
		compile 'com.github.SpaceBison:recyclerviewlistadapter:0.3'
	}

```

Example
-------

Example ViewHolder:

```Java
public class SampleBindableViewHolder extends BindableViewHolder<Integer> {
    private TextView mTextView;

    public SampleBindableViewHolder(@NonNull ViewGroup parent) {
        /* Base class constructor inflates the view. */
        super(parent, android.R.layout.simple_list_item_1);

        /* You can get view references using BindableViewHolder.findViewById().
         * The inflated view can also be accessed using the itemView field. */
        mTextView = (TextView) findViewById(android.R.id.text1);
    }

    @Override
    public void bind(Integer data) {
        /* Bind the view holder using data from adapter. */
        mTextView.setBackgroundColor(data);
        mTextView.setTextColor((data & 0xFF000000) | (~data & 0x00FFFFFF)); // invert
        mTextView.setText("#" + Integer.toHexString(data));
    }

    /* Optional public methods can be called from click listeners. */
    public CharSequence getText() {
        return mTextView.getText();
    }
}
```


Example Adapter:

```Java
class SampleRecyclerViewListAdapter extends RecyclerViewListAdapter<Integer, SampleBindableViewHolder> {
    @Override
    public SampleBindableViewHolder onCreateBindableViewHolder(ViewGroup parent, int viewType) {
        /* Instantiate view holder. That's it! */
        return new SampleBindableViewHolder(parent);
    }
}
```


Usage:

```Java
RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
recyclerView.setLayoutManager(new LinearLayoutManager(this));

SampleRecyclerViewListAdapter adapter = new SampleRecyclerViewListAdapter();
recyclerView.setAdapter(adapter);

adapter.setOnItemClickListener(new OnItemClickListener<Integer, SampleBindableViewHolder>() {
    @Override
    public void onItemClick(RecyclerViewListAdapter<Integer, SampleBindableViewHolder> adapter, SampleBindableViewHolder holder) {
        Toast.makeText(getApplicationContext(), "Clicked " + holder.getText() + "; color: " + adapter.get(holder), Toast.LENGTH_LONG).show();
    }
});

adapter.setOnItemLongClickListener(new OnItemLongClickListener<Integer, SampleBindableViewHolder>() {
    @Override
    public boolean onItemLongClick(RecyclerViewListAdapter<Integer, SampleBindableViewHolder> adapter, SampleBindableViewHolder holder) {
        Toast.makeText(getApplicationContext(), "Long clicked " + holder.getText() + "; color: " + adapter.get(holder), Toast.LENGTH_LONG).show();
        return true;
    }
});
```
