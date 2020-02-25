package com.altimetrik.external;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.altimetrik.domain.Ingredient;
import com.altimetrik.domain.Recipe;
import com.altimetrik.service.NutritionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RecipeProxy {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	NutritionService nutritionService;
	
	@Value("${recipetURL}")
	private  String recipetURL ;
	
	public Recipe getRecipe(String query) {
		Recipe recipe = new Recipe();
		recipe.setQuery(query);
		String url = recipetURL+query;
		ResponseEntity<String> recipeEntity = restTemplate.getForEntity(url,String.class);
		String jsonRespone = recipeEntity.getBody();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode=null;
		try {
			jsonNode = mapper.readTree(jsonRespone);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		if (jsonNode!= null) {
			JsonNode hits = jsonNode.path("hits");
			Iterator<JsonNode> elemnts= hits.elements();
			if (elemnts.hasNext()) {
				JsonNode recipeContainerElm = elemnts.next();
				JsonNode recipeElm = recipeContainerElm.path("recipe");
				
				recipe.setName(recipeElm.path("label").asText());
				recipe.setUri(recipeElm.path("uri").asText());
				recipe.setSource(recipeElm.path("source").asText());
				Iterator<JsonNode> itrIngredients=  recipeElm.path("ingredients").elements();
				while (itrIngredients.hasNext()) {
					String currentIngredient = itrIngredients.next().path("text").asText();
					Ingredient ingredient = nutritionService.getIngredientWithNutrition(currentIngredient);
					recipe.addIngredient(ingredient);
				}
				
			}
			
			recipe.calculateNutrition();
		}
		
		return recipe;
	}

	
}
