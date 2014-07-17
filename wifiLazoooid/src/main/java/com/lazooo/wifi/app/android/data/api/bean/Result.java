package com.lazooo.wifi.app.android.data.api.bean;/**
 * Lazooo copyright 2012
 */

import java.util.List;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 16/07/14
 * Time: 00:48
 */
public class Result<K> {

    private List<K> result;
    private Integer code;
    private Error error;

    public List<K> getResult() {
        return result;
    }

    public void setResult(List<K> result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
