package com.bavers.lamping.lampshopping;

import java.io.Serializable;
import java.util.HashMap;

public class Lamp implements Serializable {
    public String no;
    public String brand;
    public String model;
    public String barcode;
    public String rating;

    // colors by rating
    public static final String RATING_COLOR_5       = "#DDFFDC"; // 5+
    public static final String RATING_COLOR_4       = "#DEFFFE"; // 4 - 4.9
    public static final String RATING_COLOR_3       = "#FDFFE4"; // 3 - 3.9
    public static final String RATING_COLOR_2       = "#FFEEDB"; // 2 - 2.9
    public static final String RATING_COLOR_1       = "#FFDDDD"; // 0 - 1.9
    public static final String RATING_COLOR_EMPTY   = "#FFFFFF"; // no rating

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

    public String getRateColor() {
        if (rating.isEmpty()) {
            return RATING_COLOR_EMPTY;
        }

        float ratingValue = Float.parseFloat(rating);
        if (ratingValue >= 5) {
            return RATING_COLOR_5;
        } else if (ratingValue >= 4) {
            return RATING_COLOR_4;
        } else if (ratingValue >= 3) {
            return RATING_COLOR_3;
        } else if (ratingValue >= 2) {
            return RATING_COLOR_2;
        } else {
            return RATING_COLOR_1;
        }
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
