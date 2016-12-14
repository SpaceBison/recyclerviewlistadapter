package org.spacebison.recyclerviewlistadapter.sample;

import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import org.spacebison.recyclerviewlistadapter.BindableViewHolder;

/**
 * Sample {@link BindableViewHolder} implementation.
 */
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
