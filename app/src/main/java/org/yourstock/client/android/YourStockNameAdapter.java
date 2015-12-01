package org.yourstock.client.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.yourstock.client.android.org.yourstock.client.android.Bean.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taeksang Kim on 2015-12-02.
 */
public class YourStockNameAdapter extends BaseAdapter {

    private List<Record> mList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public YourStockNameAdapter(Context context) {
        this.mList = new ArrayList<Record>();
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public YourStockNameAdapter(Context context, List<Record> list) {
        this(context);
        this.mList = list;
    }

    @Override
    public int getCount() {
        if (mList == null)
            return 0;

        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mList == null)
            return null;

        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(mList.get(position).getId());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int pos;
        Context context;
        View myView;

        if (convertView == null) {
            context = parent.getContext();
            myView = mLayoutInflater.inflate(R.layout.record_name, null);
        } else
            myView = convertView;

        if (mList != null)
            attachRecordObject(mList.get(position), myView);

        return myView;
    }

    private void attachRecordObject(Record record, View view) {
        TextView txtViewName = (TextView) view.findViewById(R.id.name);

         txtViewName.setText(record.getName());
    }
}
