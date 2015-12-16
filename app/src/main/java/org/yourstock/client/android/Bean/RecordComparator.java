package org.yourstock.client.android.Bean;

import org.yourstock.client.android.R;

import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Taeksang Kim on 2015-12-02.
 */
public class RecordComparator {

    private static RecordComparator instance = null;

    private HashMap<Integer, RecordFieldComparator> mFieldMap;
    private HistoryComparator mHistoryComparator;


    public static RecordComparator getInstance() {
        if (RecordComparator.instance == null) {
           instance = new RecordComparator();
        }
        return instance;
    }

    private RecordComparator() {
        this.mFieldMap = new HashMap<Integer, RecordFieldComparator>();

        mFieldMap.put(R.id.h_name, new NameComparator());
        mFieldMap.put(R.id.h_price, new PriceComparator());
        mHistoryComparator = new HistoryComparator(0);
    }

    public RecordFieldComparator getComparator(int fieldId, int index) {
        RecordFieldComparator comparator;

        if (index == -1) {
            comparator = mFieldMap.get(fieldId);
        }
        else {
            mHistoryComparator.setIndex(index);
            comparator = mHistoryComparator;
        }
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

    private class HistoryComparator extends RecordFieldComparator {

        private int index;
        public HistoryComparator(int index) {
            this.index = index;
        }
        @Override
        protected int compareField(Record lhs, Record rhs) {
            double ratio1;
            double ratio2;

            ratio1 = lhs.getHistoryRatio()[index];
            ratio2 = rhs.getHistoryRatio()[index];

            return Double.compare(ratio1, ratio2);
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
