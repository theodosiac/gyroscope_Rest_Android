package com.vogella.jersey.first;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Hello {

	private static final String FILE_PATH = "e:\\bundle.apk";
  // This method is called if TEXT_PLAIN is request
//  @GET
//  @Produces(MediaType.TEXT_PLAIN)
//  public String sayPlainTextHello() {
//    return "Hello Jersey";
//  }

  // This method is called if XML is request
//  @GET
//  @Produces(MediaType.TEXT_XML)
//  public String sayXMLHello() {
//    return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
//  }
//
//  // This method is called if HTML is request
//  @GET
//  @Produces(MediaType.TEXT_HTML)
//  public String sayHtmlHello() {
//    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
//        + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
//  }
//  
  
  @GET
 // @Path("/get")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getFile() {
      File file = new File(FILE_PATH);
      ResponseBuilder response = Response.ok((Object) file);
      response.header("Content-Disposition",
          "attachment; filename=bundle.apk");
      return response.build();

  }

} 