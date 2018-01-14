package com.bavers.lamping.lampshopping;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class HistoryActivity extends ParentActivity {

    private RecyclerView recyclerView;
    private ScansAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        // load lamps
        initializeLamps();

        // load scans
        initializeScans();

        // setup bottom navigation
        initializeBottomNavigation(R.id.action_history);

        recyclerView = (RecyclerView) findViewById(R.id.scansRecyclerView);
        mAdapter = new ScansAdapter(scans.getScans());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
