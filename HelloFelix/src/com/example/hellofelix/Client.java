package com.example.hellofelix;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;

import android.os.AsyncTask;

public class Client extends AsyncTask<String, String, String>{

    static String responseString="";
	static boolean bundleDownloaded=false;
	static MainActivity mainActivity= null;
	
	public Client (MainActivity mainActivity) {
		this.mainActivity=mainActivity;
	}	
	
	static void getHtmlFile(String[] uri)
	{
		//String url = "http://localhost:8080/com.vogella.jersey.first/rest/hello";
	    String charset = "UTF-8";

	    // The id of the audio message requested
	    String messageID = "1";

	    //URLConnection connection = null;


	    try {   
	      //  String query = String.format("messageID=%s", URLEncoder.encode(messageID, charset));
	        //URLConnection connection;
	        //URL u = new URL(url + "?" + query);

	        //connection = u.openConnection();
	        //InputStream in = connection.getInputStream();

	        HttpClient httpClient = new DefaultHttpClient();
//	        httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);


	        HttpGet httpGet = new HttpGet(uri[0]);
	        HttpResponse response = httpClient.execute(httpGet);
	        responseString = response.getStatusLine().toString();
	        //System.out.println(response.getStatusLine());

	        InputStream in = response.getEntity().getContent();

	        FileOutputStream fos = new FileOutputStream(new File(uri[1]));

	        byte[] buffer = new byte[4096];
	        int length; 
	        while((length = in.read(buffer)) > 0) {
	            fos.write(buffer, 0, length);
	        }
	        responseString = "Bundle Downloaded";
	        bundleDownloaded=true;
	        //connection = new URL(url + "?" + query).openConnection();
	        //connection.setRequestProperty("Accept-Charset", charset);
	        //InputStream response = connection.getInputStream();
	       // mainActivity.LogEx("dfd");
	       // mainActivity.displayLog(null);
	    } catch (MalformedURLException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        responseString = e.getMessage();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        responseString = e.getMessage();
	    }
	}
	
	public void getHtml()  // not used
	{
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpResponse response;
	    try {
	    	System.out.println("dfdf");
	    	//HttpGet httpGet = new HttpGet("http://www.simplesite.gr");
	    	HttpGet httpGet = new HttpGet("http://localhost:8080/com.vogella.jersey.first/rest/hello");
	     	
	    	//change to get octet stream:////
	    	httpGet.setHeader("Content-Type", "application/octet-stream");
	    	///////
	    	
	        response = httpclient.execute(httpGet);
	        StatusLine statusLine = response.getStatusLine();
	        if(statusLine.getStatusCode() == HttpStatus.SC_OK){
	            ByteArrayOutputStream out = new ByteArrayOutputStream();
	            response.getEntity().writeTo(out);
	            out.close();
	            responseString = out.toString();

	        } else{
	            //Closes the connection.
	            response.getEntity().getContent().close();
	            throw new IOException(statusLine.getReasonPhrase());
	        }
	    } catch (ClientProtocolException e) {
	    	responseString = "error: "+e.getMessage();
	        //TODO Handle problems..
	    } catch (IOException e) {
	    	responseString = "error: "+e.getMessage();
	        //TODO Handle problems..
	    }
	    //System.out.print(responseString);
	    //	return;
	}
	
	

	@Override
	protected String doInBackground(String... uri){
		// TODO Auto-generated method stub
		getHtmlFile(uri);
		return null;
	}
}
