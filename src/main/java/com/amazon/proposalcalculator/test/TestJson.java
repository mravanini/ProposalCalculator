package com.amazon.proposalcalculator.test;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJson {
	public static void main(String[] args) {
		try {
			// general method, same as with data binding
			ObjectMapper mapper = new ObjectMapper();
			// (note: can also use more specific type, like ArrayNode or ObjectNode!)
			JsonNode rootNode = mapper.readValue(new File("test.json"), JsonNode.class); // src can be a File, URL,
																							// InputStream etc
			System.out.println(rootNode.get("Records").get("s3").get("object").get("key"));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
