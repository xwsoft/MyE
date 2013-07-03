package com.mye.gmobile.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.mye.gmobile.common.log.LogUtil;
import com.mye.gmobile.model.base.IJson;

public class HouseList implements IJson, Cloneable {

    /**
     * user ID.
     */
    private String userId = null;

    /**
     * user name.
     */
    private String userName = null;

    /**
     * whether login successfully.
     */
    private boolean success = false;

    /**
     * house list.
     */
    private House[] houses = null;

    /**
     * get the userId.
     * 
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * set the userId.
     * 
     * @param userId
     *            the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * get the userName.
     * 
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * set the userName.
     * 
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * get the success.
     * 
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * set the success.
     * 
     * @param success
     *            the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getHouseName(String houseID){
    	for(House house : houses){
    		if(house.getHouseId().equalsIgnoreCase(houseID)){
    			return house.getHouseName();
    		}
    	}
    	return "";
    }
    
    public House getHouseById(String houseID){
    	for(House house : houses){
    		if(house.getHouseId().equalsIgnoreCase(houseID)){
    			return house;
    		}
    	}
    	return null;
    }
    
    /**
     * 取得当前选中的房子
     * @return House
     */
    public House getCurrentHouse(){
    	return GlobalModels.houseList.getHouseById(GlobalModels.getCurrHouseId());
    }
    /**
     * get the houses.
     * 
     * @return the houses
     */
    public House[] getHouses() {
        return houses;
    }

