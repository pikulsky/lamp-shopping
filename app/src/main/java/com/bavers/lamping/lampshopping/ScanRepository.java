package com.bavers.lamping.lampshopping;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Valeriy on 1/2/2018.
 */

public class ScanRepository implements Serializable {
    public ArrayList<Scan> scans = new ArrayList<Scan>();

    public void addScan(Scan scan) {
        scans.add(scan);
    }
}
