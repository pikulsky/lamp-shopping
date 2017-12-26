package com.bavers.lamping.lampshopping;


public class LampRepository
{
    public Lamp getLampByBarcode(String barcode) {
        if (barcode.equals("11")) {
            Lamp lamp = new Lamp();
            lamp.no = "1";
            lamp.brand = "IKEA";
            lamp.model = "EE";
            lamp.barcode = "1234512345123";
            return lamp;
        }
        return null;
    }
}
