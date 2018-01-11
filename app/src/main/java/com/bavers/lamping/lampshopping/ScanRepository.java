package com.bavers.lamping.lampshopping;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Scans list
 */

public class ScanRepository implements Serializable {

    public ArrayList<Scan> scans;

    private static final String SCANS_FILE_NAME = "scans.dat";

    public ScanRepository() {
        this.scans = new ArrayList<Scan>();
    }

    public void clear(Context context) {
        context.deleteFile(SCANS_FILE_NAME);
        this.scans = new ArrayList<Scan>();
    }

    public void addScan(Scan scan) {
        scans.add(scan);
    }

    public ArrayList<Scan> getScans() {
        return scans;
    }

    public void loadFromFile(Context context) throws IOException, ClassNotFoundException {

        // TODO check if file exist and set empty list
        FileInputStream fin = context.openFileInput(SCANS_FILE_NAME);
        ObjectInputStream ois = new ObjectInputStream(fin);
        scans = (ArrayList<Scan>) ois.readObject();
        fin.close();
    }

    public void saveToFile(Context context) throws IOException {

        FileOutputStream fos = context.openFileOutput(SCANS_FILE_NAME, Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(scans);
        oos.close();
    }
}
