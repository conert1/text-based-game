package za.co.wethinkcode.RobotWorlds;

import za.co.wethinkcode.RobotWorlds.ServerCommands.ServerCommand;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ActivateServer {

    public static void main(String[] args) throws ClassNotFoundException,IOException {
        Thread ServerCommands = new Thread() {
            public void run() {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter a valid server command ('help' for all valid commands)");
                String instruction = scanner.nextLine();
                ServerCommand command = ServerCommand.create(instruction);
                while (command.runCommand() || command.runCommand(instruction)) {
                    System.out.println("Enter a valid server command ('help' for all valid commands)");
                    instruction = scanner.nextLine();
                    command = ServerCommand.create(instruction);
                }

            }
        };
        int XLength = 300;
        int YLength = 300;
        int obstacleNum = 10;
        int pitNum = 5;
        if (args.length == 1 && isValid(args)) {
            XLength = Integer.parseInt(args[0]);
        } else if (args.length == 2 && isValid(args)) {
            XLength = Integer.parseInt(args[0]);
            YLength = Integer.parseInt(args[1]);
        } else if (args.length == 3 && isValid(args)) {
            XLength = Integer.parseInt(args[0]);
            YLength = Integer.parseInt(args[1]);
            obstacleNum = Integer.parseInt(args[2]);
        } else if (args.length == 4 && isValid(args)) {
            XLength = Integer.parseInt(args[0]);
            YLength = Integer.parseInt(args[1]);
            obstacleNum = Integer.parseInt(args[2]);
            pitNum = Integer.parseInt(args[3]);
        }
        ServerCommands.start();
        World.setDimensions(XLength , YLength , obstacleNum , pitNum);
        createServer();
    }


    public static void createServer() throws IOException{
        World world = new World();
        world.generateObstacles(World.obstacleNum);
        world.generatePits(World.pitNum);
        ServerSocket s = new ServerSocket( SimpleServer.PORT);
        System.out.println("Server running & waiting for client connections.");
        while(true) {
            try {
                Socket socket = s.accept();
                System.out.println("Connection: " + socket);
                Runnable r = new SimpleServer(socket , world);
                Thread task = new Thread(r);
                task.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean isValid(String[] args){
        boolean bool;
        try {
            for (String number : args) {
                int i = Integer.parseInt(number);
            }
            bool = true;
        } catch (NumberFormatException e) {
            bool = false;
        }
        return bool;
    }
}
