package com.bavers.lamping.lampshopping;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EnterBarcodeActivity extends ParentActivity {

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

        // load lamps
        initializeLamps();

        // load scans
        initializeScans();

        initializeEditText();

        // setup bottom navigation
        initializeBottomNavigation(R.id.action_type);
    }


    protected void initializeEditText() {
        EditText editText = (EditText) findViewById(R.id.editBarcode);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchBarcodeAction();
                    handled = true;
                }
                return handled;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ZXING_CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // ok, granted!
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    /** Called when the user taps the Search button */
    public void searchBarcode(View view) {
        searchBarcodeAction();
    }

    public void searchBarcodeAction() {
        EditText editBarcode = (EditText) findViewById(R.id.editBarcode);
        String barcode = editBarcode.getText().toString();

        processBarcode(barcode);

        // clear the input control
        editBarcode.setText("");
    }
}
