package com.altimetrik.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.altimetrik.domain.Ingredient;
import com.altimetrik.service.NutritionService;


@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeServiceTest {

	@Autowired
	NutritionService nutritionService;
	
	@Test
	void test() {
		String query = "1 lb. jumbo shell-on shrimp (16 to 20 per lb.), preferably deveined";
		String result = "shell";
		
		Ingredient testIngerdient = nutritionService.getIngredientWithNutrition(query);
		String foodName = "";
		
		if(testIngerdient != null) {
			foodName = testIngerdient.getFoodName();
		}
		
		assertEquals(foodName, result);
	}

}
