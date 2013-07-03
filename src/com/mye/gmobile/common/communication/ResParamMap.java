package com.mye.gmobile.common.communication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * .
 * 
 * @author DongYu
 * @version 1.0 2012-1-30
 */
public class ResParamMap implements Map<String, Object> {

    public static final String KEY_RECEIVE_MESSAGE = "ReceiveMessage";

    public static final String KEY_ERROR = "Error";

    public static final String KEY_ERROR_MESSAGE = "ErrorMessage";

    /**
     * request map.
     */
    private Map<String, Object> paramMap = new HashMap<String, Object>();

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#clear()
     */
    public void clear() {
        throw new IllegalStateException(
                        "Don't support the method 'clear()' in ResParamMap.java");
    }

    /**
     * clear parameter.
     * 
     * @author dongyu 2010-10-18
     */
    public void clearParameter() {
        Set<String> keySet = paramMap.keySet();
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(keySet);
        for (String key : keyList) {
            if (key == null) {
                throw new IllegalStateException(
                                "key is null in ResParamMap.clearParameter()");
            } else {
                paramMap.remove(key);
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#containsKey(java.lang.Object)
     */
    public boolean containsKey(Object key) {
        return paramMap.containsKey(key);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#containsValue(java.lang.Object)
     */
    public boolean containsValue(Object value) {
        return paramMap.containsValue(value);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#entrySet()
     */
    public Set<java.util.Map.Entry<String, Object>> entrySet() {
        return paramMap.entrySet();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#get(java.lang.Object)
     */
    public Object get(Object key) {
        return paramMap.get(key);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#isEmpty()
     */
    public boolean isEmpty() {
        return paramMap.isEmpty();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#keySet()
     */
    public Set<String> keySet() {
        return paramMap.keySet();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#put(java.lang.Object, java.lang.Object)
     */
    public Object put(String key, Object value) {
        return paramMap.put(key, value);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#putAll(java.util.Map)
     */
    public void putAll(Map<? extends String, ? extends Object> m) {
        throw new IllegalStateException(
                        "Don't support the method 'putAll()' in ResParamMap.java");

    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#remove(java.lang.Object)
     */
    public String remove(Object key) {
        throw new IllegalStateException(
                        "Don't support the method 'remove()' in ResParamMap.java");
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#size()
     */
    public int size() {
        return paramMap.size();
    }

    /**
     * {@inheritDoc}
     **/
    public Collection<Object> values() {
        throw new IllegalStateException(
                        "Don't support the method 'values()' in ResParamMap.java");
    }

}
