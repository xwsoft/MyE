/*
 * BaseHandler.java
 * @author:DongYu
 * History: 
 *   date              name      Description 
 *   2012-1-30        DongYu      create
 */

package com.mye.gmobile.common.communication;

import java.util.LinkedList;

import android.os.Bundle;
import android.os.Message;

import com.mye.gmobile.common.constant.CommMsgID;
import com.mye.gmobile.common.constant.ErrorCode;
import com.mye.gmobile.common.exception.ConnectException;
import com.mye.gmobile.common.exception.ConnectTimeoutException;

/**
 * Send message.
 * 
 * @author DongYu
 * @version 1.0 2012-1-30
 */

public abstract class BaseHandler {

	/**
	 * Set the timeout in milliseconds until a connection is established.
	 */
	protected static final int TIMEOUT_CONNECTION = 10000;

	protected String serverIp;

	protected int serverPort;
	
	protected String server_username;
	
	protected String server_password;

	private static Object synObjectForRequestDataList = new Object();

	private static LinkedList<AsynchronousRequest> requestDataList = new LinkedList<AsynchronousRequest>();

	private static boolean isAsynchronousRequestThraadRunning = false;

	private boolean stop = false;

	/**
	 * send message synchronous.
	 * 
	 * @param message
	 *            request message
	 * @param URL
	 *            request URL.
	 * @throws ConnectTimeoutException
	 *             ConnectException
	 * @author:DongYu 2012-2-2
	 */
	public abstract void sendMessage(ReqParamMap message, String URL)
			throws ConnectTimeoutException, ConnectException;

	/**
	 * send message synchronous.
	 * 
	 * @param message
	 *            request message
	 * @param URL
	 *            request URL.
	 * @return response message
	 * @throws ConnectTimeoutException
	 *             ConnectException
	 * @author:DongYu 2012-2-2
	 */
	public abstract ResParamMap sendAndReceiveMessage(ReqParamMap message,
			String URL) throws ConnectTimeoutException, ConnectException;

	/**
	 * constructor.
	 */
	public BaseHandler() {

	}

