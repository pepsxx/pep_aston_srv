package com.xandr.pep_aston;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

@Slf4j
@SpringBootApplication
public class PepAstonApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(PepAstonApplication.class, args);
        log.info("""

                -----------------------------------------------------------------------------
                -                                              $$                           -
                - $$$$$$$$     $$$$$$   $$$$$$$$            $$$$$$$$  $$$$  $$$$ $$$$  $$$$ -
                - $$$$$$$$$   $$$$$$$$  $$$$$$$$$          $$$$$$$$$$ $$$$  $$$$ $$$$  $$$$ -
                - $$$$  $$$$ $$$$  $$$$ $$$$  $$$$         $$$$   $$$  $$$  $$$   $$$  $$$  -
                - $$$$  $$$$ $$$$  $$$$ $$$$  $$$$         $$$$         $$$$$$     $$$$$$   -
                - $$$$$$$$$  $$$$$$$$$$ $$$$$$$$$          $$$$$$$$$     $$$$       $$$$    -
                - $$$$$$$$   $$$$$$$$$$ $$$$$$$$            $$$$$$$$$    $$$$       $$$$    -
                - $$$$       $$$$       $$$$                     $$$$   $$$$$$     $$$$$$   -
                - $$$$       $$$$  $$$$ $$$$               $$$   $$$$  $$$  $$$   $$$  $$$  -
                - $$$$        $$$$$$$$  $$$$     $$$$$$$$  $$$$$$$$$$ $$$$  $$$$ $$$$  $$$$ -
                - $$$$          $$$$    $$$$     $$$$$$$$   $$$$$$$$  $$$$  $$$$ $$$$  $$$$ -
                -                                              $$                           -
                -----------------------------------------------------------------------------""");

        // Это временно - Начало
        ConfigurableEnvironment environment = context.getEnvironment();
        environment.getPropertySources().stream()
                .filter(i -> i.getClass().getSimpleName().contains("Map"))
                .map(PropertySource::getSource)
                .forEach(System.out::println);

        environment.getPropertySources().stream()
                .filter(sp -> sp instanceof OriginTrackedMapPropertySource)
                .map(sp -> (OriginTrackedMapPropertySource) sp)
                .forEach(sp -> sp.getSource()
                        .forEach((key, value) -> System.out.printf("%60s: %s%n", key, value)));
        // Это временно - Конец

    }
}
