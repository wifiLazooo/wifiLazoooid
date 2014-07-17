package com.lazooo.wifi.app.android.data.storage;/**
 * Lazooo copyright 2012
 */

import com.lazooo.wifi.app.android.utils.GeoPoint;
import com.lazooo.wifi.app.android.utils.GeoUtils;
import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 13/07/14
 * Time: 14:19
 */

/**
 *
 * Object used to save an instance of a Lazooo api return value.
 *
 * @param <D> name of the class that extends this
 */
public class LazoooRecord<D> extends SugarRecord<D> {

    private Date lastUpdate;
    private String lazoooId;
    private String geoLastUpdate;

    public LazoooRecord(){

        super();
    }

    /**
     *
     * @param now the current time when the api was called to create this instance.
     * @param currLocation the current location when the api was called to create this instance.
     */
    public LazoooRecord(Date now, GeoPoint currLocation, String lazoooId){

        super();
        this.lazoooId = lazoooId;
        this.lastUpdate = now;
        this.geoLastUpdate = GeoUtils.toString(currLocation);
    }

    public LazoooRecord(Date now, String currLocation, String lazoooId){

        super();
        this.lazoooId = lazoooId;
        this.lastUpdate = now;
        this.geoLastUpdate = currLocation;
    }

    public int getSecondsFromLastUpdate(){

        return (int)( (new Date().getTime() - lastUpdate.getTime()) / (1000) );
    }

    public long getMetersFromLastUpdate(GeoPoint currentGeoPoint){

        GeoPoint geoPoint = GeoUtils.fromString(geoLastUpdate);
        return (long) GeoUtils.distFrom(geoPoint.getLatitude(), geoPoint.getLongitude(),
                currentGeoPoint.getLatitude(), currentGeoPoint.getLongitude());
    }

    public void setLastUpdate(Date date){

        this.lastUpdate = date;
    }

    public String getLazoooId() {
        return lazoooId;
    }

    /**
     * Returns an instance of lazoooRecord retrieved by lazoooId field
     * @param l the class that extends LazoooRecord
     * @param lazoooId the value used to retrieve the record
     * @param <D> class that extends LazoooRecord
     * @return the instance found, if not found returns null
     * @throws StorageRuntimeException if there are at least two records saved with same lazoooId
     */
    public static <D extends LazoooRecord<?>> D lazoooFindById(Class<D> l, String lazoooId)
            throws StorageRuntimeException {

        List<D> r = find(l, "LAZOOO_ID = ?", lazoooId);
        if(r.isEmpty()){

            //TODO maybe raise an exception
            return null;
        }else {

            if(r.size() > 1){

                throw new StorageRuntimeException("Resource duplicated, same lazoooId");
            }
            return r.get(0);
        }
    }

    /**
     *
     * Call this method in place of save() to avoid creations of lazoooRecords with same lazoooId
     *
     * @return the current lazoooId
     */
    @SuppressWarnings("unchecked")
    public String lazoooSave(){

        LazoooRecord<D> r = lazoooFindById(getClass(), getLazoooId());
        if(r != null){

            delete();
        }
        save();
        return lazoooId;
    }
}
