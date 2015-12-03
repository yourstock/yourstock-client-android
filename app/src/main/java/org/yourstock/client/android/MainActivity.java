package org.yourstock.client.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.yourstock.client.android.org.yourstock.client.android.Adapter.MenuAdapter;
import org.yourstock.client.android.org.yourstock.client.android.Adapter.RecordAdapter;
import org.yourstock.client.android.org.yourstock.client.android.Adapter.RecordContentsAdapter;
import org.yourstock.client.android.org.yourstock.client.android.Adapter.RecordNameAdapter;
import org.yourstock.client.android.org.yourstock.client.android.Bean.Record;
import org.yourstock.client.android.org.yourstock.client.android.Bean.RecordComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity implements MenuAdapter.OnItemClickListener{

    private RecordAdapter mRecordNameAdapter, mRecordContentsAdapter;

    private ListView mRecordListView;
    private ListView mRecordNameListView;
    private List<Record> mViewList;
    private List<Record> mDataList;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private RecyclerView mDrawerList;
    private String[] mMenus;
    /* Called whenever we call invalidateOptionsMenu() */

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)  {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
    //    menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private void initDrawerLayout(Bundle savedInstanceState) {
        mTitle = mDrawerTitle = getTitle();
        mMenus = getResources().getStringArray(R.array.menu_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (RecyclerView) findViewById(R.id.left_drawer);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mDrawerList.setLayoutManager(mLinearLayoutManager);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        mDrawerList.setHasFixedSize(true);

        mDrawerList.setAdapter(new MenuAdapter(mMenus, this));

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button hName, hPrice, hMinPrice, hMaxPrice;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initDrawerLayout(savedInstanceState);
        mDataList = new ArrayList<Record>();
        mViewList = Record.getDummyList();
        mDataList.addAll(mViewList);

        mRecordNameAdapter =
                new RecordNameAdapter(this.getApplicationContext(), mViewList);

        mRecordContentsAdapter =
                new RecordContentsAdapter(this.getApplicationContext(), mViewList);

        mRecordListView = (ListView) this.findViewById(R.id.record_list);
        mRecordNameListView = (ListView) this.findViewById(R.id.record_name_list);

        hName = (Button) this.findViewById(R.id.h_name);
        hPrice = (Button) this.findViewById(R.id.h_price);
        hMinPrice = (Button) this.findViewById(R.id.h_min_price);
        hMaxPrice = (Button) this.findViewById(R.id.h_max_price);

        hName.setOnClickListener(onSortBtn);
        hPrice.setOnClickListener(onSortBtn);
        hMinPrice.setOnClickListener(onSortBtn);
        hMaxPrice.setOnClickListener(onSortBtn);

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

        mRecordListView.setOnTouchListener(touchListener);
        mRecordNameListView.setOnTouchListener(touchListener);
    }

    private void sortBySomething(int fieldId, List<Record> list) {
        RecordComparator recordComparator;
        RecordComparator.RecordFieldComparator comparator;

        recordComparator = RecordComparator.getInstance();
        comparator = recordComparator.getComparator(fieldId);
        comparator.updateState();
        Collections.sort(list, comparator);

        invalidateListView();
    }

    private void invalidateListView() {
        mRecordNameAdapter.notifyDataSetChanged();
        mRecordContentsAdapter.notifyDataSetChanged();
    }

    private View.OnClickListener onSortBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sortBySomething(v.getId(), mViewList);
        }
    };

    @Override
    public void onClick(View view, int position) {
        if (position == 0) {
            Dialog dialog = createDialog();
            dialog.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
       return  super.onOptionsItemSelected(item);
    }


    public Dialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();


        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_search, null);
        final Spinner spinner = (Spinner) view.findViewById(R.id.duration);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.duration,
                android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int position = spinner.getSelectedItemPosition();
                        CharSequence sequence = (CharSequence) spinner.getSelectedItem();

                        Toast.makeText(getApplicationContext(), "pos: " + position + "contents: "+ sequence, Toast.LENGTH_SHORT).show();
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
}