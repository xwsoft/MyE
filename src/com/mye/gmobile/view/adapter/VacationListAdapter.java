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
import com.mye.gmobile.model.Vacation;

public class VacationListAdapter extends BaseAdapter {

	private ArrayList<Vacation> mData = new ArrayList<Vacation>();
	private LayoutInflater mInflater;

	public VacationListAdapter() {
	}
	
	public VacationListAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void addItem(List<Vacation> _list) {
		for(int i=0;i<_list.size();i++){
			mData.add(_list.get(i));
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Vacation getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.listitem_vacationlist_layout,null);

			holder = new ViewHolder();
			holder.img_vacation_type = (ImageView) convertView.findViewById(R.id.img_vacation_type);
			holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
			holder.txt_startDate = (TextView) convertView.findViewById(R.id.txt_startDate);
			holder.txt_endDate = (TextView) convertView.findViewById(R.id.txt_endDate);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txt_name.setText(mData.get(position).getName());	
		int type = mData.get(position).getType();//leaveDate
		if(type==0){//vacation
			holder.txt_startDate.setText(mData.get(position).getLeaveDate());
			holder.txt_endDate.setText(mData.get(position).getReturnDate());
			holder.img_vacation_type.setBackgroundResource(R.drawable.vacation02);
		}else{
			holder.txt_startDate.setText(mData.get(position).getStartDate());
			holder.txt_endDate.setText(mData.get(position).getEndDate());
			holder.img_vacation_type.setBackgroundResource(R.drawable.vacation01);
		}
		return convertView;
	}
	public class ViewHolder {
		public TextView txt_name;
		public TextView txt_startDate;
		public TextView txt_endDate;
		public ImageView img_vacation_type;
		public int position;
	}
	

}

