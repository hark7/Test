import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class PhoneNumberValidator {

	private static String endpoint = "http://localhost:8080/phone-number-validation-api-server";
	private static String LIST_OF_COUNTRIES_URL = "/v1/Phonenumber/phone-number/country-codes";
	private static String LANDLINE_VALIDATION_URL = "/v1/Landlinenumber/landline-number/validate";
	private static String MOBILE_VALIDATION_URL = "/v1/Mobilenumber/mobile-number/validate";
		
	public static List<Country> getCountries() {
		
		List<Country> countries = new ArrayList<>();
		try {
			String response = WebServiceClient.callService(endpoint + LIST_OF_COUNTRIES_URL, null);
			System.out.println(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// parse response to list of Country objects
		return countries;
	}
	
	public static void validateNumber (String phone, String country) {
		Map<String, String> requestMap = new HashMap<>();
		try {
			requestMap.put("phoneNumber", phone);
			requestMap.put("isoCountryCode", country);
			
			String validateLandlineResponse = WebServiceClient.callService(endpoint + LANDLINE_VALIDATION_URL, requestMap);
			String validateMobileResponse = WebServiceClient.callService(endpoint + MOBILE_VALIDATION_URL, requestMap);
			
			Response validatellResponse = parseValidationResponse(validateLandlineResponse);
			Response validatembResponse = parseValidationResponse(validateMobileResponse);
			
			if (validatellResponse.isValid()) {
				System.out.println("The given phone number is a valid LandLine");
				System.out.println("Geo location is :"+validatellResponse.getGeoLocation());
			} else if (validatembResponse.isValid()) {
				System.out.println("The given phone number is a valid MobileNumber");
				System.out.println("Geo location is :"+validatembResponse.getGeoLocation());
			} else {
				System.out.println("The given phone number is invalid");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static Response parseValidationResponse (String output) throws JSONException {
		Response response = new Response();
		
		JSONObject object = new JSONObject(output);
	
		response.setPhoneNumber((String) parse("phoneNumber", object));
		response.setIsoCountryCode((String) parse("isoCountryCode", object));
		response.setValid((boolean) parse("valid", object));
		response.setGeoLocation((String) parse("geoLocation", object));
		return response;
	}
	
	private static Object parse(String key, JSONObject object) {
		try {
			if (null != object.get(key)) {
				return object.get(key);
			} else
				return null;
		} catch (JSONException e) {
			return null;
		}
	}
	
	public static void main(String [] args) {
		//getCountries();
		validateNumber("5215541423370","MX");
	}
	
	
}