    /**
     * set the houses.
     * 
     * @param houses
     *            the houses to set
     */
    public void setHouses(House[] houses) {
        this.houses = houses;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gmobile.model.base.IJson#transferFormJson(java.lang.String)
     */
    public void transferFormJson(String json) {
        if (json == null) {
            return;
        }
        JSONObject jsonObj;
        try {
            jsonObj = new JSONObject(json);
            this.success = Boolean.parseBoolean(jsonObj.getString("success"));
            if (!this.success) {
                return;
            }
            if (jsonObj.has("userId") && !jsonObj.isNull("userId")) {
                this.userId = jsonObj.getString("userId");
            }
            this.userName = jsonObj.getString("userName");
            JSONArray houseJsons = jsonObj.getJSONArray("houses");
            if (houseJsons != null) {
                this.houses = new House[houseJsons.length()];
                for (int i = 0; i < houseJsons.length(); i++) {
                    JSONObject tempJson = houseJsons.getJSONObject(i);
                    if (tempJson != null) {
                        House house = new House();
                        house.setHouseId(tempJson.getString("houseId"));
                        house.setHouseName(tempJson.getString("houseName"));
                        house.setConnection(tempJson.getInt("connection"));
                        if (tempJson.has("mId") && !tempJson.isNull("mId")) {
                        	house.setM_id(tempJson.getString("mId"));
                        	if (tempJson.has("thermostats") && !tempJson.isNull("thermostats")) {
                        		JSONArray t_jsonArray = tempJson.getJSONArray("thermostats");
                        		if(t_jsonArray!=null){
                        			List<Thermostat> thermostatList = new ArrayList<Thermostat>();
                        			boolean flag = false;
                        			for(int j=0;j<t_jsonArray.length();j++){
                        				JSONObject t_obj = t_jsonArray.getJSONObject(j);
                        				Thermostat thermostat = new Thermostat();
                        				thermostat.setT_id(t_obj.getString("tId"));
                        				thermostat.setT_name(t_obj.getString("tName"));
                        				thermostat.setThermostatStatus(t_obj.getInt("thermostat"));
                        				thermostat.setRemote(t_obj.getInt("remote"));
                        				thermostat.setDeviceType(t_obj.getInt("deviceType"));
                        				thermostat.setKeyPad(t_obj.getInt("keypad"));
                        				thermostatList.add(thermostat);
                        				if(thermostat.getThermostatStatus()==0){
                        					flag = true;
                        				}
                        			}
                        			if(flag){
                        				house.setContorl(true);
                        			}
                        			house.setThermostatList(thermostatList);                        			
                        		}
                        	}
                        }
                        this.houses[i] = house;
                    }
                }
            }

        } catch (JSONException e) {
            LogUtil.error("HouseList", "transferFormJson() error", e);
        }
        deleteNoneHardwareHouse();

    }

    private void deleteNoneHardwareHouse() {
        List<House> houseList = new ArrayList<House>();
        if (this.houses != null && this.houses.length > 0) {
            for (int i = 0; i < this.houses.length; i++) {
            	//M必须为0表示M正常链接，T列表不能为空
                if (this.houses[i] != null && this.houses[i].getM_id()!=null && !this.houses[i].getM_id().equals("")) {
                	houseList.add(this.houses[i]);
                }
            }
            this.houses = new House[houseList.size()];
            for (int i = 0; i < houseList.size(); i++) {
                this.houses[i] = houseList.get(i);
            }
        }
    }

    public void transferFormJsonOnlyHouses(String json) {
        if (json == null) {
            return;
        }
        JSONArray houseJsons;
        try {
            houseJsons = new JSONArray(json);
            if (houseJsons != null) {
                this.houses = new House[houseJsons.length()];
                for (int i = 0; i < houseJsons.length(); i++) {
                    JSONObject tempJson = houseJsons.getJSONObject(i);
                    if (tempJson != null) {
                        House house = new House();
                        house.setHouseId(tempJson.getString("houseId"));
                        house.setHouseName(tempJson.getString("houseName"));
                        house.setConnection(tempJson.getInt("connection"));
                        if (tempJson.has("mId") && !tempJson.isNull("mId")) {
                        	house.setM_id(tempJson.getString("mId"));
                        	if (tempJson.has("thermostats") && !tempJson.isNull("thermostats")) {
                        		JSONArray t_jsonArray = tempJson.getJSONArray("thermostats");
                        		if(t_jsonArray!=null){
                        			List<Thermostat> thermostatList = new ArrayList<Thermostat>();
                        			boolean flag = false;
                        			for(int j=0;j<t_jsonArray.length();j++){
                        				JSONObject t_obj = t_jsonArray.getJSONObject(j);
                        				Thermostat thermostat = new Thermostat();
                        				thermostat.setT_id(t_obj.getString("tId"));
                        				thermostat.setT_name(t_obj.getString("tName"));
                        				thermostat.setThermostatStatus(t_obj.getInt("thermostat"));
                        				thermostat.setRemote(t_obj.getInt("remote"));
                        				thermostat.setDeviceType(t_obj.getInt("deviceType"));
                        				thermostat.setKeyPad(t_obj.getInt("keypad"));
                        				thermostatList.add(thermostat);
                        				if(thermostat.getThermostatStatus()==0){
                        					flag = true;
                        				}
                        			}
                        			if(flag){
                        				house.setContorl(true);
                        			}
                        			house.setThermostatList(thermostatList);                        			
                        		}
                        	}
                        }
                        this.houses[i] = house;
                    }
                }
            }

        } catch (JSONException e) {
            LogUtil.error("HouseList", "transferFormJsonOnlyHouses() error", e);
        }
        deleteNoneHardwareHouse();

    }
    
    /**
     * 取得当前选中的房子
     * @return House
     */
//    public House getCurrentHouse(){
//    	return GlobalModels.houseList.getHouseById(0);
//    }

    /*
     * (non-Javadoc)
     * 
     * @see com.gmobile.model.base.IJson#transferToJson()
     */
    public String transferToJson() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        HouseList o = null;
        try {
            o = (HouseList) super.clone();
            if (o != null && houses != null) {
                o.houses = new House[houses.length];
                for (int i = 0; i < houses.length; i++) {
                    o.houses[i] = (House) houses[i].clone();
                }
            }
        } catch (CloneNotSupportedException e) {
            Log.e("HouseList", "error in clone()", e);
        }
        return o;
    }

}
