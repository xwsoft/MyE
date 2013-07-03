/*
 * Login.java
 * @author:DongYu
 * History: 
 *   date              name      Description 
 *   2012-1-16        DongYu      create
 */

package com.mye.gmobile.model;

import android.util.Log;

import com.mye.gmobile.common.constant.Model;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-1-16
 */

public class Login implements Cloneable {

    /**
     * user name.
     */
    private String userName = null;

    /**
     * pass word.
     */
    private String password = null;

    /**
     * whether remember me.
     */
    private boolean rememberMe = false;

    /**
     * type: web 0; native app 1.
     */
    private int type = Model.LOGIN_TYPE_NATIVE_APP;

    public Login() {

    }

    /**
     * Constructor.
     * 
     * @param userName
     * @param password
     * @param rememberMe
     */
    public Login(String userName, String password, boolean rememberMe) {
        this.userName = userName;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    /**
     * get the userName.
     * 
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * set the userName.
     * 
     * @param userName
     *            the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * get the password.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * set the password.
     * 
     * @param password
     *            the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * get the rememberMe.
     * 
     * @return the rememberMe
     */
    public boolean isRememberMe() {
        return rememberMe;
    }

    /**
     * set the rememberMe.
     * 
     * @param rememberMe
     *            the rememberMe to set
     */
    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    /**
     * get the type.
     * 
     * @return the type
     */
    public int getType() {
        return type;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        Login o = null;
        try {
            o = (Login) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.e("Login", "error in clone()", e);
        }
        return o;

    }

}
