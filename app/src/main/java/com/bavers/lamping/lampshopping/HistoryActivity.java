package com.bavers.lamping.lampshopping;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;
import java.io.IOException;

public class HistoryActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ScanRepository scans;
    private RecyclerView recyclerView;
    private ScansAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        scans = new ScanRepository();
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

        // Bottom navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_type:
                startEnterBarcodeActivity();
                break;
            case R.id.action_scan:
                startScanningActivity();
                break;
        }
        return true;
    }

    private void startScanningActivity() {
        Intent intent = new Intent(this, ScanningActivity.class);
        startActivity(intent);
    }

    private void startEnterBarcodeActivity() {
        Intent intent = new Intent(this, EnterBarcodeActivity.class);
        startActivity(intent);
    }
}
