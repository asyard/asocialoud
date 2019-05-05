package org.yardimci.web.asocialoud;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories("org.yardimci.web.asocialoud.db.repository")
@EntityScan("org.yardimci.web.asocialoud.db.model")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) // equals @Configuration, @EnableAutoConfiguration, and @ComponentScan
public class Asocialoud {

    private static final Logger logger = LoggerFactory.getLogger(Asocialoud.class);

    public static void main(String[] args) {
        logger.info("Starting asocialoud. Horraay!");
        SpringApplication.run(Asocialoud.class, args);
    }

}
