package org.yourstock.client.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.yourstock.client.android.Adapter.MenuAdapter;
import org.yourstock.client.android.Adapter.RecordAdapter;
import org.yourstock.client.android.Adapter.RecordContentsAdapter;
import org.yourstock.client.android.Adapter.RecordNameAdapter;
import org.yourstock.client.android.Bean.Record;
import org.yourstock.client.android.Bean.RecordComparator;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private RecordAdapter mRecordNameAdapter, mRecordContentsAdapter;

    private ListView mRecordListView;
    private ListView mRecordNameListView;
    private List<Record> mViewList;
    private List<Record> mDataList;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ListView mDrawerList;
    private String[] mMenus;
    private Handler mHandler;
    /* Called whenever we call invalidateOptionsMenu() */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        //    menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private String[] mKinds;

    private void initDrawerLayout(Bundle savedInstanceState) {
        mTitle = mDrawerTitle = getTitle();
        mMenus = getResources().getStringArray(R.array.menu_array);
        // mKinds = new String[] {"1", "2", "3"};
        mKinds = getResources().getStringArray(R.array.kinds);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        //mDrawerList.setLayoutManager(mLinearLayoutManager);

        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //mDrawerList.setHasFixedSize(true);

        MenuAdapter menuAdapter = new MenuAdapter(this, android.R.layout.simple_list_item_1, mMenus);
        View list_header = getLayoutInflater().inflate(R.layout.header_layout, null);
        mDrawerList.addHeaderView(list_header);
        mDrawerList.setAdapter(menuAdapter);
        mDrawerList.setOnItemClickListener(this);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View view) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            //selectItem(0);
        }
    }

    private void makeHeader() {
        LinearLayout.LayoutParams params;
        LinearLayout headerLayout;
        Button h_price;
        String[] periods;
        Button[] h_history;
        int width;

        h_price = (Button) findViewById(R.id.h_price);
        periods = getResources().getStringArray(R.array.duration);
        //Log.e("width", "width: " + h_price.getWidth());
        width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        params = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        headerLayout = (LinearLayout) findViewById(R.id.header);


        h_history = new Button[Record.NUM_PERIOD * Record.KINDS];

        for (int i = 0; i < Record.NUM_PERIOD; i++) {
            h_history[i * 2] = new Button(this);
            h_history[i * 2 + 1] = new Button(this);

            h_history[i * 2].setLayoutParams(params);
            h_history[i * 2].setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
            h_history[i * 2].setTag(new Integer(i * 2));
            h_history[i * 2].setText("Min:" + periods[i]);

            h_history[i * 2 + 1].setLayoutParams(params);
            h_history[i * 2 + 1].setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
            h_history[i * 2 + 1].setTag(new Integer(i * 2 + 1));
            h_history[i * 2 + 1].setText("Max:" + periods[i]);

            h_history[i * 2].setOnClickListener(onSortBtn);
            h_history[i * 2 + 1].setOnClickListener(onSortBtn);
            headerLayout.addView(h_history[i * 2]);
            headerLayout.addView(h_history[i * 2 + 1]);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button hName, hPrice, hMinPrice, hMaxPrice;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registBroadcastReceiver();

        initDrawerLayout(savedInstanceState);
        mDataList = new ArrayList<Record>();
        mViewList = Record.getDummyList();
        mDataList.addAll(mViewList);
        mTemp = new ArrayList<Record>();
        mHandler = new Handler(Looper.getMainLooper());

        mRecordNameAdapter =
                new RecordNameAdapter(this.getApplicationContext(), mViewList);

        mRecordContentsAdapter =
                new RecordContentsAdapter(this.getApplicationContext(), mViewList);

        mRecordListView = (ListView) this.findViewById(R.id.record_list);
        mRecordNameListView = (ListView) this.findViewById(R.id.record_name_list);

        hName = (Button) this.findViewById(R.id.h_name);
        hPrice = (Button) this.findViewById(R.id.h_price);
        makeHeader();

        hName.setOnClickListener(onSortBtn);
        hPrice.setOnClickListener(onSortBtn);

        mRecordNameListView.setAdapter(mRecordNameAdapter);
        mRecordListView.setAdapter(mRecordContentsAdapter);

        View.OnTouchListener touchListener = new View.OnTouchListener() {
            boolean dispatched = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.equals(mRecordListView) && !dispatched) {
                    dispatched = true;
                    mRecordNameListView.dispatchTouchEvent(event);
                } else if (v.equals(mRecordNameListView) && !dispatched) {
                    dispatched = true;
                    mRecordListView.dispatchTouchEvent(event);
                }
                dispatched = false;
                return false;
            }
        };

        mRecordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String simpleId = mViewList.get(position).getId();
                String url = "http://m.stock.naver.com/item/main.nhn#/stocks/";

                url += simpleId;
                url += "/total";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        mRecordListView.setOnTouchListener(touchListener);
        mRecordNameListView.setOnTouchListener(touchListener);

        initSocketIO();

        getInstanceIdToken();
    }

    @Override
    protected void onDestroy() {
        Log.e("onDestroy", "destroy");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        if (socket != null) {
            socket.disconnect();
        }
        super.onDestroy();
    }

    private boolean updateHistory(Record record, JSONArray data) throws JSONException {
        int[] history;
        JSONObject cursor;
        history = record.getHistoryPrice();
        boolean ret;

        ret = true;

        for (int i = 0; i < data.length(); i++) {
            // Log.e("updateHistory", "i: " + i);
            cursor = data.getJSONObject(i);
            history[i * 2] = cursor.getInt("min");
            history[i * 2 + 1] = cursor.getInt("max");

            if (history[i * 2] == 0 || history[i * 2 + 1] == 0) {
                ret = false;
                break;
            }
        }

        record.makeRatio();
        return ret;
    }

    private void updateDataList(JSONObject obj) {
        JSONObject cursor;
        boolean ret;
        Record record;
        Log.e("Tmp length", "length: " + mTemp.size());
        Iterator<Record> iter;

        iter = mTemp.iterator();

        while(iter.hasNext()) {
            try {
                record = iter.next();
                cursor = obj.getJSONObject(record.getId());
                ret = updateHistory(record, cursor.getJSONArray("data"));
                if (ret == false) {
                    iter.remove();
                }
                // Log.e("history", obj.toString());

            } catch (JSONException e) {
                //Log.e("update Data View", e.getMessage());
                iter.remove();
            }
        }

        mDataList.clear();
        mDataList.addAll(mTemp);
        refresh(mDataList, mViewList);
    }

    private Socket socket;

    private void initSocketIO() {
        Log.e("initSocketIO", "initIO");
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.reconnection = false;
            socket = IO.socket("http://45.32.18.89:9999", opts);
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("hello", "world");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    socket.emit("get codes", "Hello World");
                    Log.e("Connect", "emit Hello World");
                }
            }).on("codes", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    JSONArray obj = (JSONArray) args[0];
                    try {
                        updateGlobalList(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    socket.emit("history", "");
                    Log.e("message", "history sent, length: " + obj.length());

                }
            }).on("historydata", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    JSONObject obj = (JSONObject) args[0];
                    Log.e("history data", "history");
                    updateDataList(obj);
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Log.e("Disconnect", "disconnect received");
                }
            });
            socket.connect();
            Log.e("Connect", "Connect!: ");
        } catch (URISyntaxException e) {
            Log.e("Connect", "error: " + e.getMessage());
        }
    }

    private void updateGlobalList(JSONArray array) throws JSONException {
        JSONObject obj;
        List<Record> local = new ArrayList<Record>();
        HashMap<String, Boolean> isKOSDAQ = new HashMap<String, Boolean>();
        Record current;
        String str;


        isKOSDAQ.put("KOSDAQ", true);
        isKOSDAQ.put("KOSPI", false);

        mTemp.clear();
        for (int i = 0; i < array.length(); i++) {
            obj = array.getJSONObject(i);

            //   Log.e("obj", obj.toString());
            current = new Record(obj.getString("code"), obj.getString("company"),
                    Integer.parseInt(obj.getString("price").replaceAll(",", "")), 0, 0,
                    isKOSDAQ.get(obj.getString("type")));

            mTemp.add(current);
        }
    }

    private List<Record> mTemp;


    private void sortBySomething(View v, List<Record> list) {
        int fieldId;

        fieldId = v.getId();

        RecordComparator recordComparator;
        RecordComparator.RecordFieldComparator comparator;

        recordComparator = RecordComparator.getInstance();
        if (v.getTag() != null) {
            comparator = recordComparator.getComparator(fieldId, (Integer) v.getTag());
        } else {
            comparator = recordComparator.getComparator(fieldId, -1);
        }
        comparator.updateState();
        Collections.sort(list, comparator);

        invalidateListView();
    }

    private String token;
    public void getInstanceIdToken() {
        if (checkPlayServices()) {
            Log.e("getInstanceIdToken", "startService");
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);

            Log.e("getInstanceIdToken", "After startService");
        }
    }

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
                Log.e(TAG, "error log supported.");
            } else {
                Log.e(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        Log.e(TAG, "success");
        return true;
    }

    private void invalidateListView() {
        mHandler.postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {
                mRecordNameAdapter.notifyDataSetChanged();
                mRecordContentsAdapter.notifyDataSetChanged();
            }
        });
    }

    private View.OnClickListener onSortBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sortBySomething(v, mViewList);
        }
    };

    private void refresh(List<Record> datalist, List<Record> viewlist) {
        viewlist.clear();
        viewlist.addAll(datalist);
        invalidateListView();
    }

    private void filterByMode(List<Record> datalist, List<Record> viewlist, int mode) {
        if (mode == 2) {
            viewlist.clear();
            viewlist.addAll(datalist);
        } else {
            viewlist.clear();
            viewlist.addAll(datalist);
            Iterator<Record> iter = viewlist.iterator();
            while (iter.hasNext()) {
                Record record = iter.next();
                if (mode == 0 && record.isKosdaq() == false) {
                    iter.remove();
                } else if (mode == 1 && record.isKosdaq()) {
                    iter.remove();
                }
            }
        }
        invalidateListView();
    }

    private int mode = 0;


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.e("pos", "position: "+ position);
        if (position == 1) {
            refresh(mDataList, mViewList);
            mDrawerLayout.closeDrawer(this.mDrawerList);
        } else if (position == 2) {
            Dialog dialog = createDialog();
            dialog.show();
        } else if (position == 3) {
            mode = (mode + 1) % 3;
            TextView txtView = (TextView) view;
            txtView.setText(mKinds[mode]);
            filterByMode(mDataList, mViewList, mode);
        } else {
            // getInstanceIdToken();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException e) {
                    // Log.w("setNumberPickerTextColor", e.getMessage());
                } catch (IllegalAccessException e) {
                    // Log.w("setNumberPickerTextColor", e.getMessage());
                } catch (IllegalArgumentException e) {
                    //   Log.w("setNumberPickerTextColor", e.getMessage());
                }
            }
        }
        return false;
    }

    private void filterList(List<Record> mViewList, int year, boolean isMax, double percentage) {
        Iterator<Record> cursor = mViewList.iterator();

        while (cursor.hasNext()) {
            Record record = cursor.next();
            if (isMax) {
                Log.e("filter", "record: " + record.getHistoryRatio()[year * 2 + 1] +
                        "percentage: " + percentage);
                if (record.getHistoryRatio()[year * 2 + 1] < percentage) {
                    cursor.remove();
                }
            } else {
                if (record.getHistoryRatio()[year * 2] > percentage) {
                    cursor.remove();
                }
            }
        }
        invalidateListView();
    }

    private NumberPicker picker_decimal;
    private NumberPicker picker_float;
    private ToggleButton mToggle_btn;
    private Spinner mSpinner;
    private TextView textPoint;
    private TextView textPercentage;

    public Dialog createDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_search, null);
        mSpinner = (Spinner) view.findViewById(R.id.duration);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.duration,
                android.R.layout.simple_dropdown_item_1line);

        picker_decimal = (NumberPicker) view.findViewById(R.id.picker_decimal);
        mToggle_btn = (ToggleButton) view.findViewById(R.id.toggle_min_max);


        picker_decimal.setMaxValue(300);
        picker_decimal.setMinValue(95);
        picker_decimal.setValue(100);
        picker_decimal.setWrapSelectorWheel(true);

        picker_float = (NumberPicker) view.findViewById(R.id.picker_float);

        picker_float.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });

        textPoint = (TextView) view.findViewById(R.id.txt_floating_point);
        textPercentage = (TextView) view.findViewById(R.id.txt_percentage);

        picker_float.setMaxValue(99);
        picker_float.setMinValue(0);
        picker_float.setWrapSelectorWheel(true);

        textPoint.setTextColor(Color.BLUE);
        textPercentage.setTextColor(Color.BLUE);
        setNumberPickerTextColor(picker_decimal, Color.BLUE);
        setNumberPickerTextColor(picker_float, Color.BLUE);

        mToggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int color;
                int min, max, defaultValue;
                if (isChecked) {
                    color = Color.RED;
                    min = 0;
                    defaultValue = 90;
                    max = 200;

                } else {
                    min = 95;
                    defaultValue = 100;
                    max = 300;
                    color = Color.BLUE;
                }

                picker_decimal.setMaxValue(max);
                picker_decimal.setMinValue(min);
                picker_decimal.setValue(defaultValue);

                textPoint.setTextColor(color);
                textPercentage.setTextColor(color);
                setNumberPickerTextColor(picker_decimal, color);
                setNumberPickerTextColor(picker_float, color);
            }
        });

        mSpinner.setAdapter(adapter);


        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int position = mSpinner.getSelectedItemPosition();
                        CharSequence sequence = (CharSequence) mSpinner.getSelectedItem();
                        double percentage;

                        percentage = getDoubleFromPicker(picker_decimal, picker_float);

                        filterList(mViewList, position, mToggle_btn.isChecked(), percentage);

                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        Dialog dialog = builder.create();
        dialog.getWindow().setGravity(Gravity.TOP);

        return dialog;
    }


    public Dialog createDialog2() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_search, null);
        mSpinner = (Spinner) view.findViewById(R.id.duration);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.duration,
                android.R.layout.simple_dropdown_item_1line);

        picker_decimal = (NumberPicker) view.findViewById(R.id.picker_decimal);
        mToggle_btn = (ToggleButton) view.findViewById(R.id.toggle_min_max);


        picker_decimal.setMaxValue(300);
        picker_decimal.setMinValue(95);
        picker_decimal.setValue(100);
        picker_decimal.setWrapSelectorWheel(true);

        picker_float = (NumberPicker) view.findViewById(R.id.picker_float);

        picker_float.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });

        textPoint = (TextView) view.findViewById(R.id.txt_floating_point);
        textPercentage = (TextView) view.findViewById(R.id.txt_percentage);

        picker_float.setMaxValue(99);
        picker_float.setMinValue(0);
        picker_float.setWrapSelectorWheel(true);

        textPoint.setTextColor(Color.BLUE);
        textPercentage.setTextColor(Color.BLUE);
        setNumberPickerTextColor(picker_decimal, Color.BLUE);
        setNumberPickerTextColor(picker_float, Color.BLUE);

        mToggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int color;
                int min, max, defaultValue;
                if (isChecked) {
                    color = Color.RED;
                    min = 0;
                    defaultValue = 90;
                    max = 200;

                } else {
                    min = 95;
                    defaultValue = 100;
                    max = 300;
                    color = Color.BLUE;
                }

                picker_decimal.setMaxValue(max);
                picker_decimal.setMinValue(min);
                picker_decimal.setValue(defaultValue);

                textPoint.setTextColor(color);
                textPercentage.setTextColor(color);
                setNumberPickerTextColor(picker_decimal, color);
                setNumberPickerTextColor(picker_float, color);
            }
        });

        mSpinner.setAdapter(adapter);


        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int position = mSpinner.getSelectedItemPosition();
                        CharSequence sequence = (CharSequence) mSpinner.getSelectedItem();
                        double percentage;

                        percentage = getDoubleFromPicker(picker_decimal, picker_float);

                        filterList(mViewList, position, mToggle_btn.isChecked(), percentage);

                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        Dialog dialog = builder.create();
        dialog.getWindow().setGravity(Gravity.TOP);

        return dialog;
    }

    private double getDoubleFromPicker(NumberPicker decimal_pick, NumberPicker float_pick) {
        double ret = 0;
        ret = decimal_pick.getValue();
        ret += (double) float_pick.getValue() / 100;

        return ret;
    }

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public void registBroadcastReceiver(){
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();


                if(action.equals(QuickstartPreferences.REGISTRATION_READY)){
                    // 액션이 READY일 경우
                    Log.e("ready", "action ready");

                } else if(action.equals(QuickstartPreferences.REGISTRATION_GENERATING)){
                    Log.e("generating", "generating..");
                    // 액션이 GENERATING일 경우

                } else if(action.equals(QuickstartPreferences.REGISTRATION_COMPLETE)){
                    // 액션이 COMPLETE일 경우
                    String tkn = intent.getStringExtra("token");
                    token = tkn;
                   Log.e("token", "token_id: " + token);
                }

            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_READY));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_GENERATING));
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 앱이 화면에서 사라지면 등록된 LocalBoardcast를 모두 삭제한다.
     */
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

}