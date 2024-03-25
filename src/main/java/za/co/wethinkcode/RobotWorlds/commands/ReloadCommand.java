package za.co.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.co.wethinkcode.RobotWorlds.Robot;

import java.util.concurrent.TimeUnit;

public class ReloadCommand extends Command{

    public boolean execute(Robot target){
        if (canReload(target)) {
            try {
                Thread.sleep(target.convertSecondsToMilliseconds(target.getReloadTime()));
                target.setShots();
                target.setMines();
                target.setStatus("Successfully Reloaded.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else
            target.setStatus("Cannot Reload.");
        return true;
    }

    public ReloadCommand() {
            super("reload");
        }


    public Boolean canReload(Robot target){
        /*Returns true if the robot can reload And false if not*/
        if (target.getMines() < target.getMaxMines()) {
            return true;
        }
        return target.getShots() < target.getMaxShots();
    }
}
