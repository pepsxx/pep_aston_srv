package com.xandr.pep_aston.integration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.test.context.*;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestMethodOrder(MethodOrderer.Random.class)
@Sql({"classpath:sql/sql1Init.sql", "classpath:sql/sql2Data.sql"})
public abstract class IntegrationTestBase {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse("postgres"));

    @BeforeAll
    public static void startContainer() {

        container.start();

        // Это временно - Начало
        ConfigurableEnvironment environment = (ConfigurableEnvironment) new TestContextManager(IntegrationTestBase.class)
                .getTestContext()
                .getApplicationContext()
                .getEnvironment();

        environment.getPropertySources().stream()
                .filter(sp -> sp instanceof OriginTrackedMapPropertySource)
                .map(sp -> (OriginTrackedMapPropertySource) sp)
                .forEach(sp -> sp.getSource()
                        .forEach((key, value) -> System.out.printf("%60s: %s%n", key, value)));

        environment.getPropertySources().stream()
                .filter(i -> i.getClass().getSimpleName().contains("Map"))
                .map(PropertySource::getSource)
                .forEach(System.out::println);
        // Это временно - Конец

    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.jpa.properties.hibernate.connection.url", container::getJdbcUrl);

    }
}
