package com.mye.gmobile.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mye.gmobile.R;
import com.mye.gmobile.common.communication.ReqParamMap;
import com.mye.gmobile.common.communication.ResParamMap;
import com.mye.gmobile.common.component.CustomDialog;
import com.mye.gmobile.common.component.WarningInfo;
import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.common.constant.ErrorCode;
import com.mye.gmobile.common.constant.HttpURL;
import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.common.log.LogUtil;
import com.mye.gmobile.model.GlobalModels;
import com.mye.gmobile.model.House;
import com.mye.gmobile.util.CheckUtil;
import com.mye.gmobile.util.DataStack;

public class LoginActivity extends BaseActivity {
	CustomDialog dialog_advanced;
	Button btn_login;
	EditText txt_username;
	EditText txt_password;
	CheckBox chk_remember;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //自动适应输入法
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_login);
		_context = this;
		initBase();
		String timeFormat = android.provider.Settings.System.getString(getContentResolver(),android.provider.Settings.System.TIME_12_24);
		GlobalModels.timeFormat = timeFormat;
		
		btn_login = (Button)findViewById(R.id.btn_login);
		txt_username = (EditText)findViewById(R.id.txt_username);
		txt_password = (EditText)findViewById(R.id.txt_password);
		chk_remember = (CheckBox)findViewById(R.id.chk_remember);
		String tmpUN = DataStack.getString(_context, "UserInfo","UserName", "");
		String tmpPW = DataStack.getString(_context, "UserInfo","Password", "");
		if(tmpUN!=null && !tmpUN.equals("")){
			txt_username.setText(tmpUN);
			txt_password.setText(tmpPW);
			chk_remember.setChecked(true);
		}
		btn_login.setOnClickListener(loginClickListener);

		
	}

	/**
	 * 登录按钮事件
	 */
	View.OnClickListener loginClickListener = new View.OnClickListener() {
		public void onClick(View v) {
			startProgressDialog();
			String strUserName = txt_username.getText().toString();
			String strPassword = txt_password.getText().toString();
			if(strUserName==null || strUserName.equals("")){
				showToast("Please enter a username.");
				stopProgressDialog();
				return;
			}
			if(strPassword==null || strPassword.equals("")){
				showToast("Please enter a password.");
				stopProgressDialog();
				return;
			}
			ReqParamMap reqParaMap = new ReqParamMap();
			reqParaMap.put("username", strUserName);
			reqParaMap.put("password", strPassword);
			reqParaMap.put("checkCode", "1234567890");
			reqParaMap.put("type", "1");
			GlobalModels.getHandler().sendAndReceiveMessageAsynchronous(reqParaMap, HttpURL.LOGIN, myHandler, CommMsgID.LOGIN);
		}
	};
	
	/**
	 * 异步回调处理
	 */
	public Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommMsgID.LOGIN:
				if (msg.getData() == null) {
					return;
				}
				GlobalModels.houseList.transferFormJson(msg.getData().getString(ResParamMap.KEY_RECEIVE_MESSAGE));
				if (GlobalModels.houseList.isSuccess()) {
					GlobalModels.userid = GlobalModels.houseList.getUserId();
					GlobalModels.username = GlobalModels.houseList.getUserName();
					
					//账户信息保存与清除
					if (chk_remember.isChecked()) {
						DataStack.updateStringData(_context, "UserInfo","UserName", txt_username.getText().toString());
						DataStack.updateStringData(_context, "UserInfo","Password", txt_password.getText().toString());
					} else {
						DataStack.clear(_context, "UserInfo");
					}
					if (isExistHardWare()) {
						WarningInfo alert = WarningInfo.getInstance(_context);
						String info = "This application must work with MyE Smart Thermostat. If you have already purchased one, please register it through the website first.";
						alert.showWarningInfo(info, 14, Color.WHITE);
						stopProgressDialog();
						return;
					}
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					startActivity(intent);
					showToast("Login Success.");
					stopProgressDialog();
				} else {
					WarningInfo alert = WarningInfo.getInstance(_context);
					String info = "Username or password is not correct.";
					alert.showWarningInfo(info, 14, Color.WHITE);
					stopProgressDialog();
				}
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
	
	private boolean isExistHardWare() {
		for (int i = 0; i < GlobalModels.houseList.getHouses().length; i++) {
			House house = GlobalModels.houseList.getHouses()[i];
			if (house.getM_id()!=null && !house.getM_id().equals("")) {
				return false;
			}
		}
		return true;
	}
	
	
	//显示高级设置对话框
	private static final boolean showServerIPAndPortSettingPlan = true;
	public boolean onCreateOptionsMenu(Menu menu) {
		if (showServerIPAndPortSettingPlan) {
			menu.add(Menu.NONE, Menu.FIRST + 1, 1, "Advanced Settings").setIcon(android.R.drawable.ic_menu_preferences);
			menu.add(Menu.NONE, Menu.FIRST + 2, 2, "Exit").setIcon(android.R.drawable.ic_menu_close_clear_cancel);
		}
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		if (showServerIPAndPortSettingPlan) {
			switch (item.getItemId()) {
			case Menu.FIRST + 1:
				showAdvanceSettings();
				break;
			case Menu.FIRST + 2:
				startProgressDialog();
				finish();
				System.exit(0);
				break;
			default:
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	private void showAdvanceSettings() {
		dialog_advanced = new CustomDialog(_context,R.style.dialog_advance_settings,R.layout.dialog_advancesettings);
		dialog_advanced.setTitle("Advance Settings");
		dialog_advanced.show();
		//Dialog中有EditText时必须要加上这句才能有输入法，且必须要show()之后
		dialog_advanced.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
		EditText txt_serveraddress = (EditText)dialog_advanced.findViewById(R.id.txt_serveraddress);
		EditText txt_serverport = (EditText)dialog_advanced.findViewById(R.id.txt_serverport);
		EditText txt_serveruser = (EditText)dialog_advanced.findViewById(R.id.txt_serveruser);
		EditText txt_serverpwd = (EditText)dialog_advanced.findViewById(R.id.txt_serverpwd);
		txt_serveraddress.setText(DataStack.getString(_context, "MyE",Model.Adv_Settings_Server_Addr, GlobalModels.server_address));
		txt_serverport.setText(DataStack.getInt(_context, "MyE",Model.Adv_Settings_Server_Port, GlobalModels.server_port)+"");
		txt_serveruser.setText(DataStack.getString(_context, "MyE",Model.Adv_Settings_Server_UserName, GlobalModels.server_username));
		txt_serverpwd.setText(DataStack.getString(_context, "MyE",Model.Adv_Settings_Server_PWD, GlobalModels.server_password));
	
		Button btn_save = (Button)dialog_advanced.findViewById(R.id.btn_save);
		Button btn_cancel = (Button)dialog_advanced.findViewById(R.id.btn_cancel);
		btn_save.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				EditText txt_serveraddress = (EditText)dialog_advanced.findViewById(R.id.txt_serveraddress);
				EditText txt_serverport = (EditText)dialog_advanced.findViewById(R.id.txt_serverport);
				EditText txt_serveruser = (EditText)dialog_advanced.findViewById(R.id.txt_serveruser);
				EditText txt_serverpwd = (EditText)dialog_advanced.findViewById(R.id.txt_serverpwd);
				String serveraddress = txt_serveraddress.getText().toString();
				String serverport = txt_serverport.getText().toString();
				String serveruser = txt_serveruser.getText().toString();
				String serverpwd = txt_serverpwd.getText().toString();
				
		        if (!CheckUtil.checkPortFormat(serverport)) {
		            Toast.makeText(_context, "Please check the port. ", Toast.LENGTH_LONG).show();
		            return;
		        }
				DataStack.updateStringData(_context, "MyE",Model.Adv_Settings_Server_Addr, serveraddress);
				DataStack.updateIntData(_context, "MyE",Model.Adv_Settings_Server_Port,Integer.parseInt(serverport));
				DataStack.updateStringData(_context, "MyE",Model.Adv_Settings_Server_UserName, serveruser);
				DataStack.updateStringData(_context, "MyE",Model.Adv_Settings_Server_PWD, serverpwd);
				
				GlobalModels.server_username = serveraddress;
				GlobalModels.server_port = Integer.parseInt(serverport);
				GlobalModels.server_username = serveruser;
				GlobalModels.server_password = serverpwd;
				GlobalModels.handler = null;
				dialog_advanced.dismiss();
			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				dialog_advanced.dismiss();
			}
		});
	}
}
