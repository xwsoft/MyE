package com.mye.gmobile.model;

import java.util.List;

import android.util.Log;

import com.mye.gmobile.common.constant.Model;

public class House implements Cloneable {

    /**
     * house id.
     */
    private String houseId = null;

    /**
     * house name.
     */
    private String houseName = null;

    /**
     * thermostat status
     */
    private int thermostatStatus = Model.HOUSE_THERMOSTAT_STATUS_TYPE_NONE;

    /**
     * remote controllable status.
     */
    private int remote = Model.HOUSE_REMOTE_UNCONTROLLABLE;
    
    private String m_id = null;
    
    /**
     * M链接：0表示连接正常，1表示断开连接
     */
    private int connection = 1;
    
    private String defualtThermostat = "";
    
    /**
     * 当前房子是否可以被控制（当该房子下T有一个链接正常则可以被控制）
     */
    private boolean isContorl = false;
    
    private List<Thermostat> thermostatList = null;

    public List<Thermostat> getThermostatList() {
		return thermostatList;
	}

	public boolean isContorl() {
		return isContorl;
	}

	public void setContorl(boolean isContorl) {
		this.isContorl = isContorl;
	}

	public void setThermostatList(List<Thermostat> thermostatList) {
		this.thermostatList = thermostatList;
	}


	public void setDefualtThermostat(String defualtThermostat) {
		this.defualtThermostat = defualtThermostat;
	}

	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public int getConnection() {
		return connection;
	}

	public void setConnection(int connection) {
		this.connection = connection;
	}

	/**
     * get the houseId.
     * 
     * @return the houseId
     */
    public String getHouseId() {
        return houseId;
    }

    /**
     * set the houseId.
     * 
     * @param houseId
     *            the houseId to set
     */
    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    /**
     * get the houseName.
     * 
     * @return the houseName
     */
    public String getHouseName() {
        return houseName;
    }

    /**
     * set the houseName.
     * 
     * @param houseName
     *            the houseName to set
     */
    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    /**
     * get the thermostatStatus.
     * 
     * @return the thermostatStatus
     */
    public int getThermostatStatus() {
        return thermostatStatus;
    }

    /**
     * set the thermostatStatus.
     * 
     * @param thermostatStatus
     *            the thermostatStatus to set
     */
    public void setThermostatStatus(int thermostatStatus) {
        this.thermostatStatus = thermostatStatus;
    }

    /**
     * get the remote.
     * 
     * @return the remote
     */
    public int getRemote() {
        return remote;
    }

    /**
     * set the remote.
     * 
     * @param remote
     *            the remote to set
     */
    public void setRemote(int remote) {
        this.remote = remote;
    }
    
  
    public String getDefualtThermostat() {
		return findDefualtThermostat(defualtThermostat);
	}

	public String findDefualtThermostat(String t_id){
    	String d_tid = "";
    	if(this.thermostatList!=null){
    		int t_len = this.thermostatList.size();
    		for(int i=0;i<t_len;i++){
    			if(this.thermostatList.get(i).getT_id().equals(t_id) && this.thermostatList.get(i).getThermostatStatus()==0){
    				d_tid = t_id;
    				this.defualtThermostat = d_tid;
    				return d_tid;
    			}
    		}
    		for(int i=0;i<t_len;i++){
    			if(this.thermostatList.get(i).getThermostatStatus()==0){
    				d_tid = this.thermostatList.get(i).getT_id();
    				this.defualtThermostat = d_tid;
    				return d_tid;
    			}
    		}
    	}
    	this.defualtThermostat = d_tid;
    	return d_tid;
    }

	public Thermostat findonLineThermostat(String t_id){
    	Thermostat t = null;
    	if(this.thermostatList!=null){
    		int t_len = this.thermostatList.size();
    		for(int i=0;i<t_len;i++){
    			if(t_id!=null && !t_id.equals("") && this.thermostatList.get(i).getT_id().equals(t_id) && this.thermostatList.get(i).getThermostatStatus()==0){
    				t = this.thermostatList.get(i);
    				return t;
    			}
    		}
    		for(int i=0;i<t_len;i++){
    			if(this.thermostatList.get(i).getThermostatStatus()==0){
    				t = this.thermostatList.get(i);
    				return t;
    			}
    		}
    	}
    	return t;
    }
	
	public Thermostat findThermostatById(String t_id){
    	Thermostat t = null;
    	if(this.thermostatList!=null){
    		int t_len = this.thermostatList.size();
    		for(int i=0;i<t_len;i++){
    			if(t_id!=null && !t_id.equals("") && this.thermostatList.get(i).getT_id().equals(t_id)){
    				t = this.thermostatList.get(i);
    				return t;
    			}
    		}
    	}
    	return t;
    }
	
	/**
	 * 当前T是否可以远程控制
	 * @return
	 */
	public boolean getCurrentThermostatRemote(){
		return getThermostatRemote(this.defualtThermostat);
	}
	
	/**
	 * 指定T是否可以远程控制
	 * @param currentThermostat  
	 * @return  true为可以远程控制
	 */
	public boolean getThermostatRemote(String currentThermostat){
		boolean _remote = false;
		if(currentThermostat!=null && !currentThermostat.equals("")){
			if(connection == Model.M_CONNECTION_TRUE){
				Thermostat t = findThermostatById(currentThermostat);
				if(t.getThermostatStatus()==Model.T_CONNECTION_TRUE && t.getRemote()==Model.T_REMOTE_TRUE){
					_remote = true;
				}
			}
		}
		return _remote;
	}
	
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        House o = null;
        try {
            o = (House) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("House", "error in clone()", e);
        }
        return o;

    }

}
