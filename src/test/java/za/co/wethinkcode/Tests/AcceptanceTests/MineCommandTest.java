package za.co.wethinkcode.Tests.AcceptanceTests;

import org.codehaus.jackson.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.RobotWorlds.World;


import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MineCommandTest {
    String port = "localhost";
    int portNumber = 5000;
    Socket client;

    /**from client --> server*/
    PrintStream out;
    /**from Server --> client.*/
    BufferedReader in;
    @BeforeEach
    public void ConnectClient() throws IOException {
        client = new Socket(port , portNumber);
        out = new PrintStream(client.getOutputStream());
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        assertTrue(client.isConnected());
    }

    @AfterEach
    public void DisconnectClient() throws IOException {
        client.close();
    }
    @Test
    void MineCommand() throws IOException {String request ="{" +
            "  \"robot\": \"HAL\"," +
            "  \"command\": \"launch\"," +
            "  \"arguments\": [\"bomber\"]" +
            "}";
        out.println(request);
        out.flush();
        in.readLine();
        JSONObject request2 = new JSONObject();
        JSONArray args = new JSONArray();
        request2.put("robot", "HAL");
        request2.put("command" , "mine");
        request2.put("arguments" , args);
        out.println(request2);
        System.out.println(request2);
        out.flush();
        JSONObject response = new JSONObject(in.readLine());
        System.out.println(response);
        assertEquals("[0,1]" , response.getJSONObject("data").getJSONArray("position").toString());
        World.clearMines();
    }


    @Test
    void invalidMakeCannotSetMine() throws IOException {
        World.clearMines();
        String request ="{" +
            "  \"robot\": \"Gig\"," +
            "  \"command\": \"launch\"," +
            "  \"arguments\": [\"sniper\"]" +
            "}";
        out.println(request);
        out.flush();
        System.out.println(in.readLine());
        JSONObject request2 = new JSONObject();
        JSONArray args = new JSONArray();
        request2.put("robot", "Gig");
        request2.put("command" , "mine");
        request2.put("arguments" , args);
        out.println(request2);
        out.flush();
        JSONObject response = new JSONObject(in.readLine());
        assertEquals(0 ,World.mines.size());
        World.clearMines();
    }
}
