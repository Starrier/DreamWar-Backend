package org.starrier.dreamwar;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ServletComponentScan
public class DreamwarApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(DreamwarApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DreamwarApplication.class, args);

		LOGGER.info("------------ DreamWar-Backend -------- Have Started ! -----");
	}


}
