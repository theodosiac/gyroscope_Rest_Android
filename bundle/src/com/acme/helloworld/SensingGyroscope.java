package com.acme.helloworld;


import java.util.EventListener;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
/*public class SensingGyroscope
{
	
}*/
public class SensingGyroscope extends Activity implements SensorEventListener {
private SensorManager sensorManager;
Sensor temperature;
EventListener event;
TextView textview;
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// Get an instance of the SensorManager
	sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	if(sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE).size()!=0)
	{
		temperature = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE).get(0);
		sensorManager.registerListener(this,temperature, SensorManager.SENSOR_DELAY_NORMAL);
	}
	else
	{
		String message = "Sorry!Sensor not found";
		// Create the text view
		TextView textView = new TextView(this);
		textView.setTextSize(20);
		textView.setText(message);
		// Set the text view as the activity layout
		setContentView(textView);
		return;
	}
}
@Override
public void onSensorChanged(SensorEvent event) {
// Create the text view
TextView textView = new TextView(this);
textView.setTextSize(15);
//else it will output the Roll, Pitch and Yawn values
textView.setText("Orientation X (Roll) :"+ Float.toString(event.values[2]) +"\n"+
"Orientation Y (Pitch) :"+ Float.toString(event.values[1]) +"\n"+
"Orientation Z (Yaw) :"+ Float.toString(event.values[0]));
setContentView(textView);
}
@Override
public void onAccuracyChanged(Sensor sensor, int accuracy) {
//do nothing
}
@Override
protected void onResume() {
super.onResume();
if(sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE).size()!=0){
temperature = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE).get(0);
sensorManager.registerListener(this,temperature, SensorManager.SENSOR_DELAY_NORMAL);
}
}
@Override
protected void onPause() {
sensorManager.unregisterListener(this);
super.onStop();
}
//action bar
@Override
public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
//getMenuInflater().inflate(R.menu.main, menu);
return true;

}}