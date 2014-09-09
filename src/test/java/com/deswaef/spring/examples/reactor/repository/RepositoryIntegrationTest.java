package com.deswaef.spring.examples.reactor.repository;

import com.deswaef.spring.examples.reactor.SpringReactorExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * User: Quinten
 * Date: 15-7-2014
 * Time: 22:02
 *
 * @author Quinten De Swaef
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringReactorExample.class)
@WebAppConfiguration
@IntegrationTest
public class RepositoryIntegrationTest {

    @Test
    public void emptyTest() {
        //just an empty statement
    }

}
