package com.bavers.lamping.lampshopping;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;

public class EnterBarcodeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_BARCODE = "com.bavers.lamping.lampshopping.BARCODE";
    public static final String EXTRA_LAMP = "com.bavers.lamping.lampshopping.LAMP";
    public static final String EXTRA_LAMPS = "com.bavers.lamping.lampshopping.LAMPS";
    public static final String EXTRA_SCANS = "com.bavers.lamping.lampshopping.SCANS";

    private LampRepository lamps;

    private static final int ZXING_CAMERA_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_barcode);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
        }

        lamps = new LampRepository();
        // load lamps data from CSV file
        try {
            lamps.loadFromFile(this);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Bottom navigation
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_history:
                startHistoryActivity();
                break;
            case R.id.action_scan:
                startScanningActivity();
                break;
        }
        return true;
    }

    private void startHistoryActivity() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if(mClss != null) {
//                        Intent intent = new Intent(this, mClss);
//                        startActivity(intent);
//                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private void startScanningActivity() {
        Intent intent = new Intent(this, ScanningActivity.class);
        startActivity(intent);
    }

    /** Called when the user taps the Search button */
    public void searchBarcode(View view) {
        EditText editBarcode = (EditText) findViewById(R.id.editBarcode);
        String barcode = editBarcode.getText().toString();

        if (barcode.equals("11")) {
            startScanningActivity();
            return;
        }

        if (barcode.equals("22")) {
            Intent intent = new Intent(this, HistoryActivity.class);
//            ArrayList<Scan> scans = new ArrayList<Scan>();

//            scans.add(getScanForBarcode("4052899926615"));
//            scans.add(getScanForBarcode("4605645002820"));
//            scans.add(getScanForBarcode("4670000010192"));
//            scans.add(getScanForBarcode("4620004422255")); // 5
//            scans.add(getScanForBarcode("4620754506526")); // 0.2
//            scans.add(getScanForBarcode("1028805821633")); // 2.9 IKEA
//            scans.add(getScanForBarcode("2450003089201")); // no rating

//            intent.putExtra(EXTRA_SCANS, scans);
            startActivity(intent);
            return;
        }

        Lamp lamp = lamps.getLampByBarcode(barcode);
        if (lamp == null) {
            Intent unknownBarcodeIntent = new Intent(this, UnknownBarcodeActivity.class);
            unknownBarcodeIntent.putExtra(EXTRA_BARCODE, barcode);
            startActivity(unknownBarcodeIntent);
        } else {
            Intent lampDetailsIntent = new Intent(this, LampDetailsActivity.class);
            lampDetailsIntent.putExtra(EXTRA_LAMP, lamp);
            startActivity(lampDetailsIntent);
        }
    }

//    private Scan getScanForBarcode(String barcode) {
//        Scan scan = Scan.fromBarcode(barcode);
//        if (lamps.containsBarcode(barcode)) {
//            Lamp lamp = lamps.getLampByBarcode(barcode);
//            scan.setLamp(lamp);
//        }
//        return scan;
//    }

}
