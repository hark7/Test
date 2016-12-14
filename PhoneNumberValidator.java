import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import jdk.nashorn.internal.parser.JSONParser;

public class PhoneNumberValidator {

	private static String endpoint = "http://localhost:8080";
	private static String LIST_OF_COUNTRIES_URL = "/v1/Phonenumber/phone-number/country-codes";
	private static String LANDLINE_VALIDATION_URL = "/v1/Landlinenumber/landline-number/validate";
	private static String MOBILE_VALIDATION_URL = "/v1/Mobilenumber/mobile-number/validate";
		
	public static List<Country> getCountries() {
		
		List<Country> countries = new ArrayList<>();
		try {
			String response = WebServiceClient.callService(endpoint + LIST_OF_COUNTRIES_URL, null);
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
			} else if (validatembResponse.isValid()) {
				System.out.println("The given phone number is a valid MobileNumber");
			} else {
				System.out.println("The given phone number is invalid");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static Response parseValidationResponse (String output) {
		Response response = new Response();
		// code for parsing the string to json and convert to object
		return response;
	}
	
	
	public static void main(String [] args) {
		getCountries();
		validateNumber("+5215541423370","MX");
	}
	
	
}
