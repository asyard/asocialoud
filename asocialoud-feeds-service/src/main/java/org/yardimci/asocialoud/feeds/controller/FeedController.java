package org.yardimci.asocialoud.feeds.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.yardimci.asocialoud.feeds.db.model.Feed;
import org.yardimci.asocialoud.feeds.db.repository.FeedRepository;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/feeds")
public class FeedController {

    private static final Logger logger = LoggerFactory.getLogger(FeedController.class);

    @Autowired
    private FeedRepository feedRepository;

    //@LoadBalanced
    //todo paging, get last 50 feeds etc
    @GetMapping("/of/{memberId}")
    public FeedResponse findFeeds(@PathVariable("memberId") Long memberId) {
        logger.info("Retrieving all feeds of : " + memberId);
        FeedResponse feedResponse = new FeedResponse();

        List<Feed> feeds = feedRepository.findAllByMemberId(memberId);

        feedResponse.setData(feeds);
        feedResponse.setStatus(HttpStatus.OK.toString());
        return feedResponse;
    }


    //@LoadBalanced
    //todo paging, get last 50 feeds etc
    //todo instead of this, ask member service for all followings
    @GetMapping("/followingsof/{memberIds}")
    public FeedResponse findFeedsOfFollowings(@PathVariable("memberIds") Long[] memberIds,
                                              @RequestParam(value = "dateAfter", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date dateAfter) {
        logger.info("Retrieving all followings feeds");
        FeedResponse feedResponse = new FeedResponse();

        if (memberIds == null || memberIds.length < 1) {
            logger.warn("Missing feed info");
            feedResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
            feedResponse.setData("error.missinginformation");
            return feedResponse;
        }

        List<Feed> feeds = dateAfter == null ? feedRepository.findAllByMemberIdIn(memberIds) : feedRepository.findAllByMemberIdInAndPublishDateAfter(memberIds, dateAfter);

        feedResponse.setData(feeds);
        feedResponse.setStatus(HttpStatus.OK.toString());
        return feedResponse;
    }


    //todo feed dto
    // todo check if member exists
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public FeedResponse create(@RequestBody Feed feed) {
        logger.info("Save feed request received");
        FeedResponse feedResponse = new FeedResponse();

        if (feed.getText() == null || feed.getText().isEmpty() || feed.getMemberId() == null || feed.getMemberId()<1L) {
            logger.warn("Missing feed info");
            feedResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
            feedResponse.setData("error.missinginformation");
            return feedResponse;
        }
        Feed newFeed = new Feed();
        newFeed.setMemberId(feed.getMemberId());
        newFeed.setText(feed.getText()); //todo escape chars etc
        newFeed.setMediaUri("");
        newFeed.setPublishDate(new Date());

        try {
            logger.info("Saving feed");
            feedRepository.save(newFeed);
            feedResponse.setStatus(HttpStatus.CREATED.toString());
            feedResponse.setData(newFeed);
        } catch (Exception e) {
            logger.error("Unable to save feed", e);
            feedResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
            feedResponse.setData("error.servererror");
        }

        return feedResponse;
    }


}
