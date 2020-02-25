package com.altimetrik.domain;

import java.util.ArrayList;
import java.util.List;

public class Recipe {

	
	private String query;
	private String name;
	private String uri;
	private String source;
	private Nutrition nutrition;
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	
	
	public Recipe() {
		
	}
	public Recipe(String query, String name, String uri, String source) {
		super();
		this.query = query;
		this.name = name;
		this.uri = uri;
		this.source = source;
	}


	public String getQuery() {
		return query;
	}


	public void setQuery(String query) {
		this.query = query;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUri() {
		return uri;
	}


	public void setUri(String uri) {
		this.uri = uri;
	}


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	public Nutrition getNutrition() {
		return nutrition;
	}


	public void setNutrition(Nutrition nutrition) {
		this.nutrition = nutrition;
	}


	public List<Ingredient> getIngredients() {
		return ingredients;
	}


	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	public void addIngredient(Ingredient ingredient) {
		ingredients.add(ingredient);
	}
	public void calculateNutrition() {
		Nutrition nutrition = new Nutrition();
		
		double fat=0.0;
		double protein=0.0;
		double carbohydrate=0.0;
		
		for (Ingredient ingredient : ingredients) {
			if (ingredient != null && ingredient.getNutrition() != null) {
				fat+=  ingredient.getNutrition().getFat();
				protein += ingredient.getNutrition().getProtein();
				carbohydrate += ingredient.getNutrition().getCarbohydrate();
			}
		}
		
		nutrition.setCarbohydrate(carbohydrate);
		nutrition.setFat(fat);
		nutrition.setProtein(protein);
		
		this.setNutrition(nutrition);
		
		
	}
	
	
	
			
			
			
}
