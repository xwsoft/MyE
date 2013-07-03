package com.mye.gmobile.model.base;

/**
 * IJson.
 * 
 * @author DongYu
 * @version 1.0 2012-2-2
 */
public interface IJson {

    /**
     * transfer from JSON.
     * 
     * @param json
     * @author:DongYu 2012-2-2
     */
    public abstract void transferFormJson(String json);

    /**
     * transfer to JSON.
     * 
     * @return
     * @author:DongYu 2012-2-2
     */
    public abstract String transferToJson();

}
