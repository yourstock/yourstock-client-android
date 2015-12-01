package org.yourstock.client.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.yourstock.client.android.org.yourstock.client.android.Bean.Record;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Taeksang on 2015-11-30.
 */
public class YourStockAdapterContents extends BaseAdapter {

    private List<Record> mList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public YourStockAdapterContents(Context context) {
        this.mList = new ArrayList<Record>();
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public YourStockAdapterContents(Context context, List<Record> list) {
        this(context);
        this.mList = list;
    }
    @Override
    public int getCount() {
        if (this.mList == null)
            return 0;

        return this.mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (this.mList == null)
            return null;

        return this.mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.mList.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int pos;
        Context context;
        View myView;

        if (convertView == null) {
            context = parent.getContext();
            myView = mLayoutInflater.inflate(R.layout.record_contents, null);
        } else
            myView = convertView;

        if (this.mList != null)
            attachRecordObject(this.mList.get(position), myView);

        return myView;
    }

    private void attachRecordObject(Record record, View view) {
        NumberFormat formatKorea;

        TextView txtViewPrice = (TextView) view.findViewById(R.id.price);
        TextView txtViewMinPrice = (TextView) view.findViewById(R.id.min_price);
        TextView txtViewMaxPrice = (TextView) view.findViewById(R.id.max_price);

        formatKorea = NumberFormat.getNumberInstance(Locale.KOREA);
        txtViewPrice.setText(formatKorea.format(record.getPrice()));
        txtViewMinPrice.setText(formatKorea.format(record.getMin_price()));
        txtViewMaxPrice.setText(formatKorea.format(record.getMax_price()));
    }
}
