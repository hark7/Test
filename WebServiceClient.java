
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import sun.net.www.protocol.http.HttpURLConnection;

public class WebServiceClient {
		
	public static String callService (String urlText, Map<String, String> requestMap) 
			throws IOException {
		
		URL url = new URL(urlText);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/json");
		
		if (null != requestMap && !requestMap.isEmpty()) {
			for (String key : requestMap.keySet()) {
				connection.setRequestProperty(key, requestMap.get(key));
			}
		}
		
		if(connection.getResponseCode() != 200) {
			throw new RuntimeException("Error occurred while calling web service");
		}
		
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connection.getInputStream()));
		
		String response = null;
		while ((response = reader.readLine()) != null) {
			System.out.println(response);
		}
		
		return response;
	}


}
