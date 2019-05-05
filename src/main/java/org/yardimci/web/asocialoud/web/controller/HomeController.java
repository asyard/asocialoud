package org.yardimci.web.asocialoud.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "alogin";
    }

    @GetMapping("/secure/profile")
    public String secured() {
        return "secure/profile";
    }

    /*
    uncomment if another view used insted of zk
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/")
    public String index() {
        logger.debug("Returning index page");
        return "index.html";
    }*/

}
