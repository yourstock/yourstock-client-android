package org.yourstock.client.android.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.yourstock.client.android.R;

import java.util.Arrays;

/**
 * Created by Taeksang on 2015-12-02.
 */
public class MenuAdapter extends ArrayAdapter<String> {
    private String[] mDataset;

    public MenuAdapter(Context context, int resource) {
        super(context, resource);
    }

    public MenuAdapter(Context context, int resource, String[] objects) {
       super(context, resource, objects);
    }



/*
    public MenuAdapter(String[] myDataset, OnItemClickListener listener) {
        mDataset = myDataset;
        mListener = listener;
    }*/
/*
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(parent.getContext());
        View v = vi.inflate(R.layout.drawer_list_item, parent, false);
        TextView tv = (TextView) v.findViewById(android.R.id.text1);
        return new ViewHolder(tv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTextView.setText(mDataset[position]);
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }*/
}
