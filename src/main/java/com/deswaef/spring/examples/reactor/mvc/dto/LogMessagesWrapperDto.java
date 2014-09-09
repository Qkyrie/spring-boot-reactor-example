package com.deswaef.spring.examples.reactor.mvc.dto;

/**
 * User: Quinten
 * Date: 8-9-2014
 * Time: 17:14
 *
 * @author Quinten De Swaef
 */
public class LogMessagesWrapperDto {
    private Long amount;
    private String lastEvent;

    public Long getAmount() {
        return amount;
    }

    public LogMessagesWrapperDto setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public String getLastEvent() {
        return lastEvent;
    }

    public LogMessagesWrapperDto setLastEvent(String lastEvent) {
        this.lastEvent = lastEvent;
        return this;
    }
}
