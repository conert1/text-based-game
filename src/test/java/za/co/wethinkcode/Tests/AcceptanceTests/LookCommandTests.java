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

public class LookCommandTests {
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
    }

    @Test
    public void TestEdges() throws IOException {
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        out.println(request);
        out.flush();
        JSONObject response = new JSONObject(in.readLine());
        System.out.println(response);
        request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        out.println(request);
        out.flush();
        response = new JSONObject(in.readLine());
        assertNotNull(response.getJSONObject("data").get("message").toString());
    }

    @Test
    public void TestOtherRobot() throws IOException {
        String launchRobot1 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        out.println(launchRobot1);
        out.flush();
        JSONObject responseRobot1 = new JSONObject(in.readLine());
        JSONObject request2 = new JSONObject();
        JSONArray args = new JSONArray();
        request2.put("robot", "HAL");
        request2.put("command", "forward");
        args.put("5");
        request2.put("arguments", args);
        out.println(request2);
        out.flush();
        responseRobot1 = new JSONObject(in.readLine());
        String launchRobot2 = "{" +
                "  \"robot\": \"John\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        out.println(launchRobot2);
        out.flush();
        JSONObject responseRobot2 = new JSONObject(in.readLine());
        System.out.println(responseRobot2);
        String launchRobot3 = "{" +
                "  \"robot\": \"Phil\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        out.println(launchRobot3);
        out.flush();
        String requestRobot1 = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        out.println(requestRobot1);
        out.flush();
        in.readLine();
        responseRobot1 = new JSONObject(in.readLine());
        System.out.println(responseRobot1);
//        assertNotNull(responseRobot1.getJSONObject("data").getJSONArray("objects"));

    }







}
