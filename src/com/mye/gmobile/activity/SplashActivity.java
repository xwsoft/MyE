package com.mye.gmobile.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.mye.gmobile.R;
import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.common.upgrader.Config;
import com.mye.gmobile.common.upgrader.NetworkTool;
import com.mye.gmobile.model.GlobalModels;
import com.mye.gmobile.util.CheckUtil;
import com.mye.gmobile.util.DataStack;
import com.mye.gmobile.util.ReadConfigurationUtil;

public class SplashActivity extends Activity {
	private Context _context;
	private String server_chk_url;//检查服务器版本，及下载地址根路径http://192.168.0.9:65533/download/
	
	private static final int FAILURE = 0; // 失败
	private static final int SUCCESS = 1; // 成功
	private static final int OFFLINE = 2; // 如果支持离线阅读，进入离线模式
	private static final int SHOW_TIME_MIN = 800;//启动页面最短等待时间
	private TextView txt_version;
	
	private static final String TAG = "Splash";
	public ProgressDialog pBar;
	private Handler handler = new Handler();
	private int newVerCode = 0;
	private String newVerName = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		_context = this;
		//初始化读取value文件夹下的资源文件
		ReadConfigurationUtil.initConfiguration(_context);
		CheckUtil.initContext(_context);
		GlobalModels.server_address = DataStack.getString(_context, "MyE",Model.Adv_Settings_Server_Addr, GlobalModels.server_address);
		GlobalModels.server_port = DataStack.getInt(_context, "MyE",Model.Adv_Settings_Server_Port, GlobalModels.server_port);
		GlobalModels.server_username = DataStack.getString(_context, "MyE",Model.Adv_Settings_Server_UserName, GlobalModels.server_username);
		GlobalModels.server_password = DataStack.getString(_context, "MyE",Model.Adv_Settings_Server_PWD, GlobalModels.server_password);
		GlobalModels.handler = null;
		server_chk_url = GlobalModels.server_address+":"+GlobalModels.server_port+"/download/";
		if(server_chk_url.indexOf("http://")==-1){
			server_chk_url = "http://"+server_chk_url;
		}
		new AsyncTask<Void, Void, Integer>() {
			/**  
		     * 该方法并不运行在UI线程当中，主要用于异步操作，所有在该方法中不能对UI当中的空间进行设置和修改  
		     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作  
		     */   
            @Override
            protected Integer doInBackground(Void... params) {
            	int result;
                long startTime = System.currentTimeMillis();
                result = loadingCache();
                long loadingTime = System.currentTimeMillis() - startTime;
                if (loadingTime < SHOW_TIME_MIN) {
                    try {
                        Thread.sleep(SHOW_TIME_MIN - loadingTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //publishProgress(params[0]);  
                return result;
            }
            
            //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置  
            @Override  
            protected void onPreExecute() {  
            }  
            
            /**  
             * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）  
             * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置  
             */
            @Override
            protected void onPostExecute(Integer result) {
            	if(result==1){
            		goToStart();
	                finish();
	                //两个参数分别表示进入的动画,退出的动画
	                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            	}else if(result==2){
            		doNewVersionUpdate();
            	}
            };
            
            /**  
             * 这里的Intege参数对应AsyncTask中的第二个参数  
             * 在doInBackground方法当中，每次调用publishProgress方法都会触发onProgressUpdate执行  
             * onProgressUpdate是在UI线程中执行，所有可以对UI空间进行操作  
             */  
			@Override
			protected void onProgressUpdate(Void... values) {
				super.onProgressUpdate(values);
			}
            
        }.execute(new Void[]{});
	}

	
	private int loadingCache() {
		if (getServerVerCode()) {
			int vercode = Config.getVerCode(this);
			if (newVerCode > vercode) {
				return 2;
			}
		}
        return SUCCESS;
    }
	
	

	private boolean getServerVerCode() {
		try {
			String verjson = NetworkTool.getContent(server_chk_url + Config.UPDATE_VERJSON);
			JSONArray array = new JSONArray(verjson);
			if (array.length() > 0) {
				JSONObject obj = array.getJSONObject(0);
				try {
					newVerCode = Integer.parseInt(obj.getString("verCode"));
					newVerName = obj.getString("verName");
				} catch (Exception e) {
					newVerCode = -1;
					newVerName = "";
					return false;
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return false;
		}
		return true;
	}

	private void doNewVersionUpdate() {
		int verCode = Config.getVerCode(this);
		String verName = Config.getVerName(this);
		StringBuffer sb = new StringBuffer();
		sb.append("A new version is available. Would you like to upgrade now?");
		Dialog dialog = new AlertDialog.Builder(SplashActivity.this)
				.setTitle("upgrade")
				.setMessage(sb.toString())
				// 设置内容
				.setPositiveButton("Yes",// 设置确定按钮
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								pBar = new ProgressDialog(SplashActivity.this);
								pBar.setTitle("Download");
								pBar.setMessage("please wait...");
								pBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
								downFile(server_chk_url+ Config.UPDATE_APKNAME);
							}

						})
				.setNegativeButton("Remind me later",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								goToStart();
								// 点击"取消"按钮之后退出程序
								finish();
							}
						}).create();// 创建
		// 显示对话框
		dialog.show();
	}

