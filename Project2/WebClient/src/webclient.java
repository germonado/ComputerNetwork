import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class webclient {
	public static String getWebContentByGet(String urlString, final String charset, int timeout) throws IOException{
		if(urlString == null || urlString.length() == 0) {
			return null;
		}
		urlString = (urlString.startsWith("http://")||urlString.startsWith("https://"))?urlString
				: ("http://"+urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MISE 6.0; Windows NT 5.2; Trident/4.0; NET CLR 1.1.4332; .NET CLR 2.0.50727)");
		conn.setRequestProperty("Accept", "text/html");
		conn.setConnectTimeout(timeout);
		try {
			if(conn.getResponseCode()!=HttpURLConnection.HTTP_OK) {
				return null;
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			return null;
		}
		InputStream input = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while((line=reader.readLine())!=null) {
			sb.append(line).append("\r\n");
		}
		if(reader!=null) {
			reader.close();
		}
		if(conn!=null) {
			conn.disconnect();
		}
		return sb.toString();
	}
	public static String getWebContentByPost(String urlString, String data, final String charset, int timeout) throws IOException{
		if(urlString==null||urlString.length()==0) {
			return null;
		}
		urlString = (urlString.startsWith("http://")||urlString.startsWith("https://"))?urlString:("http://"+urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
		connection.setRequestProperty("User-Agent", "2017029770/YUNHEE SEO/WEBCLIENT/COMPUTERNETWORK");
		connection.setRequestProperty("Accept", "text/xml");	
		connection.setConnectTimeout(timeout);
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		byte[] content = data.getBytes("UTF-8");
		out.write(content);
		out.flush();
		out.close();
		try {
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), charset));
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		if (reader != null) {
			reader.close();
		}
		if (connection != null) {
			connection.disconnect();
		}
		return sb.toString();
	}
	public static String getWebContentByGet(String urlString) throws IOException {
		return getWebContentByGet(urlString, "iso-8859-1", 5000);
	}
	public static String getWebContentByPost(String urlString, String data)
			throws IOException {
		return getWebContentByPost(urlString, data, "UTF-8", 5000);// iso-8859-1
	}
	public static void main(String argvp[]) throws Exception{
		webclient webc = new webclient();
		Scanner key = new Scanner(System.in);
		System.out.println("Put address");
		String s1 = webc.getWebContentByGet("http://166.104.143.225:50987/test/index.html");
		System.out.print(s1);
		System.out.println("picture number");
		String ans = "2017029770/";
		ans += key.nextLine();
	    String s2 = webclient.getWebContentByPost("http://166.104.143.225:50987/test/picResult",ans);
	    String s3 = webclient.getWebContentByPost("http://166.104.143.225:50987/test/postHandleTest",ans);
	    System.out.print(s3);
	}
}
