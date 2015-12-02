package org.yourstock.client.android;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.yourstock.client.android.org.yourstock.client.android.Bean.Record;
import org.yourstock.client.android.org.yourstock.client.android.Bean.RecordComparator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends Activity {

    private YourStockAdapterContents mYourStockAdapter;
    private YourStockNameAdapter mYourStockNameAdapter;
    private ListView mRecordListView;
    private ListView mRecordNameListView;
    private List<Record> mRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button hName, hPrice, hMinPrice, hMaxPrice;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecordList = Record.getDummyList();

        mYourStockAdapter =
                new YourStockAdapterContents(this.getApplicationContext(), mRecordList);

        mYourStockNameAdapter =
                new YourStockNameAdapter(this.getApplicationContext(), mRecordList);

        mRecordListView = (ListView) this.findViewById(R.id.record_list);
        mRecordNameListView = (ListView) this.findViewById(R.id.record_name_list);

        hName = (Button) this.findViewById(R.id.h_name);
        hPrice = (Button) this.findViewById(R.id.h_price);
        hMinPrice = (Button) this.findViewById(R.id.h_min_price);
        hMaxPrice = (Button) this.findViewById(R.id.h_max_price);

        hName.setOnClickListener(onSortBtn);
        hPrice.setOnClickListener(onSortBtn);
        hMinPrice.setOnClickListener(onSortBtn);
        hMaxPrice.setOnClickListener(onSortBtn);

        mRecordNameListView.setAdapter(mYourStockNameAdapter);
        mRecordListView.setAdapter(mYourStockAdapter);

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            boolean dispatched = false;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.equals(mRecordListView) && !dispatched) {
                    dispatched = true;
                    mRecordNameListView.dispatchTouchEvent(event);
                } else if (v.equals(mRecordNameListView) && !dispatched) {
                    dispatched = true;
                    mRecordListView.dispatchTouchEvent(event);
                }
                dispatched = false;
                return false;
            }
        };

        mRecordListView.setOnTouchListener(touchListener);
        mRecordNameListView.setOnTouchListener(touchListener);
    }

    private void sortBySomething(int fieldId, List<Record> list) {
        RecordComparator recordComparator;
        RecordComparator.RecordFieldComparator comparator;

        recordComparator = RecordComparator.getInstance();
        comparator = recordComparator.getComparator(fieldId);
        comparator.updateState();
        Collections.sort(list, comparator);

        invalidateListView();
    }

    private void invalidateListView() {
        mYourStockAdapter.notifyDataSetChanged();
        mYourStockNameAdapter.notifyDataSetChanged();
    }

    private View.OnClickListener onSortBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sortBySomething(v.getId(), mRecordList);
        }
    };
}