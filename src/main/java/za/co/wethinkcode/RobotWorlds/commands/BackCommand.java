package za.co.wethinkcode.RobotWorlds.commands;
import org.json.JSONObject;
//import org.turtle.*;
import za.co.wethinkcode.RobotWorlds.Position;
import za.co.wethinkcode.RobotWorlds.Robot;


public class BackCommand extends Command {

    /**Function moves to robot backwards by calling the updatePosition function with Argument
     * nrSteps as a negative integer
     *
     * @param target The Robot object
     * @return boolean if the program should continue
     */
    @Override
    public boolean execute(Robot target) {
        try {
            int nrSteps = Integer.parseInt(getArgument());
            String status = null;
            Robot.Conditions condition = target.updatePosition(-nrSteps);
            switch (condition){
                case SUCCESS:
                    status = "Moved back by "+nrSteps + " steps.";
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
                        status = "At the SOUTH edge";
                    } else if (target.getCurrentDirection() == Robot.Direction.SOUTH) {
                        status = "AT the NORTH edge";
                    } else if (target.getCurrentDirection() == Robot.Direction.EAST) {
                        status = "At the WEST edge";
                    } else if (target.getCurrentDirection() == Robot.Direction.WEST) {
                        status = "At the EAST edge";
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

        } catch (NumberFormatException e){
            target.setStatus("Invalid Command");
        }
        return true;
    }


    public BackCommand(String argument) {
        super("back", argument);
    }
}
