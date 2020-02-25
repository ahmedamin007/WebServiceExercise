package com.altimetrik.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.builders.RequestHandlerSelectors;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
    		.select()
            .apis(RequestHandlerSelectors.basePackage("com.altimetrik.controller"))
            .paths(regex("/altimetrik.*"))
            .build()
            .apiInfo(metaData());
  }
  
  
  private ApiInfo metaData() {
	  
      return new ApiInfoBuilder()
              .title("Recipe API Exercise")
              .description("\"Search Meals Recipes\"")
              .version("1.0.0")
              .license("None")
              .licenseUrl("")
              .contact(new Contact("Shadi Farrag", "https://github.com/ShadiFarrag/recipe-api", "shadi.farrag@gmail.com"))
              .build();
  }
  
}
