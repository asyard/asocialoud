package org.yardimci.web.asocialoud.member;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.yardimci.web.asocialoud.Asocialoud;
import org.yardimci.web.asocialoud.db.model.Member;
import io.restassured.RestAssured;
import io.restassured.response.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Asocialoud.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberTest {

    private static final String API_ROOT = "http://localhost:8070/api/members";


    @Test
    public void whenGetAllMembers_thenOK() {
        Response response = RestAssured.get(API_ROOT);
        assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    //@Test
    public void whenCreateNewMember_thenCreated() {
        Member member = createMember();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(member)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    private Member createMember() {
        Member member = new Member();
        member.setLoginName("testmember");
        member.setRealName("Test Member");
        return member;
    }

}