package za.co.wethinkcode.Tests.Unittests;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.*;
import za.co.wethinkcode.RobotWorlds.WebApi.JavalinServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebApiTest {
    private static JavalinServer server;

//    @BeforeAll
//    public static void startServer() {
//        server = new JavalinServer();
//        server.start(5000);
//    }
//
//    @AfterAll
//    public static void stopServer() {
//        server.stop();
//    }

    @Test
    public void getOneQuote() throws UnirestException {
        HttpResponse<String> response = Unirest.get("http://localhost:4000/obstacles").asString();
        assertEquals(200, response.getStatus());
        assertEquals("application/json", response.getHeaders().getFirst("Content-Type"));

        String word = response.getBody();
//        assertEquals("There is no snooze button on a cat who wants breakfast.", word);
//        assertEquals("Unknown", word);
    }

    @Test
    void getAllQuotes() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("http://localhost:4000/pits").asJson();
        assertEquals(200, response.getStatus());
        JSONArray jsonArray = response.getBody().getArray();
        assertTrue(jsonArray.length() > 1);
    }
}