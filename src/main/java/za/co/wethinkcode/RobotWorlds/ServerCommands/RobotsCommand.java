package za.co.wethinkcode.RobotWorlds.ServerCommands;

import za.co.wethinkcode.RobotWorlds.Robot;
import za.co.wethinkcode.RobotWorlds.World;


public class RobotsCommand extends ServerCommand {


    public RobotsCommand(){
        super("robots");
    }

    @Override
    public boolean runCommand() {
        for (Robot robot : World.robots){
            System.out.println(robot.getName() + " :\n  position = " + robot.getPosition().getX() + "," + robot.getPosition().getY() + "\n  shields/max shields = " + robot.getShields() + "/" + robot.getMaxShields() + "\n  shots/max shots = " + robot.getShots() + "/" + robot.getMaxShots());
        }
        return true;
    }

    @Override
    public boolean runCommand(String instruction) {
        return false;
    }
}
