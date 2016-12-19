package org.spacebison.recyclerviewlistadapter.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.spacebison.recyclerviewlistadapter.OnItemClickListener;
import org.spacebison.recyclerviewlistadapter.OnItemLongClickListener;
import org.spacebison.recyclerviewlistadapter.RecyclerViewListAdapter;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private final Random mRandom = new Random();
    private RecyclerView mRecyclerView;
    private SampleRecyclerViewListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new SampleRecyclerViewListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener<Integer, SampleBindableViewHolder>() {
            @Override
            public void onItemClick(RecyclerViewListAdapter<Integer, SampleBindableViewHolder> adapter, SampleBindableViewHolder holder) {
                Toast.makeText(getApplicationContext(), "Clicked " + holder.getText() + "; color: " + adapter.get(holder), Toast.LENGTH_LONG).show();
            }
        });

        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener<Integer, SampleBindableViewHolder>() {
            @Override
            public boolean onItemLongClick(RecyclerViewListAdapter<Integer, SampleBindableViewHolder> adapter, SampleBindableViewHolder holder) {
                Toast.makeText(getApplicationContext(), "Long clicked " + holder.getText() + "; color: " + adapter.get(holder), Toast.LENGTH_LONG).show();
                return true;
            }
        });

        for (int i = 0; i < 30; ++i) {
            mAdapter.add(0xff000000 + mRandom.nextInt(0xffffff));
        }
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

            default:
                return false;
        }
    }
}
