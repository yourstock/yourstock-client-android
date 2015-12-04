package org.yourstock.client.android.org.yourstock.client.android.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yourstock.client.android.R;
import org.yourstock.client.android.org.yourstock.client.android.Bean.Record;

import java.util.List;

/**
 * Created by Taeksang Kim on 2015-12-03.
 */
public class RecordNameAdapter extends RecordAdapter{

    public RecordNameAdapter(Context context) {
        super(context);
    }

    public RecordNameAdapter(Context context, List<Record> list) {
        super(context, list);
    }

    @Override
    protected View inflateRecordView(LayoutInflater inflater, ViewGroup parent,
                                     RecordHolder holder) {
        View inflated;

        inflated = inflater.inflate(R.layout.record_name, parent, false);
        holder.name = (TextView)inflated.findViewById(R.id.name);
        return inflated;
    }


    @Override
    protected void attachRecordObject(Record record, RecordHolder holder) {
        if (holder.name == null)
            Log.e("Error", "Error");
        holder.name.setText(record.getName());
    }
}
