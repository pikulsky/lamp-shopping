package com.bavers.lamping.lampshopping;

import java.io.Serializable;
import java.util.HashMap;

public class Lamp implements Serializable {
    public String no;
    public String brand;
    public String model;
    public String barcode;

    @Override
    public String toString() {
        return "Lamp{" +
                "no='" + no + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", barcode='" + barcode + '\'' +
                '}';
    }
    public HashMap<String, String> getAttributes() {
        HashMap<String,String> attributes = new HashMap<String, String>();
        attributes.put("no", this.no);
        attributes.put("brand", this.brand);
        attributes.put("model", this.model);
        attributes.put("barcode", this.barcode);
        return attributes;
    }
}
