package com.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

public class MainActivity extends ExpandableListActivity {

	private static final String ITEM_NAME = "Item Name";
    private static final String ITEM_SUBNAME = "Item Subname";
    
    private ExpandableListAdapter mExpaListAdap;
	private TextView mTxtResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTxtResult = (TextView) findViewById(R.id.txtResult);

        List<Map<String, String>> groupList = new ArrayList<Map<String, String>>();
        List<List<Map<String, String>>> childList2D = new ArrayList<List<Map<String, String>>>();
     
        for (int i = 0; i < 5; i++) {
            Map<String, String> group = new HashMap<String, String>();
            group.put(ITEM_NAME, "選項群組" + i);
            group.put(ITEM_SUBNAME, "說明" + i);
            groupList.add(group);

            List<Map<String, String>> childList = new ArrayList<Map<String, String>>();
            for (int j = 0; j < 2; j++) {
                Map<String, String> child = new HashMap<String, String>();
                child.put(ITEM_NAME, "選項" + i + j);
                child.put(ITEM_SUBNAME, "說明" + i + j);
                childList.add(child);
            }
            childList2D.add(childList);
        }

        // 設定我們的ExpandableListAdapter
        mExpaListAdap = new SimpleExpandableListAdapter(
            this,
            groupList,
            android.R.layout.simple_expandable_list_item_2,
            new String[] {ITEM_NAME, ITEM_SUBNAME},                   
            new int[] {android.R.id.text1, android.R.id.text2},
            childList2D,                                     
            android.R.layout.simple_expandable_list_item_2,
            new String[] {ITEM_NAME, ITEM_SUBNAME},
            new int[] {android.R.id.text1, android.R.id.text2}
            );

        setListAdapter(mExpaListAdap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		String s = "選擇：群組" + groupPosition + ", 選項" + childPosition + ", ID" + id;
    	mTxtResult.setText(s);

    	return super.onChildClick(parent, v, groupPosition, childPosition, id);
	}
}
