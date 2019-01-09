package org.starrier.dreamwar;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Warning.
 *
 * 1.When we use the {@link EnableCaching},we can not use {@link java.util.Optional},
 *   Because the {@link java.util.Optional} can not applied for {@link java.io.Serializable}.
 *
 * @author Starrier
 * @date 2018/1/7
 * */
@EnableAspectJAutoProxy
@EnableCaching
@EnableTransactionManagement(proxyTargetClass = true)
@EnableSwagger2
@SpringBootApplication
@ServletComponentScan
public class DreamWarApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(DreamWarApplication.class);

	public static void main(String[] args) {

		LOGGER.info("------------ DreamWar-Backend -------- is starting!-----");

		SpringApplication.run(DreamWarApplication.class, args);

		LOGGER.info("------------ DreamWar-Backend -------- Have Started ! -----");
	}


}
