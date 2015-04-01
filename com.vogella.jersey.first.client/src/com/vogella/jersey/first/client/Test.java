package com.vogella.jersey.first.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;

public class Test {

	private static void copyFileUsingFileStreams(File source, File dest)
	        throws IOException {
	    InputStream input = null;
	    OutputStream output = null;
	    try {
	        input = new FileInputStream(source);
	        output = new FileOutputStream(dest);
	        byte[] buf = new byte[1024];
	        int bytesRead;
	        while ((bytesRead = input.read(buf)) > 0) {
	            output.write(buf, 0, bytesRead);
	        }
	    } finally {
	        input.close();
	        output.close();
	    }
	}
	
	
  public static void main(String[] args) throws FileNotFoundException {

    
/*** * Old API * * ClientConfig config = new DefaultClientConfig(); * * Client client = Client.create(config); * * WebResource service = client.resource(getBaseURI()); * * // Fluent interfaces * * System.out.println(service.path("rest").path("hello").accept(* MediaType.TEXT_PLAIN).get(ClientResponse.class).toString()); * * // Get plain text * * System.out.println(service.path("rest").path("hello").accept(* MediaType.TEXT_PLAIN).get(String.class)); * * // Get XML * * System.out.println(service.path("rest").path("hello").accept(* MediaType.TEXT_XML).get(String.class)); * * // The HTML * * System.out.println(service.path("rest").path("hello").accept(* MediaType.TEXT_HTML).get(String.class)); ***/


    ClientConfig config = new ClientConfig();

    Client client = ClientBuilder.newClient(config);

    WebTarget target = client.target(getBaseURI());

    System.out.println(target.path("rest").path("hello").request()

    .accept(MediaType.TEXT_PLAIN).get(Response.class)

    .toString());

    System.out.println(target.path("rest").path("hello").request()

    .accept(MediaType.TEXT_PLAIN).get(String.class));

    System.out.println(target.path("rest").path("hello").request()

    .accept(MediaType.TEXT_XML).get(String.class));
 
    System.out.println(target.path("rest").path("hello").request()
    		
    .accept(MediaType.TEXT_HTML).get(String.class));

    //System.out.println(target.path("rest").path("hello").request()
    //.accept(MediaType.MULTIPART_FORM_DATA).get(String.class));
    
   // System.out.println( response.toString());
  /* System.out.println(target.path("rest").path("hello").request()
    		
    .accept(MediaType.APPLICATION_OCTET_STREAM).get(String.class));*/
   //String str="";
    
    
    File sourceFile = 
			target.path("rest").path("hello").request()
    		
		    .accept(MediaType.APPLICATION_OCTET_STREAM).get(File.class);


	File file = new File("e:/1_new.jpg");
	try {
	FileOutputStream fop = new FileOutputStream(file);

	// if file doesnt exists, then create it
	if (!file.exists()) {
		file.createNewFile();
	}
	try {
		copyFileUsingFileStreams(sourceFile,file);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	// get the content in bytes
	//byte[] contentInBytes = 
	
			target.path("rest").path("hello").request()
    		
    .accept(MediaType.APPLICATION_OCTET_STREAM).get(File.class);

//		fop.write(contentInBytes);
		fop.flush();
		fop.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
//   ClientResponse response = target.path("pdf").queryParam("name", "newfile.zip").ge
//	        .get(ClientResponse.class);
//	   response.getEntityInputStream();
  }

  private static URI getBaseURI() {

    return UriBuilder.fromUri("http://localhost:8080/com.vogella.jersey.first").build();

  }
} 