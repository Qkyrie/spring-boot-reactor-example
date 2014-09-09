package com.deswaef.spring.examples.reactor.mvc;

import com.deswaef.spring.examples.reactor.model.LogMessage;
import com.deswaef.spring.examples.reactor.mvc.dto.LogMessagesWrapperDto;
import com.deswaef.spring.examples.reactor.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.Reactor;
import reactor.event.Event;
import reactor.function.Consumer;

import javax.annotation.PostConstruct;

import java.util.Collections;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static reactor.event.selector.Selectors.$;
import static reactor.event.selector.Selectors.R;

/**
 * User: Quinten
 * Date: 8-9-2014
 * Time: 11:34
 *
 * @author Quinten De Swaef
 */
@Controller
@RequestMapping(value = "/")
public class WelcomeController {

    @Autowired
    private Reactor r;
    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private LoggingService loggingService;

    @PostConstruct
    public void init() {
        r.on($("log.created"), reactOnLogging());
    }

    private Consumer<Event<LogMessage>> reactOnLogging() {
        return logMessage -> {
            List<LogMessage> all = loggingService
                    .findAll();
            LogMessagesWrapperDto logMessagesWrapperDto = new LogMessagesWrapperDto()
                    .setAmount(all.stream().count())
                    .setLastEvent(all.stream()
                            .reduce((previous, current) -> current)
                            .get().getText());
            template.convertAndSend("/topic/newlogs", logMessagesWrapperDto);
        };
    }


    @RequestMapping(method = GET)
    public String welcome() {
        r.notify("log.debug", Event.wrap("Wew, someone accessed our page!"));
        return "main";
    }


}
