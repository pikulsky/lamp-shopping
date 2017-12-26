package com.bavers.lamping.lampshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class EnterBarcodeActivity extends AppCompatActivity {

    public static final String EXTRA_BARCODE = "com.bavers.lamping.lampshopping.BARCODE";
    public static final String EXTRA_LAMP = "com.bavers.lamping.lampshopping.LAMP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_barcode);

        this.readLampsFile();

    }
    protected void readLampsFile() {
        try {
            InputStream is = getResources().openRawResource(R.raw.led);
            InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            CSVReader reader = new CSVReader(isr, ';', '"', 1);

            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Log.d("Lamp", nextLine[0] + ' ' + nextLine[1] + ' ' + nextLine[2]);
                System.out.printf(nextLine[0] + ' ' + nextLine[1] + ' ' + nextLine[2]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Called when the user taps the Search button */
    public void searchBarcode(View view) {
        EditText editBarcode = (EditText) findViewById(R.id.editBarcode);
        String barcode = editBarcode.getText().toString();

        Lamp lamp = (new LampRepository()).getLampByBarcode(barcode);
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
