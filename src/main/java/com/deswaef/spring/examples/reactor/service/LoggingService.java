package com.deswaef.spring.examples.reactor.service;

import com.deswaef.spring.examples.reactor.model.LogCategory;
import com.deswaef.spring.examples.reactor.model.LogMessage;
import com.deswaef.spring.examples.reactor.repository.LogMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.bus.Event;
import reactor.bus.EventBus;

import java.util.Date;
import java.util.List;

/**
 * User: Quinten
 * Date: 6-9-2014
 * Time: 01:43
 *
 * @author Quinten De Swaef
 */
@Service
public class LoggingService {

    @Autowired
    private LogMessageRepository logMessageRepository;

    @Autowired
    private EventBus r;

    @Transactional(readOnly = true)
    public List<LogMessage> findAll() {
        return logMessageRepository.findAll();
    }
    @Transactional
    public void log(LogCategory category, String message) {
        LogMessage save = logMessageRepository.save(
                new LogMessage()
                        .setLogDate(new Date())
                        .setText(message)
                        .setCategory(category)
        );
        r.notify("log.created", Event.wrap(save));
    }

}
