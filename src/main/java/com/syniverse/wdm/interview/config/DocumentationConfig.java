package com.syniverse.wdm.interview.config;

import java.util.Collections;
import java.util.HashSet;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// $Id$
// $URL$
@Configuration
@Profile("swagger")
@EnableSwagger2
@Configurable
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DocumentationConfig {

  @Bean
  public Docket newsApi() {
    final Docket dock =
        new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.syniverse.wdm")).paths(PathSelectors.any()).build();
    final ApiInfo apiInformation =
        new ApiInfoBuilder().title("General's armed forces REST APIs").version(this.getClass().getPackage().getImplementationVersion()).build();
    dock.forCodeGeneration(true);
    dock.apiInfo(apiInformation);
    dock.useDefaultResponseMessages(false);
    dock.produces(new HashSet<>(Collections.singletonList("application/json")));
    return dock;
  }
}
