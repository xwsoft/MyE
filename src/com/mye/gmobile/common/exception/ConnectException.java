/*
 * ConnectException.java
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

public class ConnectException extends MessageException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * constructor
     */
    public ConnectException() {
        super();
        this.messageNo = ErrorCode.COMMUNICATION_COMMON_ERROR_NO;
        this.messageStr = ErrorCode.COMMUNICATION_COMMON_ERROR_MESSAGE; // "Can not connect server."
    }

    /**
     * constructor
     */
    public ConnectException(String messageStr) {
        super();
        this.messageNo = ErrorCode.COMMUNICATION_COMMON_ERROR_NO;
        this.messageStr = ErrorCode.COMMUNICATION_COMMON_ERROR_MESSAGE + ": " + messageStr;
    }

    /**
     * constructor
     */
    public ConnectException(Throwable cause) {
        super(cause);
        this.messageNo = ErrorCode.COMMUNICATION_COMMON_ERROR_NO;
        this.messageStr = ErrorCode.COMMUNICATION_COMMON_ERROR_MESSAGE;
    }

    /**
     * constructor
     */
    public ConnectException(String messageStr, Throwable cause) {
        super(cause);
        this.messageNo = ErrorCode.COMMUNICATION_COMMON_ERROR_NO;
        this.messageStr = ErrorCode.COMMUNICATION_COMMON_ERROR_MESSAGE + ": " + messageStr;
    }

    /**
     * constructor
     */
    public ConnectException(long messageNo, String messageStr) {
        super(messageNo, messageStr);
    }

    /**
     * constructor
     */
    public ConnectException(long messageNo, String messageStr, Throwable cause) {
        super(messageNo, messageStr, cause);
    }

}
