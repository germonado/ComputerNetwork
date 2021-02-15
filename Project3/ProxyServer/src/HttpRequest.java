import java.io.*;
import java.net.*;
import java.util.*;

public class HttpRequest {
	final static String CRLF = "\r\n";
    final static int HTTP_PORT = 80;
    String method;
    String URI;
    String version;
    String headers = "";
    private String host;
    private int port;
	public HttpRequest(BufferedReader from) {
		String firstLine = "";
		try {
			firstLine = from.readLine();
			System.out.println("test request : " + firstLine);
		}
		catch(IOException e) {
			System.out.println("Error reading request line: "+ e);
		}
	String[] tmp = firstLine.split(" ");
	    tmp = firstLine.split(" ");
		method = tmp[0];
		URI = tmp[1];
		version = tmp[2];
		   System.out.println("\nCreate HttpRequest by reading it from the client socket:");
		   System.out.println("Method is: " + method);
		   System.out.println("URI is: " + URI);
		   System.out.println("Version is: " + version);
	
	if(!method.equals("GET")) {
		System.out.println("Error: Method not GET");
	}
	try {
		String line = from.readLine();
		while(line.length()!=0) {
			headers += line + CRLF;
			if(line.startsWith("Host:")) {
				tmp = line.split(" ");
				if(tmp[1].indexOf(':')>0) {
					String[] tmp2 = tmp[1].split(":");
					host = tmp2[0];
					port = Integer.parseInt(tmp2[1]);
				}
				else {
					host = tmp[1];
					port = HTTP_PORT;
				}
			}
			line = from.readLine();
		}
	}
	catch(IOException e) {
		System.out.println("Error reading from socket: " + e);
		return;
	}
	System.out.println("Host to contact is: "+host+" at port "+port);
	}
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public String toString() {
		String req= "";
		req= method +" "+URI+" "+version+ CRLF;
		req+= headers;
		
		req+="Connection: close"+CRLF;
		req+=CRLF;
		
		return req;
	}
}
