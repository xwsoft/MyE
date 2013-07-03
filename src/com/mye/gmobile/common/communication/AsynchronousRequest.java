/*
 * AsynchronousRequest.java
 * @author:DongYu
 * History: 
 *   date              name      Description 
 *   2012-2-22        DongYu      create
 */

package com.mye.gmobile.common.communication;

import java.io.Serializable;

import android.os.Handler;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-2-22
 */

public class AsynchronousRequest implements Serializable {

    /**
     * AsynchronousRequest
     */
    private static final long serialVersionUID = 1L;

    /**
     * Handler.
     */
    private Handler handler = null;

    /**
     * string request message
     */
    private ReqParamMap message = null;

    /**
     * message id for call back.
     */
    private int messageID = 0;

    /**
     * request URL.
     */
    private String requestURL = null;

    /**
     * has response.
     */
    private boolean hasResponse = false;

    /**
     * get the handler.
     * 
     * @return the handler
     */
    public Handler getHandler() {
        return handler;
    }

    /**
     * set the handler.
     * 
     * @param handler
     *            the handler to set
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * get the message.
     * 
     * @return the message
     */
    public ReqParamMap getMessage() {
        return message;
    }

    /**
     * set the message.
     * 
     * @param message
     *            the message to set
     */
    public void setMessage(ReqParamMap message) {
        this.message = message;
    }

    /**
     * get the messageID.
     * 
     * @return the messageID
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * set the messageID.
     * 
     * @param messageID
     *            the messageID to set
     */
    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    /**
     * get the requestURL.
     * 
     * @return the requestURL
     */
    public String getRequestURL() {
        return requestURL;
    }

    /**
     * set the requestURL.
     * 
     * @param requestURL
     *            the requestURL to set
     */
    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    /**
     * get the hasResponse.
     * 
     * @return the hasResponse
     */
    public boolean isHasResponse() {
        return hasResponse;
    }

    /**
     * set the hasResponse.
     * 
     * @param hasResponse
     *            the hasResponse to set
     */
    public void setHasResponse(boolean hasResponse) {
        this.hasResponse = hasResponse;
    }

}
