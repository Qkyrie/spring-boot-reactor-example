package com.deswaef.spring.examples.reactor.repository;

import com.deswaef.spring.examples.reactor.model.LogMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * User: Quinten
 * Date: 6-9-2014
 * Time: 01:02
 *
 * @author Quinten De Swaef
 */
@RepositoryRestResource(collectionResourceRel = "logs", path = "logs")
public interface LogMessageRepository extends JpaRepository<LogMessage, Long> {
}
