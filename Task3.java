package IntervivewAssessment;

import io.restassured.http.ContentType;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class Task3 {
	
	@BeforeAll
	public static void setUp() {
		
		baseURI = "http://api.url.org";
	}
	@AfterAll
	public static void tearDown() {
		
		reset();
	}
	//Create new user 1st Option
	@DisplayName("Add 1 New User with Raw Json String POST /user")
    @Test
    public void testAddNewUserWithString() {

        String newUserStr =
                "    {\n" +
                        "      \"name\": \"John\",\n" +
                        "       \"age\": 25\n" +
                        "        \"country\": \"USA\n" +
                        "    }";

        System.out.println(newUserStr);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(newUserStr).
        when()
                .post("/user").
        then()
                .log().all()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("data.name", is("John"))
                .body("data.age", is(25))
                .body("data.country", is("USA"))
        ;

    }

    //Create new user 2nd Option
    @DisplayName("Add 1 New User with with Map Object POST /user")
    @Test
    public void testAddNewUserWithMapAsBody() {

        Map<String, Object> payloadMap = new LinkedHashMap<>();
        payloadMap.put("name", "John");
        payloadMap.put("age", 25);
        payloadMap.put("country", "USA");

        System.out.println(payloadMap);

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(payloadMap).
        when()
                .post("/user").
        then()
                .log().all()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("data.name", is("John"))
                .body("data.age", is(25))
                .body("data.country", is("USA"))
        ;

    }
    
    //Create new user 3rd Option
    @DisplayName("Add 1 Data with External json file POST /api/spartans")
    @Test
    public void testAddNewUserWithJsonFileAsBody() {

        File externalJson = new File("newUser.json");

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(externalJson).
        when()
                .post("/user").
        then()
                .log().all()
                .statusCode(is(201))
                .contentType(ContentType.JSON)
                .body("data.name", is("John"))
                .body("data.age", is(25))
                .body("data.country", is("USA"))
        ;

    }

    @DisplayName("Testing PUT /user/{id} with String body")
    @Test
    public void testUpdatingUserWithStringBody() {

        String updateStrPayload =
                "    {\n" +
                        "      \"name\": \"Mike\",\n" +
                        "       \"age\": 25\n" +
                        "        \"country\": \"USA\n" +
                        "    }";

        given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("id", 105)
                .body(updateStrPayload).
        when()
                .put("/user/{id}").
        then()
                .log().all()
                .statusCode(is(204))
                .header("Date", is(notNullValue()))
        ;


    }

    @DisplayName("Testing Get /user/{id} endpoint")
    @Test
    public void test1Spartan() {

        given()
                .accept(ContentType.JSON).
        when()
                .get("/user/105").
        then()
                .statusCode(is(200))
                .contentType(ContentType.JSON)
        ;
    }

}
