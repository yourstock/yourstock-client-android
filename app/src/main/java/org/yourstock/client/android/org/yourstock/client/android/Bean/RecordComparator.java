package org.yourstock.client.android.org.yourstock.client.android.Bean;

import org.yourstock.client.android.R;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Taeksang Kim on 2015-12-02.
 */
public class RecordComparator {

    private static RecordComparator instance = null;

    private HashMap<Integer, RecordFieldComparator> mFieldMap;


    public static RecordComparator getInstance() {
        if (RecordComparator.instance == null) {
            RecordComparator.instance = new RecordComparator();
        }
        return RecordComparator.instance;
    }

    private RecordComparator() {
        this.mFieldMap = new HashMap<Integer, RecordFieldComparator>();

        mFieldMap.put(R.id.h_name, new NameComparator());
        mFieldMap.put(R.id.h_price, new PriceComparator());
        mFieldMap.put(R.id.h_min_price, new MinPriceComparator());
        mFieldMap.put(R.id.h_max_price, new MaxPriceComparator());
    }

    public RecordFieldComparator getComparator(int fieldId) {
        RecordFieldComparator comparator;

        comparator = mFieldMap.get(fieldId);

        return comparator;
    }

    public abstract class RecordFieldComparator implements Comparator<Record> {
        private int state = -1;
        public void updateState() {
            state *= -1;
        }

        public int compare(Record lhs, Record rhs) {
            return state * compareField(lhs, rhs);
        }

        protected abstract int compareField(Record lhs, Record rhs);

    }

    private class NameComparator extends RecordFieldComparator {

        @Override
        protected int compareField(Record lhs, Record rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    }

    private class PriceComparator extends RecordFieldComparator {

        @Override
        public int compareField(Record lhs, Record rhs) {
            return  lhs.getPrice() - rhs.getPrice();
        }
    }

    private class MinPriceComparator extends RecordFieldComparator {

        @Override
        public int compareField(Record lhs, Record rhs) {
            return lhs.getMin_price() - rhs.getMin_price();
        }
    }

    private class MaxPriceComparator extends RecordFieldComparator {

        @Override
        public int compareField(Record lhs, Record rhs) {
            return lhs.getMax_price() - rhs.getMax_price();
        }
    }
}
