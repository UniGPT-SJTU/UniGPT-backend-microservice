package com.unigpt.chat.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BasicConfig {

    public final String POSTGRES_HOST;
    public final Integer POSTGRES_PORT;
    public final String POSTGRES_DB;
    public final String POSTGRES_USERNAME;
    public final String POSTGRES_PASSWORD;

    private final Logger log = org.slf4j.LoggerFactory.getLogger(BasicConfig.class);

    public BasicConfig(
            @Value("${postgres.host}") String postgresHost,
            @Value("${postgres.port}") Integer postgresPort,
            @Value("${postgres.database}") String postgresDatabase,
            @Value("${postgres.username}") String postgresUsername,
            @Value("${postgres.password}") String postgresPassword) {

        this.POSTGRES_HOST = postgresHost;
        this.POSTGRES_PORT = postgresPort;
        this.POSTGRES_DB = postgresDatabase;
        this.POSTGRES_USERNAME = postgresUsername;
        this.POSTGRES_PASSWORD = postgresPassword;

        log.info("Postgres Host: " + POSTGRES_HOST);
        log.info("Postgres Port: " + POSTGRES_PORT);
        log.info("Postgres DB: " + POSTGRES_DB);
        log.info("Postgres Username: " + POSTGRES_USERNAME);
        log.info("Postgres Password: " + POSTGRES_PASSWORD);
        log.info("Basic Config Done");

    }
}
