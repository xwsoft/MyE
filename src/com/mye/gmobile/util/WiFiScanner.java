package com.mye.gmobile.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.common.log.LogUtil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;

public class WiFiScanner {
	private Context _context;
	private Handler handler;
	private WifiManager wifi;
	private BroadcastReceiver receiver;
	private List<ScanResult> results = new ArrayList<ScanResult>();
	public List<String> wifiName = new ArrayList<String>();
	private Hashtable<String, ScanResult> displayResults = new Hashtable<String, ScanResult>();
	private boolean scanFlag = false;
	
	public WiFiScanner(Context context, Handler h){
		_context = context;
		handler = h;
		wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		
		//wifi.startScan();
		receiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				if(results != null){
					results.clear();
				}
				wifiName.clear();
				displayResults.clear();
				results = wifi.getScanResults();
				
				if(results != null){
					for(int i=0; i<results.size(); i++){
						wifiName.add(results.get(i).SSID.substring(0, results.get(i).SSID.length()));
					}
				}
				
				LogUtil.info("WiFiScanner", "scanning");
				for(int i=0;i<wifiName.size();i++){
					LogUtil.info("WiFiScanner name", wifiName.get(i));
				}
				
				scanFlag = false;
				Message msg = new Message();
				msg.what = CommMsgID.SCAN_COMPLETE;
				handler.sendMessage(msg);
			}
		};
		
		context.registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
	}
	
	public boolean startScan(Handler h){
		if(scanFlag){
			return false;
		}
		scanFlag = true;
		handler = h;
		/*wifi.setWifiEnabled(false);
		wifi.setWifiEnabled(true);*/
		if (wifi.isWifiEnabled() == false)
		{
			//save device wifi status
			DataStack.updateBooleanData(_context, "UserInfo", "WIFISTATE", false);
			//Enable WiFi
		    wifi.setWifiEnabled(true);
		}else{
			DataStack.updateBooleanData(_context, "UserInfo", "WIFISTATE", true);
		}
		wifi.startScan();
		LogUtil.info("WiFiScanner", "start scan");
		return true;
		
	}
	
	public List<String> getWiFiNames(){
		List<String> result = new ArrayList<String>();
		result.addAll(wifiName);
		return result;
	}
	
	public Hashtable<String, ScanResult> getScanResuls(){
		if(results != null){
			if(results.size()>0){
				for(int i=0; i<results.size(); i++){
					displayResults.put(wifiName.get(i), results.get(i));
				}
			}
		}
		return displayResults;
	}
	
	public boolean isMatched(String wifiInfo){
		if(results != null){
			for(int i=0; i<results.size(); i++){
				String info = results.get(i).SSID + results.get(i).BSSID + results.get(i).capabilities;
				if(wifiInfo.equals(info)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean getWiFiStatus(){
		return wifi.isWifiEnabled();
	}
	
	public void wifiControl(boolean en){
		//wifi.setWifiEnabled(false);
		//wifi.setWifiEnabled(true);
		wifi.setWifiEnabled(en);
	}
	
	public void unregister(){
		if(_context.isRestricted()){
			_context.unregisterReceiver(receiver);
		}
	}
}
