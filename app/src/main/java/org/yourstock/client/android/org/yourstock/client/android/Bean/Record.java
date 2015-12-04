package org.yourstock.client.android.org.yourstock.client.android.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taeksang on 2015-11-30.
 */
public class Record {
    public static final int NUM_PERIOD = 9;
    public static final int KINDS = 2;
    private String id;
    private String name;
    private int price;
    private int maxPrice;
    private int minPrice;
    private int [] historyPrice;


    private double[] historyRatio;

    private String[] strHistoryRatio;

    private boolean isKosdaq;


    public void makeRatio() {
        int min, max;
        double result;
        String formatString = "%.2f%%";

        this.strHistoryRatio = new String[Record.NUM_PERIOD * Record.KINDS];
        this.historyRatio = new double [Record.NUM_PERIOD * Record.KINDS];

        for(int i = 0; i < Record.NUM_PERIOD; i++) {
            min = this.historyPrice[i * 2];
            max = this.historyPrice[i * 2 + 1];

            if (min == 0) {
                result = -1;
            } else {
                result = (double) price / min;
            }
            result *= 100;
            this.historyRatio[i * 2] = result;

            if (max == 0) {
                result = -1;
            } else {
                result = (double) price / max;
            }
            result *= 100;
            this.historyRatio[i * 2 + 1] = result;

            this.strHistoryRatio[i * 2] = String.format(formatString, this.historyRatio[i * 2]);
            this.strHistoryRatio[i * 2 + 1] = String.format(formatString, this.historyRatio[i * 2 + 1]);
        }
    }

    public Record(String id, String name, int price, int maxPrice, int minPrice, boolean isKosdaq) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.historyPrice = new int [Record.NUM_PERIOD * Record.KINDS];
        //for testing
        for (int i = 0; i < Record.NUM_PERIOD; i++) {
            this.historyPrice[i * 2] = minPrice;
            this.historyPrice[i * 2 + 1] = maxPrice;
        }
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
        this.isKosdaq = isKosdaq;
        this.makeRatio();
    }

    public Record(String id, String name, int price, int[] historyPrice, boolean isKosdaq) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.historyPrice = historyPrice;
        this.isKosdaq = isKosdaq;
        this.makeRatio();
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

    public String getId() {

        return id;
    }

    public int[] getHistoryPrice() {
        return historyPrice;
    }

    public void setHistoryPrice(int[] historyPrice) {
        this.historyPrice = historyPrice;
    }

    public String[] getStrHistoryRatio() {
        return strHistoryRatio;
    }

    public void setStrHistoryRatio(String[] strHistoryRatio) {
        this.strHistoryRatio = strHistoryRatio;
    }

    public double[] getHistoryRatio() {
        return historyRatio;
    }

    public void setHistoryRatio(double[] historyRatio) {
        this.historyRatio = historyRatio;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static List<Record> getDummyList() {
        ArrayList<Record> dummyList = new ArrayList<Record>();

        dummyList.add(new Record("035720", "카카오", 120600, 99000, 160000, true));
        dummyList.add(new Record("035420", "네이버", 631000, 458000, 797000, false));
        dummyList.add(new Record("005930", "삼성전자", 1284000, 1033000, 1510000, false));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));

        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));
        dummyList.add(new Record("023590", "다우기술", 22950, 10500, 35800, true));


        return dummyList;
    }
}
