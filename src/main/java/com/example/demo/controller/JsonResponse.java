package com.example.demo.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonResponse {
	public static ObjectMapper objectMapper = new ObjectMapper();
	public static final String KEY_ERROR_MESSAGE = "errorMessage";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_DATA = "data";

	/**
	 *
	 * @return
	 */
	public static ObjectNode getDefaultResponse(){
		ObjectNode objNode = JsonNodeFactory.instance.objectNode();
		return objNode;
	}

	/**
	 *
	 * @param params
	 * @param field
	 * @return
	 */
	public static boolean validateRequiredField(JsonNode params, String field){
		boolean isNullOrEmpty = params.get(field) == null || params.get(field).asText().trim().isEmpty();
		return !isNullOrEmpty;
	}
}
