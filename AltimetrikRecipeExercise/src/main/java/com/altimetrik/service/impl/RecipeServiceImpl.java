package com.altimetrik.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altimetrik.domain.Recipe;
import com.altimetrik.external.RecipeProxy;
import com.altimetrik.service.RecipeService;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	RecipeProxy recipeProxy; 
	
	@Override
	public Recipe getRecipeDetails(String query) {
		return recipeProxy.getRecipe(query);
	}

}
