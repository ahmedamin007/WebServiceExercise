package com.altimetrik.service;

import com.altimetrik.domain.Ingredient;

public interface NutritionService {
	public Ingredient getIngredientWithNutrition(String query);
	
}
