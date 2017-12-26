package com.bavers.lamping.lampshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LampDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp_details);

        // Get the Intent that started this activity and extract data
        Intent intent = getIntent();
        Lamp lamp = (Lamp) intent.getSerializableExtra(EnterBarcodeActivity.EXTRA_LAMP);

        final ListView listViewLamp = (ListView) findViewById(R.id.listViewLamp);

        HashMap<String, String> attributes = lamp.getAttributes();

        final ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry:attributes.entrySet()) {
            String key = entry.getKey();
            String v = entry.getValue();
            list.add(key + " : " + v);
        }

        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listViewLamp.setAdapter(adapter);
    }
}
