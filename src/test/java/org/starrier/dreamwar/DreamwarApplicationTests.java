package org.starrier.dreamwar;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Starrier
 * @date 2019/05/22
 * @since 0.0.1-SNAPSHOT
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DreamwarApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test() throws Exception {
        for (int i = 0; i < 100; i++) {
            log.info("Test  Logger ：");
            log.debug("------------- Debug ------------------");
        }
    }

}
