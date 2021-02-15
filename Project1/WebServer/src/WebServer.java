import java.io.*;
import java.net.*;
import java.util.*;
public final class WebServer {
	public static void main(String argvp[]) throws Exception{
		try {
			ServerSocket serverSock = new ServerSocket(8020);
			//System.out.println("server socket created.");
				while(true) {
					Socket connectionsock = serverSock.accept();
						//System.out.println("accept");
					HttpRequest request = new HttpRequest(connectionsock);
					
					Thread thread = new Thread(request);
				    thread.start();
				}
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
