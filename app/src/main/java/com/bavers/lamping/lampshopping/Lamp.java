package com.bavers.lamping.lampshopping;

import java.io.Serializable;
import java.util.HashMap;

public class Lamp implements Serializable {
    // 0
    public String no;
    public String brand;
    public String model;
    // 8
    public String barcode;
    public String rating;
    // 3, Потребляемая мощность лампы (Вт), заявленная производителем
    // Мощность, Вт (Заявлено)
    public String power_l;
    public String url;
    public String shop;
    // 6, Цена в рублях
    public String rub;
    // 7, Цена в долларах
    public String usd;
    // 9, Диаметр (мм)
    public String d;
    // 10, Высота (мм)
    public String h;
    // 11, Измеренный вес лампы в граммах
    public String w;
    // 12, Напряжение рабочее, минимальное, В (Заявлено)
    // Рабочее напряжение лампы, заявленное производителем (В)
    public String u;
    // 13, Тип цоколя
    public String base;
    // 14, Вид лампы
    // Bulb - груша, Candle - свечка, Capsule - микролампа, Corn - кукуруза,
    // G45/G95/G120 - шары 45/95/120 мм,
    // R39/R50/R63 - зеркальная 39/50/63 мм,
    // Spot - спот , Strip - лента, Lamp - светильник, Flood - прожектор
    public String shape;
    // 15, Тип лампы
    //  LED - светодиодная, LED/FIL - светодиодная филаментная, CFL - люминесцентная, STD - лампа накаливания.
    public String type;
    // 16, Тип лампы
    // FIL - филаментная
    public String type2;
    // 17, Матовость лампы, 1/0
    public String matt;
    // 18, Световой поток, лм (Заявлено)
    public String lm_l;
    // 19, Эквивалент лампы накаливания, Вт (Заявлено)
    public String eq_l;
    // 20, Цветовая температура, К (Заявлено)
    public String color_l;
    // 21, Индекс цветопередачи, CRI (Ra) (Заявлено)
    public String ra_l;
    // 22, Срок работы лампы в часах, заявленный производителем
    public String life;
    // 23, Гарантия на лампу в месяцах, заявленная производителем
    public String war;
    // 24, Дата изготовления, MYY
    public String prod;
    // 25, Возможность диммирования и его минимальный уровень (%)
    public String dim;
    // 26, Измеренная возможность работы с выключателем, имеющим индикатор.
    // 1 (плюс) поддерживается, 3 (минус) - лампа вспыхивает
    // 2 (плюс-минус) - лампа слабо светится
    public String swtch; // switch
    // 27, Вт. Измеренная потребляемая мощность в ваттах.
    // Мощность, Вт (Измерено)
    public String p;
    // 28, Световой поток, лм (Измерено)
    public String lm;
    // 29, Эквивалент лампы накаливания, Вт (Измерено)
    public String eq;
    // 30, Цветовая температура, К (Измерено)
    public String color;
    // 31, Индекс цветопередачи, CRI (Ra) (Измерено)
    public String cri;
    // 32, Измеренный индекс цветопередачи
    public String cqs;
    // 33, Измеренный индекс передачи красного цвета R9
    public String r9;
    // 34, Измеренный угол освещения в градусах
    public String angle;
    // 35, Измеренная пульсация света (%)
    public String flicker;
    // 36, Коэффициент мощности
    public String pf;
    // 37, Дата тестирования, DD.MM.YYYY
    public String date;
    // 39, Напряжение рабочее, минимальное, В (Измерено)
    public String umin;
    // 40, Тип драйвера. 1 - импульсный (яркость не меняется),
    // 2 - импульсный (отключается),
    // 3 - импульсный (мигать), 4 - линейный
    public String drv;
    // 41, Максимальная температура корпуса лампы
    public String tmax;
    // 42, Актуальность лампы, 0/1
    public String act;
    // 43,
    public String add1;
    // 44,
    public String add2;
    // 45,
    public String add3;
    // 46,
    public String add4;
    // 47,
    public String add5;

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
