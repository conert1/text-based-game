package za.co.wethinkcode.RobotWorlds.configuration;

import za.co.wethinkcode.RobotWorlds.Position;

public class Configuration {

    private final Position worldSize = new Position(-350, 350);
    private int health = 5;

    /**
     * get world size
     * @return world max and min coordinate
     */
    public Position getWorldSize() {

        return worldSize;
    }

    /**
     * reduce health
     */
    public void reduceHealth() {

        this.health -= 1;

        if (this.health < 0) {
            this.health = 0;
        }

    }

    /**
     * get robot health
     * @return health status
     */
    public int getHealth() {

        return this.health;
    }

    /**
     * set health
     * @param health status
     */
    public void setHealth(int health) {

        this.health = health;
    }
}