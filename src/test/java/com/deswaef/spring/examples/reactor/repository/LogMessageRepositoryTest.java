package com.deswaef.spring.examples.reactor.repository;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.fest.assertions.Assertions.assertThat;

public class LogMessageRepositoryTest extends RepositoryIntegrationTest{

    @Autowired
    private LogMessageRepository repository;

    @Test
    public void findAllIsNotNull() {
        assertThat(repository.findAll()).isNotNull();
    }

}