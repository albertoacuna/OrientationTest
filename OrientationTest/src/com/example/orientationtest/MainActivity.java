package com.example.orientationtest;

import android.app.Activity;
import android.content.Context;

import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.SensorManager;

public class MainActivity extends Activity{
  private TextView direction;
  private LocationManager locationManager;
  private Position position;
  private Orientation orientation;
  private SensorManager sensorManager;
  
/** Called when the activity is first created. */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
  
    direction = (TextView) findViewById(R.id.editText1);
    position = new Position(locationManager);
    orientation = new Orientation(sensorManager, position);

    // Initialize the location fields
    if (orientation != null) {
      direction.setText(String.valueOf(orientation.getOrientation()));
      
    } else {
      direction.setText("Not available");
    }
  }

  /* Request updates at startup */
  @Override
  protected void onResume() {
    super.onResume();
   
  }

  /* Remove the locationlistener updates when Activity is paused */
  @Override
  protected void onPause() {
    super.onPause();
  }



} 