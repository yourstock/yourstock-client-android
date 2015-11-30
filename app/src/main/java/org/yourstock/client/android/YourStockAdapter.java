package org.yourstock.client.android;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.yourstock.client.android.org.yourstock.client.android.Bean.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taeksang on 2015-11-30.
 */
public class YourStockAdapter extends BaseAdapter {

    private List<Record> list;

    public YourStockAdapter() {
        this.list = new ArrayList<Record>();
    }

    public YourStockAdapter(List<Record> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null)
            return 0;

        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(list.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int pos;
        Context context;
        View myView;

        if (convertView == null) {
            context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.records, null);
        } else
            myView = convertView;

        if (list != null)
            attachRecordObject(list.get(position), myView);

        return myView;
    }

    private void attachRecordObject(Record record, View view) {
        TextView txtViewName = (TextView) view.findViewById(R.id.name);
        TextView txtViewPrice = (TextView) view.findViewById(R.id.price);
        TextView txtViewMinPrice = (TextView) view.findViewById(R.id.min_price);
        TextView txtViewMaxPrice = (TextView) view.findViewById(R.id.max_price);

        txtViewName.setText(record.getName());
        txtViewPrice.setText(Integer.toString(record.getPrice()));
        txtViewMinPrice.setText(Integer.toString(record.getMin_price()));
        txtViewMaxPrice.setText(Integer.toString(record.getMax_price()));
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
