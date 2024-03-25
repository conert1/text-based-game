package za.co.wethinkcode.Tests.AcceptanceTests;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.net.Socket;

public class StateCommand {
    String port = "localhost";
    int portNumber = 5000;
    Socket client;

    /**from client --> server*/
    PrintStream out;
    /**from Server --> client.*/
    BufferedReader in;


    public StateCommand() throws IOException {
    }

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
//        assertFalse(client.isConnected());
    }


    @Test
    public void testStateCommand() throws IOException {
        String request ="{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        out.println(request);
        out.flush();
        System.out.println(in.readLine());
        request ="{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"state\"," +
                "  \"arguments\": []" +
                "}";
        out.println(request);
        out.flush();
        JSONObject response = new JSONObject(in.readLine());
        assertEquals("{\"shields\":2,\"position\":[0,0],\"shots\":5,\"direction\":\"North\",\"status\":\"Normal\"}" , response.get("state").toString());
    }

    @Test
    public void testStateCommandAfterMoving() throws IOException {
        String request ="{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        out.println(request);
        out.flush();
        System.out.println(in.readLine());
        request ="{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [\"5\"]" +
                "}";
        out.println(request);
        out.flush();

        JSONObject response = new JSONObject(in.readLine());
        assertEquals("{\"shields\":2,\"position\":[0,5],\"shots\":5,\"direction\":\"North\",\"status\":\"Normal\"}" , response.get("state").toString());
    }

}
