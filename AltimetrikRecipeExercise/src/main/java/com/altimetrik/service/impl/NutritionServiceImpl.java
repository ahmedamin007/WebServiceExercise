package com.altimetrik.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altimetrik.domain.Ingredient;
import com.altimetrik.external.NutritionProxy;
import com.altimetrik.service.NutritionService;

@Service
public class NutritionServiceImpl implements NutritionService {

	@Autowired
	NutritionProxy nutritionProxy;
	
	@Override
	public Ingredient getIngredientWithNutrition(String query) {
		return nutritionProxy.getIngredientWithNutrition(query);
	}

}
