package za.co.wethinkcode.RobotWorlds;

import java.net.*;
import java.io.*;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.wethinkcode.RobotWorlds.ServerCommands.ServerCommand;

public class Client {
    private static String name;
    /**
     * Sends request to the server through the established network
     *
     * @param args a string array with the IP Address of the server
     */
    public static void main(String[] args) {
        Thread clientThread = new Thread() {
            @Override
            public void run() {
                runClient();
            }
        };
        clientThread.start();

    }



    public static void runClient(){
        Scanner scanner = new Scanner(System.in);
        String serverIP = "localhost";
        String serverResponse;
        try {
            Socket socket = new Socket(serverIP, 5000);
            /**from client --> server*/
            PrintStream out = new PrintStream(socket.getOutputStream());
            /**from Server --> client.*/
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            /**launch robot loop*/
            while(true){
                System.out.println("To Launch Robot , please enter your name and desired make in the following order:\n\'launch cole sniper\'\nThe available makes are as follows\n1.Sniper\n2.Shooter\n3.Bomber");
                String input = scanner.nextLine();
                String request = sendLaunchCommand(input).toString();
                out.println(request);
                out.flush();

                
                serverResponse = in.readLine();
                JSONObject response = receiveResponse(serverResponse);
                System.out.println(response);
                System.out.println(response.getJSONObject("data").get("message"));
                if (response.get("result").equals("ERROR")) {
                    continue;
                }else {
                    break;
                }

            }
            /**SHOULD BE RESPONSE MESSAGE AND STATE.*/
            JSONObject response = new JSONObject(serverResponse);
            System.out.println("max shields : " +  response.getJSONObject("state").get("shields") + "\n" +
                    "max shots : " + response.getJSONObject("state").get("shots") + "\n" +
                    "direction : " + response.getJSONObject("state").get("direction") + "\n" +
                    "position : " + response.getJSONObject("state").getJSONArray("position"));
            /**main game loop*/
            while (!response.getJSONObject("state").get("status").equals("Dead")) {
                System.out.println("What would you like to do?");
                String input = scanner.nextLine();
                if (input.equals("quit")){
                    socket.close();
                    break;
                }
                JSONObject request = sendRequest(input);
                out.println(request);
                serverResponse = in.readLine();
                response = receiveResponse(serverResponse);
                if (response.get("result").equals("ERROR")) {
                    System.out.println(response.getJSONObject("data").get("message"));
                } else {
                    System.out.println(name + response.getJSONObject("data").get("position") + " : " + response.getJSONObject("data").get("message"));
                }
            }
            System.out.println("You died.Goodbye!");


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject sendLaunchCommand(String input){
        String [] arguments = input.split(" ");
        JSONObject request = new JSONObject();
        try {
            name = arguments[1];
            request.put("robot" , name);
            request.put("command" , arguments[0]);
            JSONArray argument = new JSONArray();
            request.put("arguments" , argument.put(arguments[2]));
        } catch (ArrayIndexOutOfBoundsException e) {
            request.put("command", "");
            JSONArray argument = new JSONArray();
            request.put("arguments" , argument);
        }

        return request;
    }

    public static Boolean isNameValid(JSONObject response) {
        return response.getString("result").equals("OK");

    }


    public static JSONObject sendRequest(String input){
        String [] arguments = input.split(" ");
        String command = arguments[0];
        JSONObject request = new JSONObject();
        request.put("robot" , name);
        request.put("command" , command);
        JSONArray argument = new JSONArray();
        String args;
        if (arguments.length == 1){
            args = "";
        }
        else {
            args = arguments[1];
        }
        argument.put(args);
        request.put("arguments" , argument);
        return request;
    }

    public static JSONObject receiveResponse(String input){
        return new JSONObject(input);
    }

    public static void GotShot(){
        System.out.println("You got shot");
    }

}
