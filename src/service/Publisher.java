package service;

import javax.xml.ws.Endpoint;

public class Publisher {
	 
	 
	    public static void main(String[] args)
	    {//http://127.0.0.1:9876/service?wsdl
	        Endpoint.publish("http://127.0.0.1:9876/service",
	        new BaseServ());
	    }

}
