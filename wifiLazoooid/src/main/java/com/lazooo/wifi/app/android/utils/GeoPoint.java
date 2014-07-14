package com.lazooo.wifi.app.android.utils;/**
 * Lazooo copyright 2012
 */

import com.google.gson.Gson;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 18:24
 */
public class GeoPoint {

    private Float latitude;
    private Float longitude;
    private Integer accurancy;

    public GeoPoint(Float latitude, Float longitude, Integer accurancy){

        this.latitude = latitude;
        this.longitude = longitude;
        this.accurancy = accurancy;
    }

    public GeoPoint(String json){

        GeoPoint g = new Gson().fromJson(json, GeoPoint.class);
        this.latitude = g.latitude;
        this.longitude = g.longitude;
        this.accurancy = g.accurancy;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getAccurancy() {
        return accurancy;
    }

    public void setAccurancy(Integer accurancy) {
        this.accurancy = accurancy;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