	void downFile(final String url) {
		pBar.show();
		new Thread() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);
					HttpEntity entity = response.getEntity();
					long length = entity.getContentLength();
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {
						String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
						File tmpFile = new File(path);
						if(!tmpFile.exists()){
							Log.i("not find path",path);
							pBar.cancel();
							handler.post(new Runnable() {
								public void run() {
									Dialog dialog2 = new AlertDialog.Builder(SplashActivity.this)
									.setTitle("No SD card")
									.setMessage("An SD card is required to download MyE.apk.")
									// 设置内容
									.setPositiveButton("OK",// 设置确定按钮
											new DialogInterface.OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog,int which) {
													goToStart();
													finish();
												}
										}).create();// 创建
									dialog2.show();
								}
							});
							
						}
						Log.i("path",path);
						File file = new File(path,Config.UPDATE_SAVENAME);
						fileOutputStream = new FileOutputStream(file);

						byte[] buf = new byte[1024];
						int ch = -1;
						int count = 0;
						while ((ch = is.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ch);
							count += ch;
							if (length > 0) {
							}
						}

					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}.start();

	}

	void down() {
		handler.post(new Runnable() {
			public void run() {
				pBar.cancel();
				update();
			}
		});

	}

	/**
	 * 下载后安装apk
	 */
	void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		String path = getDownloadPath();
		if(!path.equals("")){
			intent.setDataAndType(Uri.fromFile(new File(path, Config.UPDATE_SAVENAME)),	"application/vnd.android.package-archive");
			startActivity(intent);
		}
	}
	
	/**
	 * 获取当前系统下载地址路径，如果没有该路径返回为""空字符串
	 * @return
	 */
	String getDownloadPath(){
		String path = "";
		path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
		File tmpFile = new File(path);
		if(!tmpFile.exists()){
			path = "";
		}
		return path;
	}
	
	void goToStart() {		
		handler.post(new Runnable() {
			public void run() {
				try {
					int user_versionCode = DataStack.getInt(_context, "MyE","versionCode", 0);
					int versionCode = Config.getVerCode(_context);
					Log.i("user_versionCode", user_versionCode+"");
					Log.i("versionCode", versionCode+"");
					if (user_versionCode!=versionCode) {
						DataStack.updateIntData(_context, "MyE","versionCode", versionCode);
						Intent intent = new Intent(SplashActivity.this,GuideActivity.class);
						startActivity(intent);
					}else{
						Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
						startActivity(intent);
					}
				} catch (Exception e) {
					Log.e("goToStart", "Exception", e);
				}
			}
		});
	}
	
}
