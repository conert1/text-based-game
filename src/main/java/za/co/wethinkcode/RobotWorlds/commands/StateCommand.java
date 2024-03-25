package za.co.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.co.wethinkcode.RobotWorlds.Robot;

public class StateCommand extends Command{

    public static StringBuilder string;
    public boolean execute(Robot target){
        string = new StringBuilder();
        string.append("\nPosition  : [").append(target.getPosition().getX()).append(",").append(target.getPosition().getY()).append("]\n").append("Direction : ").append(target.getCurrentDirection().toString()).append("\nShields   : ").append(target.getShields()).append("\\").append(target.getMaxShields()).append("\nShots     : ").append(target.getShots()).append("\\").append(target.getMaxShots()).append("\nMines     : ").append(target.getMines()).append("\\").append(target.getMaxMines());
        target.setStatus(String.valueOf(string));
        return true;
    }



    public StateCommand() {
        super("state");
    }
}
