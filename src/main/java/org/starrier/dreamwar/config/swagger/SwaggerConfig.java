package org.starrier.dreamwar.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author Starrier
 * @Time 2018/6/5.
 */

@Configuration
public class SwaggerConfig {

    /**
     * <p>Configuration</p>
     * <p>Swagger2 for Base Configuration</p>
     * <p>package,path etc...</p>
     *
     * {@link Docket}
     * @return Swagger configuration file.
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.starrier.dreamwar.controller"))
                .paths(PathSelectors.any())
                .build();
    }



    /**
     * <p>Swagger Information</p>
     * <p>Detail Information for Swagger2 API to be build</p>
     * {@link ApiInfo}
     * @return Swagger Information will be return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档")
                .description("简单优雅的restfun风格，http://blog.csdn.net/saytime")
                .termsOfServiceUrl("http://blog.csdn.net/saytime")
                .version("1.0")
                .build();

    }
}
