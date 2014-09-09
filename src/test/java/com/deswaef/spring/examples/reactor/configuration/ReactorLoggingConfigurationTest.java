package com.deswaef.spring.examples.reactor.configuration;

import com.deswaef.spring.examples.reactor.SpringReactorExample;
import com.deswaef.spring.examples.reactor.repository.LogMessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import reactor.core.Reactor;
import reactor.event.Event;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringReactorExample.class)
@WebAppConfiguration
@IntegrationTest
public class ReactorLoggingConfigurationTest {

    @Autowired
    private Reactor r;

    @Autowired
    private LogMessageRepository logMessageRepository;

    @Test
    public void logMessagesCreateDatabaseEntries() {
        r.notify("log.trace", Event.wrap("new logmessage"));
    }

}