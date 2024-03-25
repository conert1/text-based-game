package za.co.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
import za.co.wethinkcode.RobotWorlds.Robot;

import java.util.concurrent.TimeUnit;

public class RepairCommand extends Command{

    public boolean execute(Robot target){
        if (canRepair(target)){
            try {
                Thread.sleep(target.convertSecondsToMilliseconds(target.getRepairTime()));
                target.setShields();
                target.setStatus("Successfully Repaired.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else
            target.setStatus("Cannot Repair. Shields at 100%");
        return true;
    }

    public RepairCommand() {
        super("repair");
    }

    public Boolean canRepair(Robot target){
        return target.getShields() != target.getMaxShields();
    }
}
