package za.co.wethinkcode.Tests.AcceptanceTests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;


class CommandTest {
    String port = "localhost";
    int portNumber = 5000;
    Socket client;

    /**
     * from client --> server
     */
    PrintStream out;
    /**
     * from Server --> client.
     */
    BufferedReader in;

    @BeforeEach
    public void ConnectClient() throws IOException {
        client = new Socket(port, portNumber);
        out = new PrintStream(client.getOutputStream());
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        assertTrue(client.isConnected());
    }

    @AfterEach
    public void DisconnectClient() throws IOException {
        client.close();
//        assertFalse(client.isConnected());
    }

    @Test
    void testForward() throws IOException {
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        out.println(request);
        out.flush();
        JSONObject response = new JSONObject(in.readLine());
        assertEquals("[0,0]", response.getJSONObject("data").getJSONArray("position").toString());
        JSONObject request2 = new JSONObject();
        JSONArray args = new JSONArray();
        request2.put("robot", "HAL");
        request2.put("command", "forward");
        args.put("1");
        request2.put("arguments", args);
        out.println(request2);
        out.flush();
        response = new JSONObject(in.readLine());
        assertEquals("[0,1]", response.getJSONObject("data").getJSONArray("position").toString());
    }

    @Test
    void testConstraints() throws IOException {
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        out.println(request);
        out.flush();
        JSONObject response = new JSONObject(in.readLine());
        assertEquals("[0,0]", response.getJSONObject("data").getJSONArray("position").toString());
        JSONObject request2 = new JSONObject();
        JSONArray args = new JSONArray();
        request2.put("robot", "HAL");
        request2.put("command", "forward");
        args.put("300");
        request2.put("arguments", args);
        out.println(request2);
        out.flush();
        response = new JSONObject(in.readLine());
        assertEquals("At the NORTH edge", response.getJSONObject("data").get("message").toString());
    }
}
