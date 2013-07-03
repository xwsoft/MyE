package com.mye.gmobile.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mye.gmobile.common.constant.Model;
import com.mye.gmobile.common.log.LogUtil;
import com.mye.gmobile.model.base.BaseModel;

/**
 * vacation list.
 * 
 * @author DongYu
 * @version 1.0 2012-1-23
 */
public class VacationList extends BaseModel {

    /**
     * vacation list.
     */
    private List<Vacation> vacationList = new ArrayList<Vacation>();

    /**
     * get the vacationList.
     * 
     * @return the vacationList
     */
    public List<Vacation> getVacationList() {
        return vacationList;
    }

    /**
     * set the vacationList.
     * 
     * @param vacationList
     *            the vacationList to set
     */
    public void setVacationList(List<Vacation> vacationList) {
        this.vacationList = vacationList;
    }

    /**
     * get Vacation by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public Vacation getVacationsByIndex(int index) {
        Vacation vaction = null;
        if (this.vacationList != null && index < this.vacationList.size()) {
            vaction = this.vacationList.get(index);
        }
        return vaction;
    }

    /**
     * remove Vacation by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public Vacation removeVacationAt(int index) {
        Vacation vaction = null;
        if (this.vacationList != null && index < this.vacationList.size()) {
            vaction = this.vacationList.get(index);
            vaction.setAction(Model.VACATION_ACTION_DELETE_ALL);
            vacationList.remove(vaction);
        }
        return vaction;
    }

    /**
     * add Vacation by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public void addVacationAt(int index, Vacation vaction) {
        if (this.vacationList != null && index <= this.vacationList.size()) {
            this.vacationList.add(index, vaction);
            vaction.setAction(Model.VACATION_ACTION_ADD);
        }
    }

    /**
     * add Vacation by index.
     * 
     * @return
     * @author:DongYu 2012-2-6
     */
    public void addVacation(Vacation vaction) {
        if (this.vacationList != null) {
            this.vacationList.add(vaction);
            vaction.setAction(Model.VACATION_ACTION_ADD);
        }
    }

    /**
     * update Vacation by index.
     * 
     * @param index
     * @return
     * @author:DongYu 2012-2-6
     */
    public Vacation updateVacationAt(int index, Vacation vacation) {
        Vacation vaction = null;
        if (this.vacationList != null && index < this.vacationList.size()) {
            vaction = this.vacationList.set(index, vacation);
            vaction.setAction(Model.VACATION_ACTION_ADD);
        }
        return vaction;

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
            this.houseId = jsonObj.getString("houseId");
            this.userId = jsonObj.getString("userId");
            JSONArray vacationArray = jsonObj.getJSONArray("vacations");
            this.vacationList.clear();
            if (vacationArray != null) {
                for (int i = 0; i < vacationArray.length(); i++) {
                    JSONObject temp = vacationArray.getJSONObject(i);
                    if (temp != null) {

                        Vacation vacation = new Vacation();
                        vacation.setName(temp.getString("name"));
                        vacation.setType((int) temp.getLong("type"));
                        vacation.setOldEndDate(temp.getString("old_end_date"));

                        int type = (int) temp.getLong("type");
                        vacation.setType(type);
                        if (Model.VACATION_TYPE_VACATION == type) {
                            vacation.setCooling((int) temp.getLong("cooling"));
                            vacation.setHeating((int) temp.getLong("heating"));
                            vacation.setReturnDate(temp.getString("returnDate"));
                            vacation.setReturnTime(temp.getString("returnTime"));
                            vacation.setLeaveDate(temp.getString("leaveDate"));
                            vacation.setLeaveTime(temp.getString("leaveTime"));

                        } else if (Model.VACATION_TYPE_STAYCATION == type) {
                            vacation.setNightCooling((int) temp
                                            .getLong("nightCooling"));
                            vacation.setNightHeating((int) temp
                                            .getLong("nightHeating"));
                            vacation.setDayCooling((int) temp
                                            .getLong("dayCooling"));
                            vacation.setDayHeating((int) temp
                                            .getLong("dayHeating"));
                            vacation.setStartDate(temp.getString("startDate"));
                            vacation.setEndDate(temp.getString("endDate"));
                            vacation.setDayTime(temp.getString("dayTime"));
                            vacation.setNightTime(temp.getString("nightTime"));
                        }
                        vacation.setOldEndDate(temp.getString("old_end_date"));
                        vacationList.add(vacation);

                    }
                }
            }

        } catch (JSONException e) {
            LogUtil.error("VacationList", "transferFormJson() error", e);
        }

    }

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
        VacationList o = null;
        o = (VacationList) super.clone();
        if (o != null && vacationList != null) {
            o.vacationList = new ArrayList<Vacation>();
            for (Vacation vacation : vacationList) {
                o.vacationList.add((Vacation) vacation.clone());
            }
        }
        return o;
    }

}
