package com.bavers.lamping.lampshopping;


import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class LampRepository
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
}
