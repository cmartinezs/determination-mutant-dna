package io.cmartinezs.dmd.app.config;

import io.cmartinezs.dmd.app.controller.MutantController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author Carlos
 * @version 1.0
 */

@Configuration
public class SwaggerConfig {

    @Value("${swagger.info.app-version}")
    private String appVersion;
    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(MutantController.class.getPackage().getName()))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiEndPointsInfo());
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder()
                .title("Determination Mutan DNA API Rest (DMD)")
                .description("DMD is a project created to determine if a human is a mutant from a sequence of their DNA.")
                .contact(new Contact("Carlos Martínez", "https://github.com/cmartinezs/determination-mutant-dna", "carlos.f.martinez.s@gmail.com"))
                .version(appVersion)
                .build();
    }
}
