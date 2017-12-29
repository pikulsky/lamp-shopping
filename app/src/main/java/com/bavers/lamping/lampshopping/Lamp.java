package com.bavers.lamping.lampshopping;

import java.io.Serializable;
import java.util.HashMap;

public class Lamp implements Serializable {
    public String no;
    public String brand;
    public String model;
    public String barcode;
    public String rating;

    @Override
    public String toString() {
        return "Lamp{" +
                "no='" + no + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", barcode='" + barcode + '\'' +
                '}';
    }

    public static Lamp fromArray(String[] values) {
        Lamp lamp = new Lamp();
        lamp.no = values[0];
        lamp.brand = values[1];
        lamp.model = values[2];
        lamp.barcode = values[8];
        lamp.rating = values[38];

        return lamp;
    }

    public HashMap<String, String> getAttributes() {
        HashMap<String,String> attributes = new HashMap<String, String>();
        attributes.put("no", this.no);
        attributes.put("brand", this.brand);
        attributes.put("model", this.model);
        attributes.put("barcode", this.barcode);
        attributes.put("rating", this.rating);
        return attributes;
    }
}
