package org.yardimci.web.asocialoud.web.controller.defaults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yardimci.web.asocialoud.db.repository.CountryRepository;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryRepository countryRepository;

    @GetMapping
    public Iterable findAll() {
        logger.info("Retrieving all countries");
        return countryRepository.findAll();
    }

}
