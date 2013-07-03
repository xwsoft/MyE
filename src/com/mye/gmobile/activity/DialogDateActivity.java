package com.mye.gmobile.activity;

import java.sql.Date;
import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mye.gmobile.R;
import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.util.DateUtil;
import com.mye.gmobile.view.wheel.OnWheelChangedListener;
import com.mye.gmobile.view.wheel.WheelView;
import com.mye.gmobile.view.wheel.adapters.ArrayWheelAdapter;
import com.mye.gmobile.view.wheel.adapters.NumericWheelAdapter;

/**
 * @author xwsoft
 * @version date：2013-4-25 上午10:26:20
 * 
 */
public class DialogDateActivity extends BaseActivity implements OnClickListener {
	WheelView whell_set_temp;
	private Button btn_cancel,btn_done;
	private LinearLayout layout;

	private WheelView month;
	private WheelView year;
	private WheelView day;
	Calendar calendar = Calendar.getInstance();
	int id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_date);
		btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
		btn_done = (Button) this.findViewById(R.id.btn_done);
		
		String currDate = getIntent().getStringExtra("currDate");
		id = getIntent().getIntExtra("id", 0);
		calendar.setTime(DateUtil.str2Date(currDate));
		
		month = (WheelView) findViewById(R.id.month);
		year = (WheelView) findViewById(R.id.year);
		day = (WheelView) findViewById(R.id.day);

		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(year, month, day);
			}
		};

		// month
		int curMonth = calendar.get(Calendar.MONTH);
		String months[] = new String[] { "January", "February", "March","April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		month.setViewAdapter(new DateArrayAdapter(this, months, curMonth));
		month.setCurrentItem(curMonth);
		month.addChangingListener(listener);

		// year
		int curYear = calendar.get(Calendar.YEAR);
		year.setViewAdapter(new DateNumericAdapter(this, curYear, curYear + 10,	0));
		year.setCurrentItem(curYear);
		year.addChangingListener(listener);

		// day
		updateDays(year, month, day);
		day.setCurrentItem(calendar.get(Calendar.DAY_OF_MONTH) - 1);
		
		// 添加按钮监听
		btn_cancel.setOnClickListener(this);
		btn_done.setOnClickListener(this);
	}

	void updateDays(WheelView year, WheelView month, WheelView day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());
		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//取得当前月最大天数
		day.setViewAdapter(new DateNumericAdapter(this, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1));
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
	}

	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue,
				int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	/**
	 * Adapter for string based wheel. Highlights the current value.
	 */
	private class DateArrayAdapter extends ArrayWheelAdapter<String> {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateArrayAdapter(Context context, String[] items, int current) {
			super(context, items);
			this.currentValue = current;
			setTextSize(16);
		}

		@Override
		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			if (currentItem == currentValue) {
				view.setTextColor(0xFF0000F0);
			}
			view.setTypeface(Typeface.SANS_SERIF);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			currentItem = index;
			return super.getItem(index, cachedView, parent);
		}
	}

	// 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void onClick(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		switch (v.getId()) {
		case R.id.btn_cancel:
			break;
		case R.id.btn_done:
			Calendar calendar = Calendar.getInstance();
			int _d = (day.getCurrentItem()+1);
			int _m = (month.getCurrentItem()+1);
			int _y = (calendar.get(Calendar.YEAR) + year.getCurrentItem());
			String _strD = (_d<10?("0"+_d):(""+_d));
			String _strM = (_m<10?("0"+_m):(""+_m));
			String strDate = _strM +"/"+ _strD +"/"+ _y +"";
			bundle.putInt("id", id);
			bundle.putString("currDate", strDate);
			intent.putExtras(bundle);
			setResult(CommMsgID.DAILOG_DATE, intent); // 设置返回结果
			finish(); // 关闭子窗口，否则数据无法返回
			break;
		default:
			break;
		}
		finish();
	}
}
