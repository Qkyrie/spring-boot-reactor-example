package com.deswaef.spring.examples.reactor.configuration;

import com.deswaef.spring.examples.reactor.exceptions.ReactorExampleException;
import com.deswaef.spring.examples.reactor.model.LogCategory;
import com.deswaef.spring.examples.reactor.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.fn.Consumer;

import javax.annotation.PostConstruct;

import static reactor.bus.selector.Selectors.R;
import static reactor.bus.selector.Selectors.T;

/**
 * User: Quinten
 * Date: 6-9-2014
 * Time: 01:43
 *
 * @author Quinten De Swaef
 */
@Component
public class ReactorLoggingConfiguration {

    @Autowired
    private EventBus r;

    @Autowired
    private LoggingService loggingService;

    @PostConstruct
    public void onStartUp() {
        r.on(R("log.(trace|debug)"), logForDebug());
        r.on(T(ReactorExampleException.class), logForException());
    }

    private Consumer<Event<?>> logForException() {
        return logException -> loggingService.log(LogCategory.ERROR, logException.getData().toString());
    }


    private Consumer<Event<String>> logForDebug() {
        return logInfoEvent -> loggingService.log(LogCategory.INFO, logInfoEvent.getData());
    }
}
