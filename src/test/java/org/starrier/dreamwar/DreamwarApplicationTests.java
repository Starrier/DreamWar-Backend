package org.starrier.dreamwar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DreamwarApplicationTests {

	@Test
	public void contextLoads() {
	}

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Test
	public void test()throws Exception {
		for(int i=0;i<100;i++) {
			LOGGER.info("Test  Logger ：");
			LOGGER.debug("------------- Debug ------------------");
		}
	}

}
