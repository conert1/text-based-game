package za.co.wethinkcode.Tests.AcceptanceTests;

import org.codehaus.jackson.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.RobotWorlds.RobotWorldClient;
import za.co.wethinkcode.RobotWorlds.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;

class QuitCommandTest {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }

    @Test
    void TestQuitCommand() {
        assertTrue(serverClient.isConnected());

        String launch = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"quit\"," +
                "  \"arguments\": [\"10]" +
                "}";
        JsonNode re = serverClient.sendRequest(launch);
        JsonNode response = serverClient.sendRequest(launch);
        System.out.print(response);
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        assertNotNull(response.get("data"));
        assertNotNull(response.get("state").get("position"));
        assertEquals(0, response.get("state").get("position").get(0).asInt());
        assertEquals(0, response.get("state").get("position").get(1).asInt());



    }
}


