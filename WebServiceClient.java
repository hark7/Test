
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import sun.net.www.protocol.http.HttpURLConnection;

public class WebServiceClient {
		
	public static String callService (String urlText, Map<String, String> requestMap) 
			throws IOException {
		
		if (null != requestMap && !requestMap.isEmpty()) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("?");
			for (String key : requestMap.keySet()) {
				buffer.append(key);
				buffer.append("=");
				buffer.append(requestMap.get(key));
				buffer.append("&");
			}
			buffer.deleteCharAt(buffer.length()-1);
			urlText=urlText+buffer.toString();
		}
		
		URL url = new URL(urlText);
			
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
	
		
		if(connection.getResponseCode() != 200) {
			throw new RuntimeException("Error occurred while calling web service :"+connection.getResponseCode());
		}
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		
		String output = null;
		String response = null;
		while ((output = reader.readLine()) != null) {
			System.out.println(output);
			response = output;
		}
		
		return response;
	}


}
