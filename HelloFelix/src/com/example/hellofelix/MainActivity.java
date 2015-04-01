package com.example.hellofelix;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.felix.framework.Felix;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract.Directory;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	List<org.osgi.framework.Bundle> installedBundles ;
	//EditText editText = (EditText) findViewById(R.id.text_view);
	TextView label = null;//(TextView) findViewById(R.id.text_view);
	private Felix m_felix =null;
	BundleContext felixContext;
	Client restClient;
	String bundlePath;
	
	public void LogEx(String info)
	{
		Log.d("Felix",info);
		if(label == null)
			label = (TextView) findViewById(R.id.text_view);
		
		if(label != null)
			label.setText(label.getText()+info+"\n");
	}
	
	void launchFelix() {
		
		
		
		//initiations

		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText("storage/emulated/0/files/bundle_d.apk");
		bundlePath = textView1.getText().toString();
		LogEx("About to start Felix");
		String cacheDir= null;
		try
		{
			cacheDir = File.createTempFile("skifta", ".tmp").getParent();
		}
		catch(IOException e)
		{
			Log.d("Felix", "Unable to create temp file",e);
		}
		
		final String ANDROID_FRAMEWORK_PACKAGES = (
	            "android,"
	            + "android.app,"
	            + "android.content,"
	            + "android.database,"
	            + "android.database.sqlite,"
	            + "android.graphics,"
	            + "android.graphics.drawable,"
	            + "android.graphics.glutils,"
	            + "android.hardware,"
	            + "android.location,"
	            + "android.media,"
	            + "android.net,"
	            + "android.net.wifi,"
	            + "android.opengl,"
	            + "android.os,"
	            + "android.provider,"
	            + "android.sax,"
	            + "android.speech.recognition,"
	            + "android.telephony,"
	            + "android.telephony.gsm,"
	            + "android.text,"
	            + "android.text.method,"
	            + "android.text.style,"
	            + "android.text.util,"
	            + "android.util,"
	            + "android.view,"
	            + "android.view.animation,"
	            + "android.webkit,"
	            + "android.widget");
		
		Map configMap = new HashMap();
		
		//configMap.put(Constants.FRAMEWORK_STORAGE, fwDir);

		  // Export android packages so they can be referenced by bundles
		configMap.put(Constants.FRAMEWORK_SYSTEMPACKAGES_EXTRA,
		    ANDROID_FRAMEWORK_PACKAGES);
		
		configMap.put("org.osgi.framework.storage", cacheDir);
		configMap.put("felix.embedded.execution", "true");
		configMap.put("org.osgi.service.http.port", "9990");
		configMap.put("org.osgi.framework.startlevel.beginning", "5");		
		
	
		try
		{
			m_felix = new Felix(configMap);
			m_felix.start();
			
			if (m_felix != null) 
			   {
				  // Register the application's context as an OSGi service!
				  BundleContext bundleContext = m_felix.getBundleContext();
				  bundleContext.registerService(Context.class,getApplicationContext(),new Hashtable());

				  LogEx("context registered");			
				}
		    else {				  
				  LogEx("context not registered");
				 }
						
			LogEx("Felix is started!");
			
			
			for(org.osgi.framework.Bundle b : m_felix.getBundleContext().getBundles())
				LogEx("Bundle: "+ b.getSymbolicName()+ ",ID: "+b.getBundleId());
			
		}
		catch(Throwable ex)
		{
			Log.d("Felix", "Could not create Framework: "+ex.getMessage(),ex);
		}
		
		
		
		felixContext = m_felix.getBundleContext();
		installedBundles = new LinkedList<org.osgi.framework.Bundle>();




		
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		launchFelix();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/** Called when the user clicks the Send button */
	public void DownloadBundle(View view) {	

		restClient = new Client(this);
		//requestTask.execute("http://www.simplesite.gr");	
		restClient.execute("http://192.168.1.104:8080/com.vogella.jersey.first/rest/hello", bundlePath);
		
	}
	/** Called when the user clicks the Send button */
	public void InstallBundles(View view) {	
//		if(restClient.bundleDownloaded!=true)
//		
//		{
//			LogEx("Bundle(s) is not yet downloaded.");
//			return;
//		}
//		
		//get bundles from rest
//		try
//		{
//			RestClient restClient = new RestClient();
//		}
//		catch(IOException e)
//		{
//			Log.d("Felix", "Unable to get the file from rest",e);
//		}

		
		// Install the bundles
		try {			
			installedBundles.add(felixContext.installBundle("file:"+bundlePath));
		    } 
		     catch (BundleException e) 
	    	{

			LogEx("Additional Bundles were not installed");
			LogEx(e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
	
	}
	/** Called when the user clicks the Send button */
	public void StartBundles(View view) {		
	
		for (org.osgi.framework.Bundle bundle : installedBundles) 
	{ 
	    try {
			bundle.start();

			LogEx("Bundle started ("+bundle.getSymbolicName()+")");
			/*for(org.osgi.framework.ServiceReference b : bundle.getRegisteredServices())
			{
				String str = bundle.getBundleContext().getService(b);
				LogEx(str);
				
			}*/
		} catch (BundleException e) {
			LogEx("Bundle not started: ");
			// TODO Auto-generated catch block
			LogEx(e.getMessage());
		}
	}
	for(org.osgi.framework.Bundle b : m_felix.getBundleContext().getBundles())
		LogEx("Bundle: "+ b.getSymbolicName()+ ",ID: "+b.getBundleId());
	// Start installed bundles
	
	}
	
	/** Called when the user clicks the Send button */
	public void displayData(View view) {
	    // Do something in response to button
		for (org.osgi.framework.Bundle bundle : installedBundles) 
		{ 

			org.osgi.framework.ServiceReference b = bundle.getBundleContext().getServiceReference(ArrayList.class.getName());
			if (b!=null)
			{			
				try {
					
					ArrayList<Float> sensorData = (ArrayList<Float> )bundle.getBundleContext().getService(b);
					String str =  "Orientation X (Roll) :"+ Float.toString(sensorData.get(0)) +"\n"+
					"Orientation Y (Pitch) :"+ Float.toString(sensorData.get(1)) +"\n"+
					"Orientation Z (Yaw) :"+ Float.toString(sensorData.get(2));
					
					LogEx(str);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					LogEx(e.getMessage());
				}
				//LogEx(logEvent.getMessage());
				
			}
			else
			{
				LogEx("sensor data service not found");
			}
	}
}
	
	
	/** Called when the user clicks the Send button */
	public void displayLog(View view) {
		LogEx("We get from http the following:\n"+restClient.responseString);
	    // Do something in response to button
		for (org.osgi.framework.Bundle bundle : installedBundles) 
		{ 

				
		//LogEx("Bundle started ("+bundle.getSymbolicName()+")");
				for(org.osgi.framework.ServiceReference b1 : bundle.getRegisteredServices())
				{
					if(bundle.getBundleContext().getService(b1).getClass().getName().equals(String.class.getName()))
					{
						String str = bundle.getBundleContext().getService(b1);
						LogEx(str);
					}
					//LogEvent logEvent = (LogEvent)bundle.getBundleContext().getService(b);
					//LogEx(logEvent.getMessage());
				}

		}
		
	}
}
