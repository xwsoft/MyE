package com.mye.gmobile.view; 

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

/** 
 * @author xwsoft 
 * @version date£º2013-4-27 ÏÂÎç5:34:29 
 * 
 */
public class DashboardViewPagerItemView  implements IViewPagerItemView{

	public Context _context; 
	public DashboardViewPagerItemView(){

	}
	
	public DashboardViewPagerItemView(Context context){
		_context = context;
	}
	
	@Override
	public void fillData() {
		LayoutInflater inflater = LayoutInflater.from(_context);
	}

}
