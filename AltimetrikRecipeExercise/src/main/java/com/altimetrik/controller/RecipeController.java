package com.altimetrik.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.domain.Ingredient;
import com.altimetrik.domain.Recipe;
import com.altimetrik.service.NutritionService;
import com.altimetrik.service.RecipeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping("/altimetrik")
@Api(value="This is Our Base Controller")
public class RecipeController {

	
	@Autowired
	NutritionService nutritionService;
	
	@Autowired
	RecipeService recipeService;

	
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve food recipe!"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
	
	@ApiOperation(value = "Get Recipe Details",response = String.class)
	@RequestMapping("/hi")
	public String sayHi() {
		return "hi";
	}
    
	@ApiOperation(value ="Get Nutrition Details for an ingredient" , response=Ingredient.class)
	@GetMapping("/ingredient/{query}")
	public ResponseEntity<?> getIngredientDetails(@ApiParam(value = "enter query") @PathVariable String query){
		Ingredient ingredient = nutritionService.getIngredientWithNutrition(query);
		return  new ResponseEntity<Ingredient>(ingredient , HttpStatus.OK);
	}
	@ApiOperation(value = "Get Recipe Details",response = Recipe.class)
	@GetMapping("/recipe/{query}")
	public ResponseEntity<?> getRecipe(@ApiParam(value="enter food name" , required = true)  @PathVariable String query){
		Recipe recipe = recipeService.getRecipeDetails(query);
		return new ResponseEntity<Recipe>(recipe,HttpStatus.OK);
	}

	@GetMapping(value= "/query")
	public String getFoodQueryView(HttpServletRequest request , Model model) {	
		System.out.println("query 1");
		return "foodHome";
	}

	@PostMapping(value= "/queryIngredients")
	public String postFoodQueryView(HttpServletRequest request , Model model) {	
		if (request.getParameter("dataquery") == null) {
			System.out.println("exitt");
			return "foodHome";
		}
		
		
		String option = request.getParameter("api");
		String query = request.getParameter("dataquery").toString();
		if (option.equalsIgnoreCase("nutrition")) {
			Ingredient ingredients = nutritionService.getIngredientWithNutrition(query);
			model.addAttribute("ingredients",ingredients);
			model.addAttribute("mode",1);
		}
		else if  (option.equalsIgnoreCase("recipe")) {
			Recipe recipe = recipeService.getRecipeDetails(query);
			model.addAttribute("recipes",recipe);
			model.addAttribute("mode",2);
		}
		return "foodHome";
	}
	
	
	@GetMapping(value=  "/")
	public String getFoodView(HttpServletRequest request ,Model model) {
		return "index";
	}
}
