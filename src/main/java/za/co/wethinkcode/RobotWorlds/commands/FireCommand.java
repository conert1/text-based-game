package za.co.wethinkcode.RobotWorlds.commands;

import org.json.JSONObject;
//import org.turtle.*;

import za.co.wethinkcode.RobotWorlds.*;

import java.util.ArrayList;

public class FireCommand extends Command {

    Robot gotShot;
    Position bulletPos;
    public FireCommand() {
        super("fire");
    }


    public boolean execute(Robot target) {
        if (canFire(target)){
            getBulletPos(target);
            if (hitPlayer(target)){
                gotShot.setGotShotTrue();
                gotShot.shotByBullet();
                gotShot.setGotShotMessage(target.getName());
                target.setStatus("You shot " + gotShot.getName());
                System.out.println(target.getName() + " shot " + gotShot.getName()+".");
            } else {
                target.setStatus("You didn't hit a thing.");
            }
            target.fire();
        } else {
            target.setStatus("No bullets left.");
        }
        return true;
    }


    private void getBulletPos(Robot target) {
        switch (target.getCurrentDirection()) {
            case NORTH:
                bulletPos = new Position(target.getPosition().getX(), target.getPosition().getY() + target.getShotDistance());
                break;
            case SOUTH:
                bulletPos = new Position(target.getPosition().getX(), target.getPosition().getY() - target.getShotDistance());
                break;
            case EAST:
                bulletPos = new Position(target.getPosition().getX() + target.getShotDistance(), target.getPosition().getY());
                break;
            case WEST:
                bulletPos = new Position(target.getPosition().getX() - target.getShotDistance(), target.getPosition().getY());
        }
    }

    public boolean hitPlayer(Robot target) {
        for (Robot robot : World.robots) {
            if (sameX(robot) && inYRange(target , robot)){
                gotShot = robot;
                return true;
            } else if (sameY(robot) && inXRange(target , robot)) {
                gotShot = robot;
                return true;
            }
        }
        return false;
    }

    public boolean canFire(Robot target){
        return (target.getShots() > 0);
    }

    private boolean sameX(Robot robot) {
        return robot.getPosition().getX() == bulletPos.getX();
    }

    private boolean sameY(Robot robot) {
        return robot.getPosition().getY() == bulletPos.getY();
    }

    private boolean inXRange(Robot target , Robot robot) {
        return (robot.getPosition().getX() > target.getPosition().getX() && robot.getPosition().getY() <= bulletPos.getX()) || (robot.getPosition().getX() < target.getPosition().getX() && robot.getPosition().getY() >= bulletPos.getX());
    }

    private boolean inYRange(Robot target , Robot robot) {
        return (robot.getPosition().getY() > target.getPosition().getY() && robot.getPosition().getY() <= bulletPos.getY()) || (robot.getPosition().getY() < target.getPosition().getY() && robot.getPosition().getY() >= bulletPos.getY());
    }
}




