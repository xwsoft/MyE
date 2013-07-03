package com.mye.gmobile.activity; 

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mye.gmobile.R;
import com.mye.gmobile.common.constant.CommMsgID;

/** 
 * @author xwsoft 
 * @version date：2013-4-25 上午10:26:20 
 * 
 */
public class DialogVacationMenuActivity extends BaseActivity implements OnClickListener {

	private TextView txt_edit;
	private TextView txt_delete;
	private LinearLayout layout;
	int position;//vacation index
	String vacation_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_vacation_list_pop_menu);
		txt_delete = (TextView) this.findViewById(R.id.txt_delete);
		txt_edit = (TextView) this.findViewById(R.id.txt_edit);
		
		vacation_name= getIntent().getStringExtra("vacation_name");
		position= getIntent().getIntExtra("position",0);

		
		layout=(LinearLayout)findViewById(R.id.pop_layout);
		
		//添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
		layout.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});
		//添加按钮监听
		txt_delete.setOnClickListener(this);
		txt_edit.setOnClickListener(this);
	}
	
	//实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}

	public void onClick(View v) {
		Intent intent = new Intent();  
		Bundle bundle = new Bundle();  
		bundle.putString("vacation_name", vacation_name);
		bundle.putInt("position", position);
		switch (v.getId()) {
		case R.id.txt_delete:
			bundle.putInt("action", 1);
			intent.putExtras(bundle);
			setResult(CommMsgID.VACATION_MENU, intent);  //设置返回结果
			finish();
			break;
		case R.id.txt_edit:
			bundle.putInt("action", 2);
			intent.putExtras(bundle);
			setResult(CommMsgID.VACATION_MENU, intent);  //设置返回结果
			finish();
			break;
		default:
			break;
		}
		finish();//关闭子窗口，否则数据无法返回
	}
}
