package com.xandr.pep_aston.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestConstructor;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestMethodOrder(MethodOrderer.Random.class)
//@Sql({"classpath:sql/sql1Init.sql", "classpath:sql/sql2Data.sql"})
public abstract class IntegrationTestBase {

    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres"));

    @BeforeAll
    public static void startContainer() {

        container.start();

    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.jpa.properties.hibernate.connection.url", container::getJdbcUrl);

    }
}
