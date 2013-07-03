package com.mye.gmobile.common.exception;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-2-21
 */
public class MessageException extends Exception {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    protected long messageNo;

    protected String messageStr;

    /**
     * constructor
     */
    public MessageException() {
        super();
    }

    /**
     * constructor
     */
    public MessageException(Throwable cause) {
        super(cause);
    }

    /**
     * constructor
     */
    public MessageException(long messageNo, String messageStr) {
        super();
        this.messageNo = messageNo;
        this.messageStr = messageStr;
    }

    /**
     * constructor
     */
    public MessageException(long messageNo, String messageStr, Throwable cause) {
        super(cause);
        this.messageNo = messageNo;
        this.messageStr = messageStr;
    }

    /**
     * get the messageNo.
     * 
     * @return the messageNo
     */
    public long getMessageNo() {
        return messageNo;
    }

    /**
     * get the messageStr.
     * 
     * @return the messageStr
     */
    public String getMessageStr() {
        return messageStr;
    }

}
