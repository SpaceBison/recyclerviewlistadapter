package org.spacebison.recyclerviewlistadapter.sample;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.spacebison.recyclerviewlistadapter.OnItemClickListener;
import org.spacebison.recyclerviewlistadapter.OnItemLongClickListener;
import org.spacebison.recyclerviewlistadapter.RecyclerViewListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final int MAX_ITEMS = 30;
    private final Random mRandom = new Random();
    private SampleRecyclerViewListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new SampleRecyclerViewListAdapter();
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener<Integer, SampleBindableViewHolder>() {
            @Override
            public void onItemClick(RecyclerViewListAdapter<Integer, SampleBindableViewHolder> adapter, SampleBindableViewHolder holder) {
                Snackbar.make(recyclerView, "Clicked " + holder.getText() + "; color: " + adapter.get(holder), Snackbar.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener<Integer, SampleBindableViewHolder>() {
            @Override
            public boolean onItemLongClick(RecyclerViewListAdapter<Integer, SampleBindableViewHolder> adapter, SampleBindableViewHolder holder) {
                Snackbar.make(recyclerView, "Long clicked " + holder.getText() + "; color: " + adapter.get(holder), Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });

        mAdapter.addAll(getRandomData(MAX_ITEMS));
    }

    private List<Integer> getRandomData(int max) {
        int count = mRandom.nextInt(max) + 1;
        ArrayList<Integer> data = new ArrayList<>(count);

        while (count --> 0) {
            data.add(getRandomColor());
        }

        return data;
    }

    private int getRandomColor() {
        return 0xff000000 + mRandom.nextInt(0xffffff);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_swap:
                mAdapter.swap(mLayoutManager.findFirstVisibleItemPosition(), mLayoutManager.findLastCompletelyVisibleItemPosition());
                return true;

            case R.id.action_set_reset:
                mAdapter.set(getRandomData(MAX_ITEMS), RecyclerViewListAdapter.UpdateStrategy.RESET);
                return true;

            case R.id.action_set_update_all:
                mAdapter.set(getRandomData(MAX_ITEMS), RecyclerViewListAdapter.UpdateStrategy.UPDATE_ALL);
                return true;

            case R.id.action_set_update_changed:
                mAdapter.set(getRandomData(MAX_ITEMS), RecyclerViewListAdapter.UpdateStrategy.UPDATE_CHANGED);
                return true;

            default:
                return false;
        }
    }
}
