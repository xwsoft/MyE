package com.mye.gmobile.view.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mye.gmobile.R;
import com.mye.gmobile.model.GlobalModels;
import com.mye.gmobile.model.Thermostat;

public class DialogSelectThermostatListAdapter extends BaseAdapter {

	private ArrayList<Thermostat> mData = new ArrayList<Thermostat>();
	private LayoutInflater mInflater;
	public static Context _context = null;
	public DialogSelectThermostatListAdapter() {
	}
	
	public DialogSelectThermostatListAdapter(Context context) {
		_context = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public void addItem(List<Thermostat> listThermostat) {
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
	public Thermostat getItem(int position) {
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
			convertView = mInflater.inflate(R.layout.listitem_dialog_s_t_layout,null);

			holder = new ViewHolder();
			holder.thermostat_name = (TextView) convertView.findViewById(R.id.thermostat_name);
			holder.thermostat_id = (TextView) convertView.findViewById(R.id.thermostat_id);
			holder.position = position;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.thermostat_name.setText(mData.get(position).getT_name());
		holder.thermostat_id.setText(mData.get(position).getT_id());

		if(mData.get(position).getT_id().equals(GlobalModels.houseList.getCurrentHouse().getDefualtThermostat())){
			RelativeLayout relativeLayout=(RelativeLayout)convertView.findViewById(R.id.l_tid);
			relativeLayout.setBackgroundResource(R.color.dialog_switch_t_listitem_sel_row_color);
			Log.i("T:", mData.get(position).getT_id());
		}
		
/*		RelativeLayout relativeLayout=(RelativeLayout)convertView.findViewById(R.id.l_tid);
		relativeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				TextView t_id = (TextView)v.findViewById(R.id.thermostat_id);
				GlobalModels.houseList.getHouseById(GlobalModels.houseID).setDefualtThermostat(t_id.getText().toString());
				GlobalModels.builder_dialog.create().dismiss();
				Intent intent = new Intent(_context,DashboardActivity.class);
				_context.startActivity(intent);
				Toast.makeText(v.getContext(), t_id.getText(), Toast.LENGTH_SHORT).show();
			}
		});*/
		
		
		
		
		return convertView;
	}
	
	public class ViewHolder {
		public TextView thermostat_id;
		public TextView thermostat_name;
		public int position;
	}
}


