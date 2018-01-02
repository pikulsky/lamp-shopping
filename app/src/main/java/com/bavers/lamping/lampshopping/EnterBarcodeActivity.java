package com.bavers.lamping.lampshopping;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;

public class EnterBarcodeActivity extends AppCompatActivity {

    public static final String EXTRA_BARCODE = "com.bavers.lamping.lampshopping.BARCODE";
    public static final String EXTRA_LAMP = "com.bavers.lamping.lampshopping.LAMP";
    public static final String EXTRA_LAMPS = "com.bavers.lamping.lampshopping.LAMPS";

    LampRepository lamps;

    private static final int ZXING_CAMERA_PERMISSION = 1;
    //private Class<?> mClss;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_barcode);

        readLampsFile();

    }

    protected void readLampsFile() {
        try {
            InputStream is = getResources().openRawResource(R.raw.led);
            InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            CSVReader reader = new CSVReader(isr, ';', '"', 1);

            String [] nextLine;
            lamps = new LampRepository();

            while ((nextLine = reader.readNext()) != null) {

                Lamp lamp = Lamp.fromArray(nextLine);
                lamps.add(lamp);

                //Log.d("Lamp", nextLine[0] + ' ' + nextLine[1] + ' ' + nextLine[2]);
                //System.out.printf(nextLine[0] + ' ' + nextLine[1] + ' ' + nextLine[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void launchActivity(Class<?> clss) {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            mClss = clss;
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.CAMERA}, ZXING_CAMERA_PERMISSION);
//        }
//
//        Intent intent = new Intent(this, clss);
//        startActivity(intent);
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case ZXING_CAMERA_PERMISSION:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if(mClss != null) {
//                        Intent intent = new Intent(this, mClss);
//                        startActivity(intent);
//                    }
//                } else {
//                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
//                }
//                return;
//        }
//    }

    private void startScanningActivity() {

        Intent intent = new Intent(this, ScanningActivity.class);
        intent.putExtra(EXTRA_LAMPS, lamps);
        startActivity(intent);

        //launchActivity(ScanningActivity.class);
    }

    /** Called when the user taps the Search button */
    public void searchBarcode(View view) {
        EditText editBarcode = (EditText) findViewById(R.id.editBarcode);
        String barcode = editBarcode.getText().toString();

        if (barcode.equals("11")) {
            startScanningActivity();
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

}
