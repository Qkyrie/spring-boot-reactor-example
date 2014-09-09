package com.deswaef.spring.examples.reactor.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

import static javax.persistence.EnumType.STRING;

/**
 * User: Quinten
 * Date: 6-9-2014
 * Time: 00:28
 *
 * @author Quinten De Swaef
 */
@Entity
public class LogMessage {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private Date logDate;
    @Enumerated(value = STRING)
    private LogCategory category;


    public Long getId() {
        return id;
    }

    public LogMessage setId(Long id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public LogMessage setText(String text) {
        this.text = text;
        return this;
    }

    public Date getLogDate() {
        return logDate;
    }

    public LogMessage setLogDate(Date logDate) {
        this.logDate = logDate;
        return this;
    }

    public LogCategory getCategory() {
        return category;
    }

    public LogMessage setCategory(LogCategory category) {
        this.category = category;
        return this;
    }
}
