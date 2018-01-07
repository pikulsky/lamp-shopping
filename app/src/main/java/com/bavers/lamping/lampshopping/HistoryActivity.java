package com.bavers.lamping.lampshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    //ScanRepository scans;
    ArrayList<Scan> scans;

    private RecyclerView recyclerView;
    private ScansAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        //scans = (ScanRepository) intent.getSerializableExtra(ScanningActivity.EXTRA_SCANS);
        ArrayList<Scan> scans = (ArrayList<Scan>) intent.getSerializableExtra(EnterBarcodeActivity.EXTRA_SCANS);

        recyclerView = (RecyclerView) findViewById(R.id.scansRecyclerView);

        mAdapter = new ScansAdapter(scans);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
