package com.mye.gmobile.view.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mye.gmobile.R;
import com.mye.gmobile.model.House;

public class HouseListAdapter extends BaseAdapter {

	private ArrayList<House> mData = new ArrayList<House>();
	private LayoutInflater mInflater;

	public HouseListAdapter() {
	}
	
	public HouseListAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void addItem(List<House> listThermostat) {
		for(int i=0;i<listThermostat.size();i++){
			mData.add(listThermostat.get(i));
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public House getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listitem_houselist_layout,null);

			holder = new ViewHolder();
			holder.txt_housename = (TextView) convertView.findViewById(R.id.txt_housename);
			holder.txt_housestat = (TextView) convertView.findViewById(R.id.txt_housestat);
			holder.img_housestat = (ImageView) convertView.findViewById(R.id.img_housestat);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_housename.setText(mData.get(position).getHouseName());	
		int connection = mData.get(position).getConnection();
		holder.txt_housestat.setText(connection==0?"Connected":"Disbaled");
		if(connection==1){
			holder.img_housestat.setBackgroundResource(R.drawable.remote_no);
		}else{
			int keypad_img = (mData.get(position).isContorl()?R.drawable.remote_yes:R.drawable.remote_no);
			holder.img_housestat.setBackgroundResource(keypad_img);
		}
		return convertView;
	}
	public class ViewHolder {
		public TextView txt_housename;
		public TextView txt_housestat;
		public ImageView img_housestat;
		public int position;
	}
	

}

