package org.starrier;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@ServletComponentScan
@EnableAutoConfiguration
@ImportResource(locations = { "classpath:druid/druid-bean.xml" })
public class DreamwarApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(DreamwarApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DreamwarApplication.class, args);

		LOGGER.info("------------ DreamWar-Backend -------- Have Started ! -----");
	}


}
