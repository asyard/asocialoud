package org.yardimci.asocialoud.members.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/")
    public String index() {
        logger.debug("Returning index page");
        return "index.html";
    }

    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    @RequestMapping(value = "{_:^(?!index\\.html|api).+}")
    public String redirectToHome() {
        return "index.html";
    }

}
