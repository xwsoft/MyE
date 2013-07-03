package com.mye.gmobile.common.constant;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-2-21
 */
public class ErrorCode {

    /**
     * communication timeout.
     */
    public final static long COMMUNICATION_TIMEOUT_NO = 1;
    public final static String COMMUNICATION_TIMEOUT_MESSAGE = "Failed to connect to the server. Please try again.";

    /**
     * communication other exception.
     */
    public final static long COMMUNICATION_COMMON_ERROR_NO = 2;
    public final static String COMMUNICATION_COMMON_ERROR_MESSAGE = "Failed to connect to the server. Please try again.";

    /**
     * communication Authentication exception.
     */
    public final static long COMMUNICATION_AUTHENTICATION_ERROR_NO = 3;
    public final static String COMMUNICATION_AUTHENTICATION_ERROR_MESSAGE = "Authentication error. Please check the username and password.";

    /**
     * communication Client Protocol exception.
     */
    public final static long COMMUNICATION_CLIENTPROTOCOL_ERROR_NO = 4;
    public final static String COMMUNICATION_CLIENTPROTOCOL_ERROR_MESSAGE = "Failed to connect to the server. Please try again.";

    /**
     * communication IO exception.
     */
    public final static long COMMUNICATION_IO_ERROR_NO = 5;
    public final static String COMMUNICATION_IO_ERROR_MESSAGE = "Failed to connect to the server. Please try again.";

    /**
     * communication IO exception.
     */
    public final static long COMMUNICATION_NETWORK_ERROR_NO = 6;
    public final static String COMMUNICATION_NETWORK_ERROR_MESSAGE = "Failed to connect to the server. Please try again.";
    
    public final static String CONNECTION_TIMEOUT = "Connection has timed out.";

}
