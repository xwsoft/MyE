/*
 * ConnectTimeoutException.java
 * @author:DongYu
 * History: 
 *   date              name      Description 
 *   2012-2-21        DongYu      create
 */

package com.mye.gmobile.common.exception;

import com.mye.gmobile.common.constant.ErrorCode;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-2-21
 */

public class ConnectTimeoutException extends MessageException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * constructor
     */
    public ConnectTimeoutException() {
        super();
        this.messageNo = ErrorCode.COMMUNICATION_TIMEOUT_NO;
        this.messageStr = ErrorCode.COMMUNICATION_TIMEOUT_MESSAGE; // "Can not connect server."
    }

    /**
     * constructor
     */
    public ConnectTimeoutException(String messageStr) {
        super();
        this.messageNo = ErrorCode.COMMUNICATION_TIMEOUT_NO;
        this.messageStr = ErrorCode.COMMUNICATION_TIMEOUT_MESSAGE + ": " + messageStr;
    }

    /**
     * constructor
     */
    public ConnectTimeoutException(Throwable cause) {
        super(cause);
        this.messageNo = ErrorCode.COMMUNICATION_TIMEOUT_NO;
        this.messageStr = ErrorCode.COMMUNICATION_TIMEOUT_MESSAGE;
    }

    /**
     * constructor
     */
    public ConnectTimeoutException(String messageStr, Throwable cause) {
        super(cause);
        this.messageNo = ErrorCode.COMMUNICATION_TIMEOUT_NO;
        this.messageStr = ErrorCode.COMMUNICATION_TIMEOUT_MESSAGE + ": " + messageStr;
    }

    /**
     * constructor
     */
    public ConnectTimeoutException(long messageNo, String messageStr) {
        super(messageNo, messageStr);
    }

    /**
     * constructor
     */
    public ConnectTimeoutException(long messageNo, String messageStr,
                    Throwable cause) {
        super(messageNo, messageStr, cause);
    }

}
