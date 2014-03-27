package com.example.orientationtest;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.hardware.GeomagneticField;

public class Orientation implements SensorEventListener {
	private static final int ARM_DISPLACEMENT_DEGREES = 6;
	
	private final SensorManager sensorManager;
	private float[] R = new float[16];
	private float[] orientation = new float[3];
	private float magHeading;
	private float trueReading;
	private Position position;
	private GeomagneticField geomagneticField;
	
	public Orientation(SensorManager mSensorManager, Position pos){
		sensorManager = mSensorManager;
		position = pos;
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy){
		
	}
	@Override
	public void onSensorChanged(SensorEvent event){
		if(event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR){
			
		SensorManager.getRotationMatrixFromVector(R, event.values);
		SensorManager.getOrientation(R, orientation);
		
		magHeading = (float)Math.toDegrees(orientation[2]);
		trueReading = mod(computeTrueNorth(magHeading), 360.0f) - ARM_DISPLACEMENT_DEGREES;
		}
	}
	
	private static float mod(float a, float b) {
        return (a % b + b) % b;
    }
	
	private float computeTrueNorth(float mHeading){
		geomagneticField = position.getGeomagneticField();
		if (geomagneticField != null) {
            return mHeading + geomagneticField.getDeclination();
        } else {
            return mHeading;
        }
	}
	
	public float getOrientation(){
		return trueReading;
	}
	
}

