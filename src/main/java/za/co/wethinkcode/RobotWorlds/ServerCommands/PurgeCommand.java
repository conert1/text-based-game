package za.co.wethinkcode.RobotWorlds.ServerCommands;

import za.co.wethinkcode.RobotWorlds.World;

public class PurgeCommand extends ServerCommand{

    static String name;
    public PurgeCommand() {
        super("purge");
    }

    @Override
    public boolean runCommand() {
        if (World.robots.size() == 0){
            System.out.println("No robots to purge in the world.");
        }else
            for (int i = 0; i < World.robots.size() ; i ++){
            if (World.robots.get(i).getName().toLowerCase().equalsIgnoreCase(name)){
                World.robots.get(i).setPurged();
                System.out.println(World.robots.get(i) + " has been purged.");
                World.robots.get(i).setDead();
            } else
                System.out.println("\"" +name + "\" does not exist in the world.");
        }
        return true;
    }

    @Override
    public boolean runCommand(String instruction) {
        return false;
    }

    public static void setName(String args){
        name = args;
    }
}
