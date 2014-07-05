package com.lazooo.wifi.app.android.components;/**
 * Lazooo copyright 2012
 */

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * @author giok57
 * @email gioelemeoni@gmail.com
 * @modifiedBy giok57
 * <p/>
 * Date: 04/07/14
 * Time: 22:03
 */
public class CompassListener implements SensorEventListener {

    private Context context;
    private SensorManager mSensorManager;
    private float currentDegree;
    private Sensor sensorAccelerometer;
    private Sensor sensorMagneticField;
    private float[] valuesAccelerometer;
    private float[] valuesMagneticField;
    private float[] matrixR;
    private float[] matrixI;
    private float[] matrixValues;
    private SensorUpdateListener sensorUpdateListener;
    public CompassListener(Context context){

        this.context = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        valuesAccelerometer = new float[3];
        valuesMagneticField = new float[3];
        matrixR = new float[9];
        matrixI = new float[9];
        matrixValues = new float[3];
    }

    public void setSensorUpdateListener(SensorUpdateListener sensorUpdateListener){

        this.sensorUpdateListener = sensorUpdateListener;
    }

    protected void startListen() {

        // for the system's orientation sensor registered listeners
        mSensorManager.registerListener(this, sensorMagneticField,
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, sensorAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void stopListen() {

        mSensorManager.unregisterListener(this, sensorMagneticField);
        mSensorManager.unregisterListener(this, sensorAccelerometer);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch(event.sensor.getType()){

            case Sensor.TYPE_ACCELEROMETER:
                for(int i =0; i < 3; i++){

                    valuesAccelerometer[i] = event.values[i];
                }
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                for(int i =0; i < 3; i++){

                    valuesMagneticField[i] = event.values[i];
                }
                break;
        }
        boolean success = SensorManager.getRotationMatrix(
                matrixR,
                matrixI,
                valuesAccelerometer,
                valuesMagneticField);

        if(success && sensorUpdateListener != null) {
            SensorManager.getOrientation(matrixR, matrixValues);
            double azimuth = Math.toDegrees(matrixValues[0]);
            double pitch = Math.toDegrees(matrixValues[1]);
            double roll = Math.toDegrees(matrixValues[2]);
            //update the listener
            sensorUpdateListener.onSensorUpdate(azimuth, pitch, roll);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not in use
    }

    public interface SensorUpdateListener{

        public void onSensorUpdate(double newAzimut, double newPitch, double newRoll);
    }
}
