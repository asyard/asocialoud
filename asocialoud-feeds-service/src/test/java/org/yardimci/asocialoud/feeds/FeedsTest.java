package org.yardimci.asocialoud.feeds;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.yardimci.asocialoud.feeds.db.model.Feed;
import org.yardimci.asocialoud.feeds.db.repository.FeedRepository;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AsocialoudFeedService.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedsTest {

    @Autowired
    private FeedRepository feedRepository;

    @LocalServerPort
    private int port;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @Transactional
    public void when_member_feeds_requested_then_OK() {
        Response response = when().get("/api/feeds/of/1").then().contentType(ContentType.JSON).extract().response();

        System.out.println(response.asString());
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
    }


    @Test
    @Transactional
    public void when_member_has_new_feed_then_saved() {
        Feed feed = createFeed(2L, "Feed 1 of member 2");
        feedRepository.save(feed);

        List<Feed> feedsOfMember = feedRepository.findAllByMemberIdOrderByPublishDateDesc(2L);
        assertEquals(1, feedsOfMember.size());

    }

    @Test
    @Transactional
    public void when_feeds_requested_then_listed() {
        Feed feed = createFeed(2L, "Feed 1 of member 2");
        Feed feed2 = createFeed(2L, "Feed 2 of member 2");
        Feed feed3 = createFeed(3L, "Feed 1 of member 3");
        feedRepository.save(feed);
        feedRepository.save(feed2);
        feedRepository.save(feed3);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(feed3.getPublishDate().getTime() - 1000 * 60);

        Date feedsAfter = cal.getTime();

        Long[] memberIds = {2L, 3L};

        List<Feed> feedsOfMember = feedRepository.findAllByMemberIdInAndPublishDateAfterOrderByPublishDateDesc(memberIds, feedsAfter);
        assertEquals(3, feedsOfMember.size());

    }


    @Test
    @Transactional
    public void when_feeds_of_followings_requested_then_OK() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, -1000 * 60);
        Date feedsAfter = cal.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateAfterString = simpleDateFormat.format(feedsAfter);

        //Long[] memberIds = {2L, 3L};
        //String[] memberIdsString = Arrays.stream(memberIds).map(String::valueOf).toArray(String[]::new);

        //http://localhost:8060/api/feeds/followingsof/1,2,3,4,5?dateAfter=18.08.2019%2011:01:01
        Response response = given()
                .params("dateAfter", dateAfterString).when()
                .get("/api/feeds/followingsof/1,2,3").then().contentType(ContentType.JSON).extract().response();

        System.out.println(response.asString());
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());

    }


    private Feed createFeed(Long memberId, String text) {
        Feed feed = new Feed();
        feed.setMemberId(memberId);
        feed.setPublishDate(new Date());
        feed.setText(text);
        return feed;
    }

}
