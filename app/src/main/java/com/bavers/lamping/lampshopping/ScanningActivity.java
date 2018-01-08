package com.bavers.lamping.lampshopping;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.zxing.Result;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanningActivity extends BaseScannerActivity implements ZXingScannerView.ResultHandler {

    public static final String EXTRA_SCANS = "com.bavers.lamping.lampshopping.SCANS";

    private ZXingScannerView mScannerView;

    private LampRepository lamps;

    /**
     * List of scans
     */
    private ScanRepository scans;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scanning);
        setupToolbar();

        Intent intent = getIntent();
        lamps = (LampRepository) intent.getSerializableExtra(EnterBarcodeActivity.EXTRA_LAMPS);

        scans = new ScanRepository();

        try {
            scans.loadFromFile(this);

            // TODO remove
            scans.addScan(getScanForBarcode("4052899926615"));
            scans.addScan(getScanForBarcode("4605645002820"));
            scans.addScan(getScanForBarcode("4670000010192"));
            scans.addScan(getScanForBarcode("4620004422255")); // 5
            scans.addScan(getScanForBarcode("4620754506526")); // 0.2
            scans.addScan(getScanForBarcode("1028805821633")); // 2.9 IKEA
            scans.addScan(getScanForBarcode("2450003089201")); // no rating

        } catch (IOException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (ClassNotFoundException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
    }

    // TODO remove
    private Scan getScanForBarcode(String barcode) {

        Scan scan = Scan.fromBarcode(barcode);
        if (lamps.containsBarcode(barcode)) {
            Lamp lamp = lamps.getLampByBarcode(barcode);
            scan.setLamp(lamp);
        }
        return scan;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    private void startHistoryActivity() {

        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putExtra(EXTRA_SCANS, scans);
        startActivity(intent);
    }

    @Override
    public void handleResult(Result rawResult) {

        String barcode = rawResult.getText();

        // TODO remove
        if (barcode.equals("12627613")) {
            startHistoryActivity();
            return;
        }

        Scan scan = Scan.fromBarcode(barcode);
        if (lamps.containsBarcode(barcode)){
            Lamp lamp = lamps.getLampByBarcode(barcode);
            scan.setLamp(lamp);

            String message = "Found lamp for barcode" + barcode;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        } else {
            String message = "Lamp for barcode " + barcode + " not found";
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        scans.addScan(scan);

        try {
            scans.saveToFile(this);
            Toast.makeText(this, "scans saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScanningActivity.this);
            }
        }, 2000);
    }
}
