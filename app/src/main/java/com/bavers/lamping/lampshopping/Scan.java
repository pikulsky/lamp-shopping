package com.bavers.lamping.lampshopping;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

/**
 * Scan class describes the scan
 */

public class Scan implements Serializable {
    public Date scanDate;
    public String barcode;
    public boolean isFound;
    public HashSet<Lamp> foundLamps;

    public static Scan fromBarcode(String barcode) {
        Scan scan = new Scan();
        scan.scanDate = new Date();
        scan.barcode = barcode;
        scan.isFound = false;
        scan.foundLamps = new HashSet<Lamp>();
        return scan;
    }

    public void setLamp(Lamp lamp) {
        isFound = true;
        foundLamps.add(lamp);
    }
}
