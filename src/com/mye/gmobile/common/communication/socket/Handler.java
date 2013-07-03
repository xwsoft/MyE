/*
 * Handler.java
 * @author:DongYu
 * History: 
 *   date              name      Description 
 *   2012-2-21        DongYu      create
 */

package com.mye.gmobile.common.communication.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Map;

import com.mye.gmobile.R;
import com.mye.gmobile.common.communication.BaseHandler;
import com.mye.gmobile.common.communication.ReqParamMap;
import com.mye.gmobile.common.communication.ResParamMap;
import com.mye.gmobile.common.exception.ConnectException;
import com.mye.gmobile.common.exception.ConnectTimeoutException;
import com.mye.gmobile.common.log.LogUtil;
import com.mye.gmobile.util.ReadConfigurationUtil;



/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-2-21
 */

public class Handler extends BaseHandler {

	/**
	 * constructor.
	 * 
	 * @param serverIp
	 *            server IP.
	 * @param serverPort
	 *            server port.
	 */
	public Handler(String serverIp, int serverPort,String server_username,String server_password) {
		super(serverIp, serverPort,server_username,server_password);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gmobile.common.communicaton.BaseHandler#sendMessage(com.gmobile.common
	 * .communicaton.ReqParamMap, java.lang.String)
	 */
	@Override
	public void sendMessage(ReqParamMap message, String URL)
			throws ConnectTimeoutException, ConnectException {
		Socket socket = null;
		try {
			int timeoutConnection = ReadConfigurationUtil.getIntValue(
					R.string.timeoutConnection, TIMEOUT_CONNECTION);

			socket = new Socket();
			SocketAddress socAddress = new InetSocketAddress(serverIp,
					serverPort);
			socket.connect(socAddress, timeoutConnection);

			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

			String requestStr = getRequestMessage(message);
			LogUtil.info("Handler", "Request Message: " + requestStr);

			writer.println(requestStr);
			writer.flush();

		} catch (java.net.SocketTimeoutException e) {
			LogUtil.error("Handler", "Socket request fail", e);
			throw new ConnectTimeoutException();
		} catch (Exception e) {
			LogUtil.error("Handler", "Socket request fail", e);
			throw new ConnectException();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.gmobile.common.communicaton.BaseHandler#sendAndReceiveMessage(com
	 * .gmobile.common.communicaton.ReqParamMap, java.lang.String)
	 */
	@Override
	public ResParamMap sendAndReceiveMessage(ReqParamMap message, String URL)
			throws ConnectTimeoutException, ConnectException {

		Socket socket = null;
		ResParamMap res = null;
		try {
			int timeoutConnection = ReadConfigurationUtil.getIntValue(
					R.string.timeoutConnection, TIMEOUT_CONNECTION);

			socket = new Socket();
			SocketAddress socAddress = new InetSocketAddress(serverIp,
					serverPort);
			socket.connect(socAddress, timeoutConnection);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

			String requestStr = getRequestMessage(message);
			LogUtil.info("Handler", "Request Message: " + requestStr);

			writer.println(requestStr);
			writer.flush();

			String responseStr = reader.readLine();
			LogUtil.info("Handler", "Receive Message: " + responseStr);

			res = new ResParamMap();
			res.put(ResParamMap.KEY_RECEIVE_MESSAGE, responseStr);

		} catch (java.net.SocketTimeoutException e) {
			LogUtil.error("Handler", "Socket request fail", e);
			throw new ConnectTimeoutException();
		} catch (Exception e) {
			LogUtil.error("Handler", "Socket request fail", e);
			throw new ConnectException();
		} finally {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				;
			}
		}
		return res;

	}

	private String getRequestMessage(ReqParamMap message) {
		StringBuffer actualUrl = new StringBuffer();
		for (Map.Entry<String, String> m : message.entrySet()) {
			actualUrl.append(m.getKey()).append("=").append(m.getValue())
					.append("&");
		}
		if (actualUrl.toString().endsWith("&")) {
			actualUrl.deleteCharAt(actualUrl.length() - 1);
		}
		return actualUrl.toString();
	}

}
