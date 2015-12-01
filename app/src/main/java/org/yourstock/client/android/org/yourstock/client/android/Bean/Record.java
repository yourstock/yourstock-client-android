package org.yourstock.client.android.org.yourstock.client.android.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taeksang on 2015-11-30.
 */
public class Record {
    private String id;
    private String name;
    private int price;
    private int max_price;
    private int min_price;
    private boolean isKosdaq;


    public Record(String id, String name, int price, int max_price, int min_price, boolean isKosdaq) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.max_price = max_price;
        this.min_price = min_price;
        this.isKosdaq = isKosdaq;
    }

    public boolean isKosdaq() {
        return isKosdaq;
    }

    public void setIsKosdaq(boolean isKosdaq) {
        this.isKosdaq = isKosdaq;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMax_price() {
        return max_price;
    }

    public void setMax_price(int max_price) {
        this.max_price = max_price;
    }

    public int getMin_price() {
        return min_price;
    }

    public void setMin_price(int min_price) {
        this.min_price = min_price;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static List<Record> getDummyList() {
        ArrayList<Record> dummyList = new ArrayList<Record>();

        dummyList.add(new Record("035720","카카오",120600, 99000, 160000, true));
        dummyList.add(new Record("035420","네이버",631000, 458000, 797000, false));
        dummyList.add(new Record("005930","삼성전자",1284000, 1033000, 1510000, false));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));

        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590","다우기술", 22950, 10500, 35800, true));


        return dummyList;
    }
}
