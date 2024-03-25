package za.co.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
//import org.turtle.*;
import za.co.wethinkcode.RobotWorlds.Position;
import za.co.wethinkcode.RobotWorlds.Robot;

public class ForwardCommand extends Command {

    @Override
    public boolean execute(Robot target) {
        /**Function moves to robot forward by calling the updatePosition function with Argument
         * nrSteps as a positive integer
         *
         * @param target The Robot object
         * @return boolean if the program should continue
         */
        try {
            int nrSteps = Integer.parseInt(getArgument());
            String status = null;
            Robot.Conditions condition = target.updatePosition(nrSteps);
            switch (condition) {
                case SUCCESS:
                    status = "Moved forward by " + nrSteps + " steps.";
                    break;
                case FAILED_OBSTACLE_DETECTED:
                    status = "Unable to move due to there being an obstacle in my way.";
                    break;
                case IN_MINE:
                    System.out.println(target.getName() + " stepped in Mine.");
                    target.inMine();
                    status = "Stepped in mine";
                    break;
                case FAILED_OUTSIDE_ROBOT_WORLD:
                    if (target.getCurrentDirection() == Robot.Direction.NORTH) {
                        status = "At the NORTH edge";
                    } else if (target.getCurrentDirection() == Robot.Direction.SOUTH) {
                        status = "AT the SOUTH edge";
                    } else if (target.getCurrentDirection() == Robot.Direction.EAST) {
                        status = "At the EAST edge";
                    } else if (target.getCurrentDirection() == Robot.Direction.WEST) {
                        status = "At the WEST edge";
                    }
                    break;
                case COLLIDED_WITH_ROBOT:
                    target.collidedWithRobot();
                    status = "Collided with robot!";
                    break;
                case IN_PIT:
                    System.out.println(getName() + " Fell in a bottomless pit.");
                    status = "You Fell in a pit!";
                    target.setDead();
            }

            target.setStatus(status);
        } catch ( NumberFormatException e){
            target.setStatus("Invalid Command.");
        }
        return true;
    }


    public ForwardCommand(String argument) {
        super("forward", argument);
    }
}
