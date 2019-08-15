package org.yardimci.asocialoud.members;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.yardimci.asocialoud.members.db.model.Member;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { AsocialoudMemberService.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberTest {

    //private static final String API_ROOT = "http://localhost:8070/api/members";

    @LocalServerPort
    private int port;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    private String authenticateAndGetToken() {
        Response response = given().contentType(ContentType.JSON).body("{\n" +
                "\t\"username\" : \"admin\",\n" +
                "\t\"password\" : \"admin\"\n" +
                "}").get("/api/members/login");
        response.then().statusCode(HttpStatus.SC_OK);

        String bearerToken = (String) ((Map) response.getBody().jsonPath().get("data")).get("token");

        return bearerToken;
    }

    @Test
    public void whenGetAllMembers_thenOK() {
        String bearerToken = authenticateAndGetToken();

        Response response = given()
                .headers(
                        "Authorization",
                        "Bearer " + bearerToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .when().get("/api/members").then().contentType(ContentType.JSON).extract().response();

        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        System.out.println(response.asString());

        //Response response = RestAssured.get(API_ROOT);
        //assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    /*@Test
    public void whenCreateNewMember_thenCreated() {
        Member member = createMember();
        Response response = RestAssured.given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(member)
                .post(API_ROOT);

        assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }*/

    private Member createMember() {
        Member member = new Member();
        member.setLoginName("testmember");
        member.setRealName("Test Member");
        return member;
    }

}