package com.mye.gmobile.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mye.gmobile.R;
import com.mye.gmobile.common.communication.ResParamMap;
import com.mye.gmobile.common.component.CustomDialog;
import com.mye.gmobile.common.component.CustomProgressDialog;
import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.common.constant.ErrorCode;
import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.common.constant.TipMessage;
import com.mye.gmobile.common.constant.TipMessage.TipMessages;
import com.mye.gmobile.util.CheckUtil;

public abstract class BaseActivity extends Activity {
	private static AnimationDrawable anim;
	private static RelativeLayout l_loading;
	private ImageView img_loading;
	
	private Handler handler = new Handler();
	public Context _context;
	public CustomProgressDialog progressDialog = null;	
/*    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	//showToast("keydown back");
        }
        return super.onKeyDown(keyCode, event);
    }
	
	public boolean ResultCheck(Message msg){
		boolean flag = false;	
		if(msg.what == CommMsgID.COMMON_ERROR){
			Long errorCode = msg.getData().getLong(ResParamMap.KEY_ERROR);
			String message = msg.getData().getString(ResParamMap.KEY_ERROR_MESSAGE);
			if (errorCode == ErrorCode.COMMUNICATION_TIMEOUT_NO) {
				message = ErrorCode.CONNECTION_TIMEOUT;
			}
			CheckUtil.alertErrorDialog(_context, message);
			return false;
		}
		String data = msg.getData().getString(ResParamMap.KEY_RECEIVE_MESSAGE);
		if (data != null) {
			data = CheckUtil.cut(data);
			if (Model.HARDWARE_STATUS_UNCONNECTION.equals(data)	|| Model.HARDWARE_STATUS_REMOTE_UNCONTROLLABLE.equals(data)) {
				if (Model.HARDWARE_STATUS_UNCONNECTION.equals(data)) {
					Toast.makeText(_context, "No Connection",Toast.LENGTH_SHORT).show();
					//GlobalModels.setCurrentThermostatConnection(Model.T_CONNECTION_FALSE);
				} else if (Model.HARDWARE_STATUS_REMOTE_UNCONTROLLABLE.equals(data)) {
					Toast.makeText(_context, "Remote No",Toast.LENGTH_SHORT).show();
					//GlobalModels.setCurrentThermostatConnection(Model.T_CONNECTION_TRUE);
					//GlobalModels.setCurrentThermostatRemote(Model.T_REMOTE_FALSE);
				}
			} else if (data.equalsIgnoreCase("fail")) {
				Toast.makeText(_context, "Data Commit Failed",Toast.LENGTH_SHORT).show();
			}else{
				flag = true;
			}
		}
		return flag;
	}
	
    /**
     * 初始化基本公用类
     */
    public void initBase(){
		initTips();
		showTips();
    }

	public void showToast(final String text) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(_context, text, Toast.LENGTH_SHORT).show();
			}
		});
	}

    public void startProgressDialog(){
    	stopProgressDialog();
        progressDialog = CustomProgressDialog.createDialog(_context);
        //progressDialog.setMessage(" Loading...");
        progressDialog.show();
    }

    public void stopProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
    	
	
	/*
	 * Tips信息处理
	 */
	public CustomDialog tips_dialog;
	protected TipMessages currentTip;
	private TipMessages[] enabledtips;
	
	/**
	 * 子类要覆盖该类
	 * @return
	 */
	protected TipMessages[] getTips(){
		return null;
	}
	
	protected void initTips(){
		TipMessages[] tips = getTips();
		if(tips != null){
			int count = 0;
			for(int i = 0;i<tips.length;i++){
				if(TipMessage.isTipEnabled(this,tips[i])){
					count ++;
				}
			}
			enabledtips = new TipMessages[count];
			count = 0;
			for(int i = 0;i<tips.length;i++){
				if(TipMessage.isTipEnabled(this,tips[i])){
					enabledtips[count] = tips[i] ;
					count ++;
				}
			}
		}
	}


	public void showTips(){
		if(enabledtips!= null && enabledtips.length > 0 ){
			if(TipMessage.isTipEnabled(this, enabledtips[0])){
				boolean enabled_next = false;
				if(enabledtips.length>1){
					enabled_next = true;
				}
				TipsDialog(TipMessage.getTipMessage(enabledtips[0]),enabled_next);
				currentTip = enabledtips[0];
			}
			 
		}
	}
	
	/**
	 * 提示消息对话框
	 * @param strMsg 消息内容 
	 * @param enabled_next 是否有多条提示
	 */
	public void TipsDialog(String strMsg,boolean enabled_next){
		 tips_dialog = new CustomDialog(this,R.style.dialog,R.layout.dialog_tips_layout);//创建Dialog并设置样式主题
		 tips_dialog.show();
		 TextView txt_content = (TextView)tips_dialog.findViewById(R.id.txt_content);
		 txt_content.setText(strMsg);
		 Button btn_close = (Button)tips_dialog.findViewById(R.id.tips_btn_close);
		 Button btn_next = (Button)tips_dialog.findViewById(R.id.tips_btn_next);
		 btn_close.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					CheckBox chk = (CheckBox)tips_dialog.findViewById(R.id.tips_chk);
					if(chk.isChecked()){
						TipMessage.disableTip(BaseActivity.this, currentTip);
					}
					tips_dialog.dismiss();
				}
		 });
		 
		 btn_next.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					TipMessages[] tips = getTips();
					CheckBox chk = (CheckBox)tips_dialog.findViewById(R.id.tips_chk);
					for(int i = 0;i<tips.length;i++){
						if(currentTip == enabledtips[i]){
							if(chk.isChecked()){
								TipMessage.disableTip(BaseActivity.this, currentTip);
								chk.isChecked();
							}
							if(i<enabledtips.length-1){
								TextView txt_content = (TextView)tips_dialog.findViewById(R.id.txt_content);
								txt_content.setText(TipMessage.getTipMessage(enabledtips[i+1]));
								chk.setChecked(false);
								currentTip = enabledtips[i+1];
							}
							if(i+1 == enabledtips.length-1){
								tipsDialogDisbledNext();
							}
							
						}
					}
				}
		 });
		 if(!enabled_next){
			 tipsDialogDisbledNext();
		 }
	}
	
	private void tipsDialogDisbledNext(){
		Button btn_next = (Button)tips_dialog.findViewById(R.id.tips_btn_next);
		btn_next.setEnabled(false);
		btn_next.setTextColor(Color.DKGRAY);
	}
	
}
