package org.yardimci.web.asocialoud;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class Asocialoud {

    private static final Logger logger = LoggerFactory.getLogger(Asocialoud.class);

    public static void main(String[] args) {
        logger.info("Starting asocialoud. Horraay!");
        SpringApplication.run(Asocialoud.class, args);
    }

}
