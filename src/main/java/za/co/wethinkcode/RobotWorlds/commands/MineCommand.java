package za.co.wethinkcode.RobotWorlds.commands;

import za.co.wethinkcode.RobotWorlds.*;

import java.util.ArrayList;

public class MineCommand extends Command {

    public MineCommand() {
        super("mine");
    }

    @Override
    public boolean execute(Robot target) {
        String status = null;
        if (target.getMines() == 0) {
            status = "No mines available!";
        } else {
            World.mines.add(new Mine(target.getPosition()));
            status = "Mine Placed at " + target.getPosition().getX() + "," + target.getPosition().getY() + ".";
            System.out.println(target.getName() + " Placed a mine at " + target.getPosition().getX() + "," + target.getPosition().getY() + ".");
            ForwardCommand forwardCommand = new ForwardCommand("1");
            forwardCommand.execute(target);
            target.plantMine();
        }
        target.setStatus(status);
        return true;
    }
}





