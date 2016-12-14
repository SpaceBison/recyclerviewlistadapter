package org.spacebison.recyclerviewlistadapter.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

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

        for (int i = 0; i < 30; ++i) {
            adapter.add(0xff000000 + mRandom.nextInt(0xffffff));
        }
    }

}
