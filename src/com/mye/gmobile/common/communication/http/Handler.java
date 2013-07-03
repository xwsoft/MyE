package com.mye.gmobile.common.communication.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import com.mye.gmobile.R;
import com.mye.gmobile.common.communication.ReqParamMap;
import com.mye.gmobile.common.communication.ResParamMap;
import com.mye.gmobile.common.constant.ErrorCode;
import com.mye.gmobile.common.constant.HttpURL;
import com.mye.gmobile.common.exception.ConnectException;
import com.mye.gmobile.common.exception.ConnectTimeoutException;
import com.mye.gmobile.common.log.LogUtil;
import com.mye.gmobile.util.CheckUtil;
import com.mye.gmobile.util.ReadConfigurationUtil;

/**
 * HTTP connection.
 */
public class Handler extends com.mye.gmobile.common.communication.BaseHandler {

    private static final int MAX_RESPONSE_BYTE = 1024;

    /**
     * Set the default socket timeout (SO_TIMEOUT) in milliseconds which is the
     * timeout for waiting for data.
     */
    private static final int TIMEOUT_SOCKET = 12000;

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

    // ##################### Synchronous-HTTP(get) begin #################

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.gmobile.common.communicaton.Handler#sendMessage(com.gmobile.common
     * .communicaton.ReqParamMap, java.lang.String)
     */
    public void sendMessage(ReqParamMap message, String URL)
                    throws ConnectTimeoutException, ConnectException {
        String statusCode = "";
        try {
            int timeoutConnection = ReadConfigurationUtil.getIntValue(
                            R.string.timeoutConnection, TIMEOUT_CONNECTION);
            int timeoutSocket = ReadConfigurationUtil.getIntValue(
                            R.string.timeoutSocket, TIMEOUT_SOCKET);

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                            timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);

            String urlStr = getURLForHttpGetRequest(message, URL);
            LogUtil.info("Handler", "Request URL: " + urlStr);

            HttpGet httpGet = new HttpGet(urlStr);
            httpGet.addHeader("Cookie","JSESSIONID="+HttpURL.JSESSIONID);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            statusCode = httpResponse.getStatusLine().getStatusCode() + "";
            
            //获取Header中Cookie:JSESSION
            CookieStore mCookieStore = httpClient.getCookieStore();
            List<Cookie> cookies = mCookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                //这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
                if ("JSESSIONID".equals(cookies.get(i).getName())) {
                	if(cookies.get(i).getValue()!=null && !cookies.get(i).getValue().equals("")){
	                	HttpURL.JSESSIONID = cookies.get(i).getValue();
	                    break;
                	}
                }

            }
            
            LogUtil.info("Handler", "Http status code: " + statusCode);
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new Exception();
            }
 
        } catch (org.apache.http.conn.ConnectTimeoutException e) {
            LogUtil.error("Handler", "Http request fail", e);
            throw new ConnectTimeoutException();

        } catch (Exception e) {
            LogUtil.error("Handler", "Http request fail", e);
            throw new ConnectException(statusCode);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.gmobile.common.communicaton.Handler#sendAndReceiveMessage(com.gmobile
     * .common.communicaton.ReqParamMap, java.lang.String)
     */
    public ResParamMap sendAndReceiveMessage(ReqParamMap message, String URL)
                    throws ConnectTimeoutException, ConnectException {
        ResParamMap res = null;
        String statusCode = "";
        InputStream is = null;
        BufferedInputStream bis = null;
        try {
            int timeoutConnection = ReadConfigurationUtil.getIntValue(
                            R.string.timeoutConnection, TIMEOUT_CONNECTION);
            int timeoutSocket = ReadConfigurationUtil.getIntValue(
                            R.string.timeoutSocket, TIMEOUT_SOCKET);

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                            timeoutConnection);
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            String urlStr = getURLForHttpGetRequest(message, URL);
            LogUtil.info("Handler", "Request Message: " + urlStr);

            HttpGet httpGet = new HttpGet(urlStr);
            //添加jsession,保持与WEB session链接
            httpGet.addHeader("Cookie","JSESSIONID="+HttpURL.JSESSIONID);
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(server_username,server_password);
            httpGet.addHeader(new BasicScheme().authenticate(creds, httpGet));
            HttpResponse httpResponse = httpClient.execute(httpGet);

            LogUtil.info("Handler", "Http status code: "
                            + httpResponse.getStatusLine().getStatusCode());
            statusCode = httpResponse.getStatusLine().getStatusCode() + "";
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            bis = new BufferedInputStream(is);
            
            //获取Header中Cookie:JSESSION
            CookieStore mCookieStore = httpClient.getCookieStore();
            List<Cookie> cookies = mCookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                //这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
                if ("JSESSIONID".equals(cookies.get(i).getName())) {
                	if(cookies.get(i).getValue()!=null && !cookies.get(i).getValue().equals("")){
	                	HttpURL.JSESSIONID = cookies.get(i).getValue();
	                    break;
                	}
                }

            }

            ByteArrayBuffer bab = new ByteArrayBuffer(MAX_RESPONSE_BYTE);

            int current = 0;
            while ((current = bis.read()) != -1) {
                bab.append((byte) current);
            }
            String receiveMessage = EncodingUtils.getString(bab.toByteArray(),
                            HTTP.UTF_8);
            res = new ResParamMap();
            res.put(ResParamMap.KEY_RECEIVE_MESSAGE, receiveMessage);

            LogUtil.info("Handler", "Receive Message: " + receiveMessage);

            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                throw new Exception();
            }

        } catch (org.apache.http.conn.ConnectTimeoutException e) {
            LogUtil.error("Handler", "Http request fail", e);
            throw new ConnectTimeoutException();

        } catch (java.net.SocketTimeoutException e) {
            LogUtil.error("Handler", "Http request fail", e);
            throw new ConnectTimeoutException();

        } catch (org.apache.http.auth.AuthenticationException e) {
            LogUtil.error("Handler", "Http request fail", e);
            throw new ConnectException(
                            ErrorCode.COMMUNICATION_AUTHENTICATION_ERROR_NO,
                            ErrorCode.COMMUNICATION_AUTHENTICATION_ERROR_MESSAGE);
        } catch (org.apache.http.client.ClientProtocolException e) {
            LogUtil.error("Handler", "Http request fail", e);
            throw new ConnectException(
                            ErrorCode.COMMUNICATION_CLIENTPROTOCOL_ERROR_NO,
                            ErrorCode.COMMUNICATION_CLIENTPROTOCOL_ERROR_MESSAGE);
        } catch (IOException e) {
            if (CheckUtil.isNetworkAvailable()) {
                LogUtil.error("Handler", "Http request fail", e);
                throw new ConnectException(ErrorCode.COMMUNICATION_IO_ERROR_NO,
                                ErrorCode.COMMUNICATION_IO_ERROR_MESSAGE);
            } else {
                LogUtil.error("Handler", "Http request fail", e);
                throw new ConnectException(
                                ErrorCode.COMMUNICATION_NETWORK_ERROR_NO,
                                ErrorCode.COMMUNICATION_NETWORK_ERROR_MESSAGE);
            }

        } catch (Exception e) {
            LogUtil.error("Handler", "Http request fail", e);
            throw new ConnectException("Http status code = " + statusCode);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }
        return res;
    }

    // ##################### Synchronous-HTTP(get) end #################

    private String getURLForHttpGetRequest(ReqParamMap message, String URL) {
        StringBuffer actualUrl = new StringBuffer();
        actualUrl.append("http://" + serverIp + ":" + serverPort + URL);
        for (Map.Entry<String, String> m : message.entrySet()) {
            actualUrl.append(URLEncoder.encode(m.getKey())).append("=")
                            .append(URLEncoder.encode(m.getValue()))
                            .append("&");
        }
        if (actualUrl.toString().endsWith("&")) {
            actualUrl.deleteCharAt(actualUrl.length() - 1);
        }
        return actualUrl.toString();
    }

    // ############################# do post begin ########################

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.gmobile.common.communicaton.Handler#sendMessage(com.gmobile.common
     * .communicaton.ReqParamMap)
     */
    // public void sendMessage(ReqParamMap message, String URL) {
    //
    // try {
    // String actualUrl = "http://" + serverIp + ":" + serverPort + URL;
    //
    // DefaultHttpClient httpClient = new DefaultHttpClient();
    //
    // HttpPost httpPost = new HttpPost(actualUrl);
    //
    // List<NameValuePair> postData = new ArrayList<NameValuePair>();
    //
    // for (Map.Entry<String, String> m : message.entrySet()) {
    //
    // NameValuePair temp = new BasicNameValuePair(m.getKey(),
    // m.getValue());
    // postData.add(temp);
    //
    // }
    //
    // UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData,
    // HTTP.UTF_8);
    //
    // httpPost.setEntity(entity);
    //
    // httpClient.execute(httpPost);
    //
    // } catch (Exception e) {
    //
    // LogUtil.error("Handler", "Http request fail", e);
    //
    // }
    //
    // }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.gmobile.common.communicaton.Handler#sendAndReceiveMessage(com.gmobile
     * .common.communicaton.ReqParamMap)
     */
    // public ResParamMap sendAndReceiveMessage(ReqParamMap message, String URL)
    // {
    //
    // try {
    // String actualUrl = "http://" + serverIp + ":" + serverPort + URL;
    //
    // DefaultHttpClient httpClient = new DefaultHttpClient();
    //
    // HttpPost httpPost = new HttpPost(actualUrl);
    //
    // List<NameValuePair> postData = new ArrayList<NameValuePair>();
    //
    // for (Map.Entry<String, String> m : message.entrySet()) {
    //
    // NameValuePair temp = new BasicNameValuePair(m.getKey(),
    // m.getValue());
    // postData.add(temp);
    //
    // }
    //
    // UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postData,
    // HTTP.UTF_8);
    //
    // httpPost.setEntity(entity);
    //
    // HttpResponse response = httpClient.execute(httpPost);
    //
    // // HttpURLConnection.HTTP_OK
    //
    // HttpEntity httpEntity = response.getEntity();
    //
    // InputStream is = httpEntity.getContent();
    //
    // String receiveMessage = convertStreamToString(is);
    //
    // LogUtil.info("Handler", "http status code: "
    // + response.getStatusLine().getStatusCode() + " "
    // + receiveMessage);
    //
    // ResParamMap result = new ResParamMap();
    //
    // result.put(ResParamMap.KEY_RECEIVE_MESSAGE, receiveMessage);
    //
    // return result;
    //
    // } catch (Exception e) {
    //
    // LogUtil.error("Handler", "Http request fail", e);
    //
    // }
    //
    // return null;
    // }

    /**
     * read HTTP response message.
     * 
     * @param is
     * @return
     * @author:DongYu 2012-2-2
     */
    // private String convertStreamToString(InputStream is) {
    // BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    // StringBuilder sb = new StringBuilder();
    // String line = null;
    // try {
    // while ((line = reader.readLine()) != null) {
    // sb.append(line + " ");
    // }
    // } catch (IOException e) {
    // LogUtil.error("Handler", "Read http receive message error", e);
    // } finally {
    // try {
    // is.close();
    // } catch (IOException e) {
    // LogUtil.error("Handler",
    // "Close http receive message InputStream error",
    // e);
    // }
    // }
    // return sb.toString();
    // }

    // ############################# do post end ##########################

}
