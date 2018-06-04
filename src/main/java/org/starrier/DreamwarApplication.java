package org.starrier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@ImportResource(locations = { "classpath:druid/druid-bean.xml" })
public class DreamwarApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(DreamwarApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DreamwarApplication.class, args);

		LOGGER.info("------------ DreamWar-Backend -------- Have Started ! -----");
	}


}
