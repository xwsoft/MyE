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
public class ReqParamMap implements Map<String, String> {

    /**
     * request map.
     */
    private Map<String, String> paramMap = new HashMap<String, String>();

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#clear()
     */
    public void clear() {
        throw new IllegalStateException(
                        "Don't support the method 'clear()' in ReqParamMap.java");
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
                                "key is null in ReqParamMap.clearParameter()");
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
    public Set<java.util.Map.Entry<String, String>> entrySet() {
        return paramMap.entrySet();
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#get(java.lang.Object)
     */
    public String get(Object key) {
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
    public String put(String key, String value) {
        return paramMap.put(key, value);
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#putAll(java.util.Map)
     */
    public void putAll(Map<? extends String, ? extends String> m) {
        throw new IllegalStateException(
                        "Don't support the method 'putAll()' in ReqParamMap.java");

    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.Map#remove(java.lang.Object)
     */
    public String remove(Object key) {
        throw new IllegalStateException(
                        "Don't support the method 'remove()' in ReqParamMap.java");
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
    public Collection<String> values() {
        throw new IllegalStateException(
                        "Don't support the method 'values()' in ReqParamMap.java");
    }

}
