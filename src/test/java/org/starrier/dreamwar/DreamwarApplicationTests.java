package org.starrier.dreamwar;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DreamwarApplicationTests {

	@Test
	public void contextLoads() {
	}

	private Logger logger = Logger.getLogger(getClass());

	@Test
	public void test()throws Exception {
		for(int i=0;i<100;i++) {
			logger.info("Test  Logger ：");
			logger.debug("------------- Debug ------------------");
		}
	}

}
