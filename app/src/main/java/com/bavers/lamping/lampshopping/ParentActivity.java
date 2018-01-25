package com.bavers.lamping.lampshopping;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ParentActivity
    extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener
{
    public static final String EXTRA_BARCODE    = "com.bavers.lamping.lampshopping.BARCODE";
    public static final String EXTRA_LAMP       = "com.bavers.lamping.lampshopping.LAMP";
    public static final String EXTRA_LAMPS      = "com.bavers.lamping.lampshopping.LAMPS";
    public static final String EXTRA_SCANS      = "com.bavers.lamping.lampshopping.SCANS";

    // lamps
    protected LampRepository lamps;

    // scans
    protected ScanRepository scans;

    protected void initializeBottomNavigation(int selectedMenuItemId)
    {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        // set "selected" menu item
        Menu menu = bottomNavigationView.getMenu();
        menu.findItem(selectedMenuItemId).setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    protected void initializeScans()
    {
        Intent intent = getIntent();

        scans = (ScanRepository) intent.getSerializableExtra(ParentActivity.EXTRA_SCANS);
        if (scans == null) {

            Log.d("Init", "Scans not passed, loading from file...");
            System.out.printf("Scans not passed, loading from file...");

            scans = new ScanRepository();
            try {
                scans.loadFromFile(this);
            } catch (FileNotFoundException e) {
                // initially there is no file with scans
                // but, what if other reasons for missing file?
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (ClassNotFoundException e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void initializeLamps()
    {
        Intent intent = getIntent();

        lamps = (LampRepository) intent.getSerializableExtra(ParentActivity.EXTRA_LAMPS);
        if (lamps == null) {

            Log.d("Init", "Lamps not passed, loading from file...");
            System.out.printf("Lamps not passed, loading from file...");

            // load lamps data from CSV file
            lamps = new LampRepository();
            try {
                lamps.loadFromFile(this);
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_scan:
                startScanningActivity();
                break;
            case R.id.action_history:
                startHistoryActivity();
                break;
            case R.id.action_type:
                startEnterBarcodeActivity();
                break;
        }
        return true;
    }

    protected void startScanningActivity() {
        Intent intent = new Intent(this, ScanningActivity.class);
        intent.putExtra(ParentActivity.EXTRA_SCANS, scans);
        intent.putExtra(ParentActivity.EXTRA_LAMPS, lamps);
        startActivity(intent);
    }

    protected void startEnterBarcodeActivity() {
        Intent intent = new Intent(this, EnterBarcodeActivity.class);
        intent.putExtra(ParentActivity.EXTRA_SCANS, scans);
        intent.putExtra(ParentActivity.EXTRA_LAMPS, lamps);
        startActivity(intent);
    }

    protected void startHistoryActivity() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra(ParentActivity.EXTRA_SCANS, scans);
        intent.putExtra(ParentActivity.EXTRA_LAMPS, lamps);
        startActivity(intent);
    }

    protected void saveScans()
    {
        try {
            scans.saveToFile(this);
        } catch (IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    protected void processBarcode(String barcode)
    {
        Scan scan = Scan.fromBarcode(barcode);
        if (lamps.containsBarcode(barcode)){
            Lamp lamp = lamps.getLampByBarcode(barcode);
            scan.setLamp(lamp);

            String message = "Found lamp for barcode " + barcode;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            String message = "Lamp for barcode " + barcode + " not found";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        scans.addScan(scan);

        saveScans();
    }
}
