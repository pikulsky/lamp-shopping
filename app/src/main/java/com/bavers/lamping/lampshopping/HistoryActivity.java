package com.bavers.lamping.lampshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    ScanRepository scans = new ScanRepository();
    //ArrayList<Scan> scans;

    private RecyclerView recyclerView;
    private ScansAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();

        //scans = (ScanRepository) intent.getSerializableExtra(ScanningActivity.EXTRA_SCANS);
        // ArrayList<Scan> scans = (ArrayList<Scan>) intent.getSerializableExtra(EnterBarcodeActivity.EXTRA_SCANS);


        try {
            scans.loadFromFile(this);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        recyclerView = (RecyclerView) findViewById(R.id.scansRecyclerView);

        mAdapter = new ScansAdapter(scans.getScans());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
}
