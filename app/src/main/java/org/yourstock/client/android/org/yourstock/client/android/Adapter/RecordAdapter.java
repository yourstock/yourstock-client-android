package org.yourstock.client.android.org.yourstock.client.android.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.yourstock.client.android.R;
import org.yourstock.client.android.org.yourstock.client.android.Bean.Record;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Taeksang on 2015-11-30.
 */
public abstract class RecordAdapter extends BaseAdapter {

    private List<Record> mList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    protected static class RecordHolder {
        public TextView name;
        public TextView price;
        public TextView minPrice;
        public TextView maxPrice;
    }

    public RecordAdapter(Context context) {
        this.mList = new ArrayList<Record>();
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public RecordAdapter(Context context, List<Record> list) {
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
        RecordHolder holder;

        if (convertView == null) {
            convertView = inflateRecordView(mLayoutInflater, parent);
            holder = createHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RecordHolder) convertView.getTag();
        }
        if (this.mList != null)
            attachRecordObject(this.mList.get(position), holder);

        return convertView;
    }

    protected abstract View inflateRecordView(LayoutInflater inflater, ViewGroup parent);

    protected abstract RecordHolder createHolder(View view);

    protected abstract void attachRecordObject(Record record, RecordHolder holder);

    private void attachRecordObject(Record record, View view) {
        NumberFormat formatKorea;

        TextView txtViewPrice = (TextView) view.findViewById(R.id.price);
        TextView txtViewMinPrice = (TextView) view.findViewById(R.id.min_price);
        TextView txtViewMaxPrice = (TextView) view.findViewById(R.id.max_price);

        formatKorea = NumberFormat.getNumberInstance(Locale.KOREA);
        txtViewPrice.setText(formatKorea.format(record.getPrice()));
        txtViewMinPrice.setText(formatKorea.format(record.getMinPrice()));
        txtViewMaxPrice.setText(formatKorea.format(record.getMaxPrice()));
    }
}