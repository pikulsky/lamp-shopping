package com.bavers.lamping.lampshopping;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanningActivity extends BaseScannerActivity implements ZXingScannerView.ResultHandler {

    public static final String EXTRA_SCANS = "com.bavers.lamping.lampshopping.SCANS";

    private ZXingScannerView mScannerView;

    /**
     * List of scans
     */
    private ScanRepository scans = new ScanRepository();

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scanning);
        setupToolbar();

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);
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

        Intent intent = getIntent();
        LampRepository lamps = (LampRepository) intent.getSerializableExtra(EnterBarcodeActivity.EXTRA_LAMPS);

        String barcode = rawResult.getText();

        if (barcode.equals("111")) {
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
