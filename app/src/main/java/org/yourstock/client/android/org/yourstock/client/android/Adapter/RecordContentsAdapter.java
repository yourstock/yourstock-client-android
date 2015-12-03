package org.yourstock.client.android.org.yourstock.client.android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.yourstock.client.android.R;
import org.yourstock.client.android.org.yourstock.client.android.Bean.Record;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Taeksang Kim on 2015-12-03.
 */
public class RecordContentsAdapter extends RecordAdapter {

    public RecordContentsAdapter(Context context) {
        super(context);
    }

    public RecordContentsAdapter(Context context, List<Record> list) {
        super(context, list);
    }

    @Override
    protected View inflateRecordView(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.record_contents, parent, false);
    }

    @Override
    protected RecordHolder createHolder(View view) {
        RecordHolder holder = new RecordHolder();
        holder.price = (TextView) view.findViewById(R.id.price);
        holder.minPrice = (TextView) view.findViewById(R.id.min_price);
        holder.maxPrice = (TextView) view.findViewById(R.id.max_price);
        return holder;
    }

    @Override
    protected void attachRecordObject(Record record, RecordHolder holder) {
        NumberFormat formatKorea;

        formatKorea = NumberFormat.getNumberInstance(Locale.KOREA);
        holder.price.setText(formatKorea.format(record.getPrice()));
        holder.minPrice.setText(formatKorea.format(record.getMinPrice()));
        holder.maxPrice.setText(formatKorea.format(record.getMaxPrice()));
    }
}
