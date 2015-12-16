package org.yourstock.client.android.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import org.yourstock.client.android.Bean.Record;
import org.yourstock.client.android.R;
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
    private static LayoutParams linear_params = null;
    private static LayoutParams text_params = null;
    public static String[] periods = null;
    public static int txtSize = 9;

    public RecordContentsAdapter(Context context, List<Record> list) {
        super(context, list);
    }

    private LayoutParams getTextLayoutParams() {
        if (text_params == null) {
            text_params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        }
        return text_params;
    }

    private void attachTextView(LinearLayout linearLayout, TextView[][] history, int pos) {
        TextView ratio, price;
        LayoutParams textParams;

        ratio = new TextView(linearLayout.getContext());
        price = new TextView(linearLayout.getContext());

        textParams = getTextLayoutParams();

        ratio.setLayoutParams(textParams);
        price.setLayoutParams(textParams);

        ratio.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        price.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        ratio.setTextSize(TypedValue.COMPLEX_UNIT_PT, txtSize);
        price.setTextSize(TypedValue.COMPLEX_UNIT_PT, txtSize);

        ratio.setTag("ratio");
        price.setTag("price");

        history[0][pos] = ratio;
        history[1][pos] = price;

        history[0][pos].setTextColor(Color.BLACK);
        history[1][pos].setTextColor(Color.BLACK);

        linearLayout.addView(ratio);
        linearLayout.addView(price);

    }

    private LayoutParams getLinearLayoutParams(int width) {
        if (linear_params == null) {

           width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, width,
                    mContext.getResources().getDisplayMetrics());

            linear_params = new LayoutParams(width, LayoutParams.MATCH_PARENT);
        }
        return linear_params;
    }

    @Override
    protected View inflateRecordView(LayoutInflater inflater, ViewGroup parent, RecordHolder holder) {

        int total;
        LinearLayout[] linearLayout;
        LinearLayout recordRoot;
        LinearLayout min_cur, max_cur;
        TextView priceView;
        LayoutParams linear_params;
        TextView [][] history;
        View inflated;

        total = Record.KINDS * Record.NUM_PERIOD;

        if (periods == null) {
          periods = mContext.getResources().getStringArray(R.array.duration);
        }

        inflated =
                inflater.inflate(R.layout.record_contents, parent, false);

        recordRoot = (LinearLayout) inflated.findViewById(R.id.record);

        priceView = (TextView) recordRoot.findViewById(R.id.price);


        linear_params = getLinearLayoutParams(100);
        linearLayout = new LinearLayout[total];

        history = new TextView[2][total];
        history[0] = new TextView[total];
        history[1] = new TextView[total];

        for (int i = 0; i < Record.NUM_PERIOD; i++) {
            min_cur = linearLayout[i * 2] = new LinearLayout(mContext);
            max_cur = linearLayout[i * 2 + 1] = new LinearLayout(mContext);

            min_cur.setOrientation(LinearLayout.VERTICAL);
            max_cur.setOrientation(LinearLayout.VERTICAL);
            min_cur.setLayoutParams(linear_params);
            max_cur.setLayoutParams(linear_params);
            min_cur.setTag("min" + periods[i]);
            max_cur.setTag("max" + periods[i]);

            attachTextView(min_cur, history, i * 2);
            attachTextView(max_cur, history, i * 2 + 1);

            recordRoot.addView(min_cur);
            recordRoot.addView(max_cur);
        }

        holder.history = history;
        holder.price = priceView;

        return inflated;
    }

    @Override
    protected void attachRecordObject(Record record, RecordHolder holder) {
        NumberFormat formatKorea;
        int[] historyPrice;
        String[] historyRatio;

        formatKorea = NumberFormat.getNumberInstance(Locale.KOREA);
        holder.price.setText(formatKorea.format(record.getPrice()));
        historyPrice = record.getHistoryPrice();
        historyRatio = record.getStrHistoryRatio();


        for (int i = 0; i < Record.NUM_PERIOD; i++) {

            // ratio & price
            holder.history[0][i * 2].setText(historyRatio[i * 2]);
            holder.history[1][i * 2].setText(Integer.toString(historyPrice[i * 2]));

            holder.history[0][i * 2 + 1].setText(historyRatio[i * 2 + 1]);
            holder.history[1][i * 2 + 1].setText(Integer.toString(historyPrice[i * 2 + 1]));
        }
    }
}
