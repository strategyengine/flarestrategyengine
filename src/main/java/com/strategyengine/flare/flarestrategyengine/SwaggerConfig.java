package com.strategyengine.flare.flarestrategyengine;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://localhost:8080/swagger-ui.html
 * @author barry
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
		        .select()                                  
		          .apis(RequestHandlerSelectors.any())              
		          .paths(PathSelectors.any()).build();
	}

	private Predicate<String> postPaths() {
		return or(regex("/api/posts.*"), regex("/api/flarestrategyengine.*"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("FlareStrategyEngine API")
				.description("FlareStrategyEngine API reference for developers").license("FlareStrategyEngine License")
				.version("1.0").build();
	}

}
