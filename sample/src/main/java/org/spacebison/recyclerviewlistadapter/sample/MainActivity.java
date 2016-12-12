package org.spacebison.recyclerviewlistadapter.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.spacebison.recyclerviewlistadapter.BindableViewHolder;
import org.spacebison.recyclerviewlistadapter.OnItemClickListener;
import org.spacebison.recyclerviewlistadapter.OnItemLongClickListener;
import org.spacebison.recyclerviewlistadapter.RecyclerViewListAdapter;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final Random mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SampleAdapter adapter = new SampleAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener<Integer, SampleViewHolder>() {
            @Override
            public void onItemClick(RecyclerViewListAdapter<Integer, SampleViewHolder> adapter, SampleViewHolder holder) {
                Toast.makeText(getApplicationContext(), "Clicked " + holder.getText() + "; color: " + adapter.get(holder), Toast.LENGTH_LONG).show();
            }
        });

        adapter.setOnItemLongClickListener(new OnItemLongClickListener<Integer, SampleViewHolder>() {
            @Override
            public boolean onItemLongClick(RecyclerViewListAdapter<Integer, SampleViewHolder> adapter, SampleViewHolder holder) {
                Toast.makeText(getApplicationContext(), "Long clicked " + holder.getText() + "; color: " + adapter.get(holder), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        for (int i = 0; i < 30; ++i) {
            adapter.add(0xff000000 + mRandom.nextInt(0xffffff));
        }
    }

    private static class SampleAdapter extends RecyclerViewListAdapter<Integer, SampleViewHolder> {
        @Override
        public SampleViewHolder onCreateBindableViewHolder(ViewGroup parent, int viewType) {
            return new SampleViewHolder(parent);
        }
    }

    public static class SampleViewHolder extends BindableViewHolder<Integer, SampleViewHolder> {
        private TextView mTextView;

        public SampleViewHolder(@NonNull ViewGroup parent) {
            super(parent, android.R.layout.simple_list_item_1);
            mTextView = (TextView) findViewById(android.R.id.text1);
        }

        @Override
        public void bind(Integer data) {
            mTextView.setBackgroundColor(data);
            mTextView.setTextColor((data & 0xFF000000) | (~data & 0x00FFFFFF)); // invert
            mTextView.setText("#" + Integer.toHexString(data));
        }

        public CharSequence getText() {
            return mTextView.getText();
        }
    }
}
