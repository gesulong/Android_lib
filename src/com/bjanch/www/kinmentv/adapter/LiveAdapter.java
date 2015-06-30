package com.bjanch.www.kinmentv.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bjanch.www.kinmentv.R;
import com.bjanch.www.kinmentv.util.Constants;
import com.bjanch.www.kinmentv.util.SharePrefUtils;

import java.util.ArrayList;
import java.util.List;

public class LiveAdapter extends BaseAdapter {

	private Activity activity;
	private Item item;
	private List<String> proNameArray;
	private int type = 0;   //0国内，1国外，2锁码
	private int isb = 0;
	private int pos = 0;
	
	public LiveAdapter(Activity activity) {
		this.activity = activity;
		proNameArray = new ArrayList<>();
	}
	
	public void putProNameArray(List<String> proNameArray, int i) {
		this.proNameArray = proNameArray;
		type = i;
		notifyDataSetChanged();
	}
	
	public void changeLivingState(int i, int p) {
		isb = SharePrefUtils.getInt(Constants.LIVE_ISB, 0);
		pos = p;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return proNameArray.size();
	}

	@Override
	public Object getItem(int position) {
		return proNameArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			item = new Item();
			convertView = activity.getLayoutInflater().inflate(R.layout.live_item_layout, null);
			item.view = (TextView) convertView.findViewById(R.id.textview);
			item.view.setSelected(true);
			convertView.setTag(item);
		} else{
			item = (Item) convertView.getTag();
		}
		item.view.setText(proNameArray.get(position));
		item.view.setTextColor(0xFFFFFFFF);
		if(isb == type) {
			if(position == pos) {
				item.view.setTextColor(0xff74f40d);
			}
		}

		return convertView;
	}
	
	class Item {
		TextView view;
	}

}
