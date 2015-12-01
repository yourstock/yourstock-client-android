package org.yourstock.client.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import org.yourstock.client.android.org.yourstock.client.android.Bean.Record;

import java.util.List;

public class MainActivity extends Activity {

    private YourStockAdapterContents mYourStockAdapter;
    private YourStockNameAdapter mYourStockNameAdapter;
    private ListView mRecordListView;
    private ListView mRecordNameListView;
    private List<Record> mRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecordList = Record.getDummyList();

        mYourStockAdapter =
                new YourStockAdapterContents(this.getApplicationContext(), mRecordList);

        mYourStockNameAdapter =
                new YourStockNameAdapter(this.getApplicationContext(), mRecordList);

        mRecordListView = (ListView) this.findViewById(R.id.record_list);
        mRecordNameListView = (ListView) this.findViewById(R.id.record_name_list);

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
}
