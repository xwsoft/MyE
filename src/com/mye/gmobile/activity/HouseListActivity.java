package com.mye.gmobile.activity; 

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mye.gmobile.R;
import com.mye.gmobile.common.communication.ReqParamMap;
import com.mye.gmobile.common.communication.ResParamMap;
import com.mye.gmobile.common.component.MyListView;
import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.common.constant.ErrorCode;
import com.mye.gmobile.common.constant.HttpURL;
import com.mye.gmobile.model.GlobalModels;
import com.mye.gmobile.model.House;
import com.mye.gmobile.util.CheckUtil;
import com.mye.gmobile.view.adapter.HouseListAdapter;

/** 
 * @author xwsoft 
 * @version date：2013-4-25 上午10:26:20 
 * 
 */
public class HouseListActivity extends BaseActivity  {

	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;

	private MyListView mPullRefreshListView;
	HouseListAdapter houseListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_houselist);
		_context = this;
		mPullRefreshListView = (MyListView) findViewById(R.id.pull_refresh_list);
/*		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new GetDataTask().execute();
			}
		});
		ImageView mTopImageView = (ImageView) this.findViewById(R.id.lv_backtotop);
		mTopImageView.setVisibility(View.INVISIBLE);
		mPullRefreshListView.setBackToTopView(mTopImageView);
		
		ListView actualListView = mPullRefreshListView.getRefreshableView();*/
		
		houseListAdapter = new HouseListAdapter(this);
		houseListAdapter.addItem(getData());
		houseListAdapter.notifyDataSetChanged();
		mPullRefreshListView.setAdapter(houseListAdapter);//getSimpleAdapter_1());
		//setListViewHeightBasedOnChildren(mPullRefreshListView);
		
		ImageView img_refresh = (ImageView) this.findViewById(R.id.img_refresh);
		img_refresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refresh();
				
			}
		});

		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			@Override  
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				TextView txt_housename = (TextView)arg1.findViewById(R.id.txt_housename);
				showToast(""+txt_housename.getText());
            }  
		});
	}

	/***
	 * 动态设置listview的高度
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// params.height += 5;// if without this statement,the listview will be
		// a
		// little short
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}

	private List<House> getData(){
		List<House> data = new ArrayList<House>();
		House[] houses = GlobalModels.houseList.getHouses();
		if(houses!=null){
			for(int i = 0 ;i<houses.length;i++){
				data.add(houses[i]);
			}
		}
		return data;
	}
	
	public void refresh(){
		startProgressDialog();
		ReqParamMap reqParaMap = new ReqParamMap();
		reqParaMap.put("username", GlobalModels.userid);
		GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.HOUSELIST_VIEW, myHandler, CommMsgID.HOUSELIST_VIEW);
	}
	/**
	 * 异步回调处理
	 */
	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommMsgID.HOUSELIST_VIEW:
				GlobalModels.houseList.transferFormJsonOnlyHouses(msg.getData().getString(ResParamMap.KEY_RECEIVE_MESSAGE));
				houseListAdapter = new HouseListAdapter(_context);
				houseListAdapter.addItem(getData());
				houseListAdapter.notifyDataSetChanged();
				mPullRefreshListView.setAdapter(houseListAdapter);
				stopProgressDialog();
				break;
			case CommMsgID.COMMON_ERROR:
				Long errorCode = msg.getData().getLong(ResParamMap.KEY_ERROR);
				String message = msg.getData().getString(
						ResParamMap.KEY_ERROR_MESSAGE);
				if (errorCode == ErrorCode.COMMUNICATION_TIMEOUT_NO) {
					message = ErrorCode.CONNECTION_TIMEOUT;
				}
				CheckUtil.alertErrorDialog(_context, message);
				stopProgressDialog();
				break;
			}
			super.handleMessage(msg);
		}
	};
}
