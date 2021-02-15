import java.io.*;
import java.util.*;
import java.net.*;
public class ProxyCache {
	private static int port;
	private static ServerSocket socket;
	private static Socket clinet =null;
	
	public static HashMap<String, HttpResponse> cache = new HashMap<String,HttpResponse>();
	public static void init(int p) {
		port = p;
		try {
			socket = new ServerSocket(port);
		}
		catch(IOException e) {
			System.out.println("Error creating socket: "+e);
			System.exit(-1);
		}
	}
	public static void handle(Socket client) {
		Socket server = null;
		HttpRequest request = null;
		HttpResponse response = null;
		System.out.println("in handle");
		try {
			BufferedReader fromClient =  new BufferedReader(new InputStreamReader(client.getInputStream()));
			request = new HttpRequest(fromClient);
		}
		catch(IOException e) {
			System.out.println("Error reading request from client: " + e);
			return;
		}
		
		try {
			server = new Socket(request.getHost(), request.getPort());
			DataOutputStream toServer =  new DataOutputStream(server.getOutputStream());
			toServer.writeBytes(request.toString());
		}
		catch (UnknownHostException e) {
			System.out.println("Unknown host: "+request.getHost());
			System.out.println(e);
			return;
		}
		catch (IOException e) {
			System.out.println("Error writing request to server: "+e);
			return;
		}
		try {
			DataInputStream fromServer = new DataInputStream (server.getInputStream());
			response =  new HttpResponse(fromServer);
			DataOutputStream toClient = new DataOutputStream(client.getOutputStream());
			toClient.write(response.toString().getBytes());
		    toClient.writeChars(response.body.toString());
	
			client.close();
			server.close();
		    cache.put(request.toString(), response);
		}
		catch(IOException e) {
			System.out.println("Error writing response to client: "+e);
		}
	}
	
	public static void main(String args[]) throws Exception{
		int myPort = 12245;
		/*try {
			myPort = Integer.parseInt(args[0]);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Need port number as argument");
			System.exit(-1);
		}
		catch(NumberFormatException e) {
			System.out.println("Please give port number as integer.");
			System.exit(-1);
		}*/
		
		init(myPort);
		
		Socket client = null;
		
		while(true) {
			try {
				client = socket.accept();
				handle(client);
			}
			catch(IOException e) {
				System.out.println("Error reading request from client: " + e);
				continue;
			}
		}
	}
}
