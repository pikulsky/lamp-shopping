package com.bavers.lamping.lampshopping;

import android.content.Intent;
import android.support.v4.text.BidiFormatter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UnknownBarcodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unknown_barcode);

        Intent intent = getIntent();
        String barcode = intent.getStringExtra(EnterBarcodeActivity.EXTRA_BARCODE);

        BidiFormatter bidiFormatter = BidiFormatter.getInstance();

        String messageTemplate = getResources().getString(R.string.unknown_barcode);
        String message = String.format(messageTemplate, bidiFormatter.unicodeWrap(barcode));

        TextView textViewUnknownBarcode = findViewById(R.id.textViewUnknownBarcode);
        textViewUnknownBarcode.setText(message);
    }

    public void anotherBarcode(View view) {
        Intent enterBarcodeIntent = new Intent(this, EnterBarcodeActivity.class);
        startActivity(enterBarcodeIntent);
    }

}
