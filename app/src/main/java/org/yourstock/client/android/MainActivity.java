package org.yourstock.client.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import org.yourstock.client.android.R;
import org.yourstock.client.android.org.yourstock.client.android.Bean.Record;

public class MainActivity extends Activity {

    private YourStockAdapter mYourStockAdapter;
    private ListView mRecordListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mYourStockAdapter = new YourStockAdapter(Record.getDummyList());
        mRecordListView = (ListView) findViewById(R.id.record_list_view);
        mRecordListView.setAdapter(mYourStockAdapter);
    }
}
