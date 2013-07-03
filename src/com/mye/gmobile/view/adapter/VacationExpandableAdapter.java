package com.mye.gmobile.view.adapter; 

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mye.gmobile.R;
import com.mye.gmobile.activity.DialogVacationMenuActivity;
import com.mye.gmobile.activity.VacationEditActivity;
import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.model.GlobalModels;
import com.mye.gmobile.model.Vacation;

/** 
 * @author xwsoft 
 * @version date：2013-6-3 下午8:21:23 
 * 
 */
public class VacationExpandableAdapter extends BaseExpandableListAdapter{

		private Context context;
		private LayoutInflater mChildInflater;
		private LayoutInflater mGroupInflater;
		private List<Vacation> group;

		public VacationExpandableAdapter(Context c, List<Vacation> group) {
			this.context = c;
			mChildInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mGroupInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			this.group = group;
		}

		public Object getChild(int childPosition, int itemPosition) {
			return group.get(childPosition);//group.get(childPosition).getChild(itemPosition);
		}

		public long getChildId(int childPosition, int itemPosition) {

			return itemPosition;
		}

		@Override
		public int getChildrenCount(int index) {
			return 1;//group.get(index).getChildSize();
		}

		public Object getGroup(int index) {
			return group.get(index);
		}

		public int getGroupCount() {
			return group.size();
		}

		public long getGroupId(int index) {
			return index;
		}

		public View getGroupView(int position, boolean flag, View convertView,	ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mGroupInflater.inflate(R.layout.listitem_vacationlist_layout,null);

				holder = new ViewHolder();
				holder.img_vacation_type = (ImageView) convertView.findViewById(R.id.img_vacation_type);
				holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
				holder.txt_startDate = (TextView) convertView.findViewById(R.id.txt_startDate);
				holder.txt_endDate = (TextView) convertView.findViewById(R.id.txt_endDate);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.txt_name.setText(group.get(position).getName());	
			int type = group.get(position).getType();//leaveDate
			if(type==0){//vacation
				holder.txt_startDate.setText(group.get(position).getLeaveDate());
				holder.txt_endDate.setText(group.get(position).getReturnDate());
				holder.img_vacation_type.setBackgroundResource(R.drawable.vacation02);
			}else{
				holder.txt_startDate.setText(group.get(position).getStartDate());
				holder.txt_endDate.setText(group.get(position).getEndDate());
				holder.img_vacation_type.setBackgroundResource(R.drawable.vacation01);
			}
			return convertView;
		}

		public boolean hasStableIds() {
			return false;
		}

		@Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
			String msg = "parent_id = " + groupPosition + " child_id = "+ childPosition;
			//Log.i("isChildSelectable",msg);
        	return true;
        }
		
		@Override
		public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = mGroupInflater.inflate(R.layout.listitem_vacationlist_child_layout,null);
				holder = new ViewHolder();
				holder.btn_edit = (Button) convertView.findViewById(R.id.btn_edit);				
				holder.btn_delete = (Button) convertView.findViewById(R.id.btn_delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.btn_edit.setTag(groupPosition);
			holder.btn_delete.setTag(groupPosition);
			holder.btn_edit.setOnClickListener(new View.OnClickListener() {	
				@Override
				public void onClick(View v) {
					int position = Integer.parseInt(v.getTag().toString());
					Vacation tmp_vacation = GlobalModels.vacationList.getVacationsByIndex(position);	
					Intent intent = new Intent();  
					Bundle bundle = new Bundle();
					bundle.putInt("action", 1);
					bundle.putInt("position", position);
					bundle.putString("vacation_name", tmp_vacation.getName());
					intent.setClass(context,VacationEditActivity.class);  
					intent.putExtras(bundle);
					//startActivityForResult(intent, CommMsgID.VACATION_MENU);
					context.startActivity(intent);
					
				}
			});
			

			return convertView;
		}

		public class ViewHolder {
			public TextView txt_name;
			public TextView txt_startDate;
			public TextView txt_endDate;
			public ImageView img_vacation_type;
			public Button btn_edit;
			public Button btn_delete;
			public int position;
		}
	}
