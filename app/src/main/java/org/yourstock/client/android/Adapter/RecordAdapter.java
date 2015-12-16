package org.yourstock.client.android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.yourstock.client.android.Bean.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taeksang on 2015-11-30.
 */
public abstract class RecordAdapter extends BaseAdapter {

    private List<Record> mList;
    private LayoutInflater mLayoutInflater;
    protected Context mContext;

    protected static class RecordHolder {
        public TextView name;
        public TextView price;
        public TextView[][] history;
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
            holder = new RecordHolder();
            convertView = inflateRecordView(mLayoutInflater, parent, holder);
            //holder = createHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RecordHolder) convertView.getTag();
        }
        if (this.mList != null)
            attachRecordObject(this.mList.get(position), holder);

        return convertView;
    }

    protected abstract View inflateRecordView(LayoutInflater inflater, ViewGroup parent,
                                              RecordHolder holder);

   // protected abstract RecordHolder createHolder(View view);

    protected abstract void attachRecordObject(Record record, RecordHolder holder);

}
