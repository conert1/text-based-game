package za.co.wethinkcode.RobotWorlds.ServerCommands;

import za.co.wethinkcode.RobotWorlds.Mine;
import za.co.wethinkcode.RobotWorlds.Robot;
import za.co.wethinkcode.RobotWorlds.SquareObstacle;
import za.co.wethinkcode.RobotWorlds.World;

public class DumpCommand extends ServerCommand{


    public DumpCommand(){
        super("dump");
    }

    @Override
    public boolean runCommand() {
        if (World.robots.size() == 0) {
            System.out.println("There are no robots in the world.");
        } else {
            System.out.println("The robots in the world as as follows:");
            for (Robot robot : World.robots) {
                System.out.println(robot.name + "(" + robot.getKind() + ")" + "[" + robot.getPosition().getX() + "," + robot.getPosition().getY() + "]");
            }
        }
        if (World.obstacles.size() == 0) {
            System.out.println("There are no obstacles in the world.");
        } else {
            System.out.println("There is an obstacle:");
            for (SquareObstacle obstacle : World.obstacles) {
                System.out.println("At " + obstacle.getBottomLeftX() + "," + obstacle.getBottomLeftY() + " to (" + (obstacle.getBottomLeftX() + 4) + "," + (obstacle.getBottomLeftY() + 5) + ")");
            }
        }
        if (World.pits.size() == 0) {
            System.out.println("There are no bottomless pits in the world.");
        } else {
            System.out.println("There is a bottomless pit:");
            for (SquareObstacle pit : World.pits) {
                System.out.println("At " + pit.getBottomLeftX() + "," + pit.getBottomLeftY() + " to (" + (pit.getBottomLeftX() + 4) + "," + (pit.getBottomLeftY() + 5) + ")");
            }
        }
        if (World.mines.size() == 0) {
            System.out.println("There are no mines in the world.");
        } else {
            System.out.println("There is a mine:");
            for (Mine mine : World.mines) {
                System.out.println("At " + mine.getX() + "," + mine.getY());
            }
        }
        return true;
    }

    @Override
    public boolean runCommand(String instruction) {
        return false;
    }
}