	/**
	 * constructor.
	 * 
	 * @param serverIp
	 *            server IP.
	 * @param serverPort
	 *            server port.
	 */
	public BaseHandler(String serverIp, int serverPort,String server_username,String server_password) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.server_username = server_username;
		this.server_password = server_password;
	}

	/**
	 * get the serverIp.
	 * 
	 * @return the serverIp
	 */
	public String getServerIp() {
		return serverIp;
	}

	/**
	 * set the serverIp.
	 * 
	 * @param serverIp
	 *            the serverIp to set
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	/**
	 * get the serverPort.
	 * 
	 * @return the serverPort
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * set the serverPort.
	 * 
	 * @param serverPort
	 *            the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	// ############################# Asynchronous begin #################

	/**
	 * send message synchronous.
	 * 
	 * @param message
	 *            request message
	 * @param URL
	 *            request URL.
	 * @return response message
	 * @throws ConnectTimeoutException
	 *             ConnectException
	 * @author:DongYu 2012-2-2
	 */
	public void sendMessageAsynchronous(ReqParamMap message, String URL,
			android.os.Handler handler, int messageID) {
		AsynchronousRequest requestData = new AsynchronousRequest();
		requestData.setHandler(handler);
		requestData.setMessageID(messageID);
		requestData.setMessage(message);
		requestData.setRequestURL(URL);
		requestData.setHasResponse(false);
		addNewRequestDataIntoList(requestData);
		asynchronousRequest();
	}

	/**
	 * send message synchronous.
	 * 
	 * @param message
	 *            request message
	 * @param URL
	 *            request URL.
	 * @return response message
	 * @throws ConnectTimeoutException
	 *             ConnectException
	 * @author:DongYu 2012-2-2
	 */
	public void sendAndReceiveMessageAsynchronous(ReqParamMap message,
			String URL, android.os.Handler handler, int messageID) {
		AsynchronousRequest requestData = new AsynchronousRequest();
		requestData.setHandler(handler);
		requestData.setMessageID(messageID);
		requestData.setMessage(message);
		requestData.setRequestURL(URL);
		requestData.setHasResponse(true);
		addNewRequestDataIntoList(requestData);
		asynchronousRequest();
	}

	// ############################# Asynchronous end #################

	/**
	 * add a new AsynchronousRequest into list.
	 * 
	 * @param requestData
	 * @author:DongYu 2012-2-22
	 */
	protected void addNewRequestDataIntoList(AsynchronousRequest requestData) {
		synchronized (synObjectForRequestDataList) {
			if (requestDataList != null && requestData != null) {
				requestDataList.addLast(requestData);
			}
		}
	}

	/**
	 * get requestDataList size.
	 * 
	 * @author:DongYu 2012-2-22
	 */
	protected int getRequestDataListSize() {
		synchronized (synObjectForRequestDataList) {
			if (requestDataList != null) {
				return requestDataList.size();
			}
		}
		return 0;
	}

	/**
	 * get first request data.
	 * 
	 * @author:DongYu 2012-2-22
	 */
	protected AsynchronousRequest getFirstRequestData() {
		synchronized (synObjectForRequestDataList) {
			if (requestDataList != null && requestDataList.size() > 0) {
				return requestDataList.getFirst();
			}
		}
		return null;
	}

	/**
	 * remove first request data.
	 * 
	 * @author:DongYu 2012-2-22
	 */
	protected void removeFirstRequestData() {
		synchronized (synObjectForRequestDataList) {
			if (requestDataList != null && requestDataList.size() > 0) {
				requestDataList.removeFirst();
			}
		}
	}

	protected void clearRequestData() {
		synchronized (synObjectForRequestDataList) {
			if (requestDataList != null) {
				requestDataList.clear();
			}
		}
	}

	/**
	 * 
	 * @author:DongYu 2012-2-22
	 */
	private void asynchronousRequest() {
		if (!isAsynchronousRequestThraadRunning) {
			isAsynchronousRequestThraadRunning = true;
			new Thread() {
				public void run() {
					sendRequestDataList();
					isAsynchronousRequestThraadRunning = false;
				}
			}.start();
		}
	}

	private void sendRequestDataList() {
		while (true) {
			if (stop) {
				break;
			}
			int size = getRequestDataListSize();
			if (size > 0) {
				AsynchronousRequest request = getFirstRequestData();
				if (request != null) {
					try {
						if (request.isHasResponse()) {
							ResParamMap response = sendAndReceiveMessage(
									request.getMessage(),
									request.getRequestURL());
							Message msg = new Message();
							msg.what = request.getMessageID();
							Bundle bundle = new Bundle();
							bundle.putString(
									ResParamMap.KEY_RECEIVE_MESSAGE,
									(String) response
											.get(ResParamMap.KEY_RECEIVE_MESSAGE));
							msg.setData(bundle);
							request.getHandler().sendMessage(msg);
						} else {
							sendMessage(request.getMessage(),
									request.getRequestURL());
						}
					} catch (ConnectTimeoutException e) {
						Message msg = new Message();
						msg.what = CommMsgID.COMMON_ERROR;
						Bundle bundle = new Bundle();
						bundle.putLong(ResParamMap.KEY_ERROR,
								ErrorCode.COMMUNICATION_TIMEOUT_NO);
						bundle.putString(ResParamMap.KEY_ERROR_MESSAGE,
								e.getMessageStr());
						msg.setData(bundle);
						request.getHandler().sendMessage(msg);
					} catch (ConnectException e) {
						Message msg = new Message();
						msg.what = CommMsgID.COMMON_ERROR;
						Bundle bundle = new Bundle();
						bundle.putLong(ResParamMap.KEY_ERROR, e.getMessageNo());
						bundle.putString(ResParamMap.KEY_ERROR_MESSAGE,
								e.getMessageStr());
						msg.setData(bundle);
						request.getHandler().sendMessage(msg);
					}
				}
				removeFirstRequestData();
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {

				}
			}

		}
	}

	public void stop() {
		stop = true;
	}

}
