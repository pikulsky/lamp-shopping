package com.bavers.lamping.lampshopping;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LampRepository implements Serializable
{
    private HashMap<String, HashSet<Lamp>> lamps;

    public LampRepository() {
        lamps = new HashMap<String, HashSet<Lamp>>();
    }

    public void add(Lamp lamp) {
        if (lamps.containsKey(lamp.barcode)) {
            HashSet<Lamp> s = lamps.get(lamp.barcode);
            s.add(lamp);
            lamps.put(lamp.barcode, s);
        } else {
            HashSet<Lamp> s = new HashSet<Lamp>();
            s.add(lamp);
            lamps.put(lamp.barcode, s);
        }

    }

    public boolean containsBarcode(String barcode) {

        return lamps.containsKey(barcode);
    }

    public Lamp getLampByBarcode(String barcode) {

        if (lamps.containsKey(barcode)) {
            Set<Lamp> s = lamps.get(barcode);
            Lamp lamp = s.iterator().next();

            Log.d("Lamp FOUND", lamp.toString());
            System.out.printf("Lamp FOUND"  + lamp.toString());

            return lamp;
        }
        return null;
    }

    public void loadFromFile(Context context) throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.led);
        InputStreamReader isr = new InputStreamReader(is, Charset.forName("Windows-1251"));
        CSVReader reader = new CSVReader(isr, ';', '"', 1);

        String [] nextLine;
        lamps.clear();

        while ((nextLine = reader.readNext()) != null) {

            Lamp lamp = Lamp.fromArray(nextLine);
            add(lamp);

            //Log.d("Lamp", nextLine[0] + ' ' + nextLine[1] + ' ' + nextLine[2]);
            //System.out.printf(nextLine[0] + ' ' + nextLine[1] + ' ' + nextLine[2]);
        }
    }

}
