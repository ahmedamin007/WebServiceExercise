package com.altimetrik.external;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.altimetrik.domain.Ingredient;
import com.altimetrik.domain.Nutrition;
import com.altimetrik.domain.NutritionRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NutritionProxy {
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${nutritiontURL}")
	private String nutritiontURL ;
	
	
	public Ingredient getIngredientWithNutrition(String query) {

		Ingredient ingredient = new Ingredient();
		HttpHeaders headers = new HttpHeaders();
		ingredient.setDescription(query);
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-app-id", "242a0de9");
		headers.set("x-app-key", "8b7a41452a0e9efe1d7fa7d55c80d727");
		headers.set("x-remote-user-id", "shadi_77");
		NutritionRequest nutritionRequest = new NutritionRequest(query, "US/Eastern");
		
		HttpEntity<NutritionRequest> request= new HttpEntity<>(nutritionRequest,headers);
		
		ResponseEntity<String> responseEntity= restTemplate.postForEntity(nutritiontURL, request, String.class);
		String josnResponse=responseEntity.getBody();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = null;
		try {
			rootNode = mapper.readTree(josnResponse);
		}
		catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		
		if (rootNode != null) {
			
			JsonNode foodNode = rootNode.path("foods");
			Iterator<JsonNode> josnElemnts= foodNode.elements();
			if (josnElemnts.hasNext()) {
				JsonNode jsonRecord=josnElemnts.next();
				
				ingredient.setFoodName(jsonRecord.path("food_name").asText());
				ingredient.setServiceQty(jsonRecord.path("serving_qty").asDouble());
				ingredient.setServiceUnit(jsonRecord.path("serving_unit").asText());
				ingredient.setServingUnitsGrams(jsonRecord.path("serving_weight_grams").asDouble());
				ingredient.setNutrition(new Nutrition(jsonRecord.path("nf_total_fat").asDouble(), jsonRecord.path("nf_protein").asDouble(), jsonRecord.path("nf_total_carbohydrate").asDouble()));
			}
		}
		
		return ingredient;
	}
	
}
