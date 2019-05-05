package org.yardimci.asocialoud.members;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories("org.yardimci.asocialoud.members.db.repository")
@EntityScan("org.yardimci.asocialoud.members.db.model")
@SpringBootApplication // equals @Configuration, @EnableAutoConfiguration, and @ComponentScan
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class AsocialoudMemberService {

    private static final Logger logger = LoggerFactory.getLogger(AsocialoudMemberService.class);

    public static void main(String[] args) {
        logger.info("Starting asocialoud members. Horraay!");
        SpringApplication.run(AsocialoudMemberService.class, args);
    }

}
