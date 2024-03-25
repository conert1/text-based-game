package za.co.wethinkcode.RobotWorlds;

public class Position {
    private final int x;
    private final int y;

    /**
     * Position constructor that sets the x and y coordinate of the robot
     * @param x coordinate of the robot
     * @param y coordinate of the robot
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /**
     * returns the x coordinate of the robot
     * @return (int) x- coordinate
     */
    public int getX() {
        return x;
    }


    /**
     * returns the y coordinate of the robot
     * @return (int) xy- coordinate
     */
    public int getY() {
        return y;
    }

    /**Function checks to see if 2 position objects hve the same x and y position
     *
     * @param o the Object the position is being compared to
     * @return boolean If they are equal
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }


    /**Function checks to see if the Position is withing the border of the maze
     *
     * @param topLeft the Position of the top left point of the maze
     * @param bottomRight the Position of the bottom right point of the maze
     * @return boolean If the Position is within the area between the 2 points
     */
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    public static boolean collideWithRobot(Position robotPosition){
        for (int i = 0 ; i < World.robots.size();i ++) {
            if ((robotPosition.getX() == World.robots.get(i).getPosition().getX()) && (robotPosition.getY() == World.robots.get(i).getPosition().getY())){
                World.robots.get(i).collidedWithRobot();
            return true;
            }
        }
        return false;
    }

    public static boolean inRobotPath(Position currentRobotPosition , Position newRobotPosition) {
        for (int i = 0 ; i < World.robots.size() ; i ++){
            if (sameXAsRobot(currentRobotPosition , newRobotPosition , World.robots.get(i)) && inYRangeOfRobot(currentRobotPosition , newRobotPosition , World.robots.get(i))) {
                World.robots.get(i).collidedWithRobot();
                return true;
            } else if (sameYAsRobot(currentRobotPosition , newRobotPosition , World.robots.get(i)) && inXRangeOfRobot(currentRobotPosition , newRobotPosition , World.robots.get(i))) {
                World.robots.get(i).collidedWithRobot();
                return true;
            }
        }
        return false;
    }

    private static boolean sameXAsRobot(Position robotPosition, Position newRobotPosition, Robot robot) {
        return robotPosition.getX() == robot.getPosition().getX() && robot.getPosition().getX() == newRobotPosition.getX();
    }

    private static boolean sameYAsRobot(Position robotPosition ,Position newRobotPosition , Robot robot) {
        return robotPosition.getY() == robot.getPosition().getY() && robot.getPosition().getY() == newRobotPosition.getY();
    }

    private static boolean inYRangeOfRobot(Position robotPosition ,Position newRobotPosition , Robot robot) {
        return (robotPosition.getY() < robot.getPosition().getY() && newRobotPosition.getY() > robot.getPosition().getY()) || (robotPosition.getY() > robot.getPosition().getY() && newRobotPosition.getY() < robot.getPosition().getY());
    }

    private static boolean inXRangeOfRobot(Position robotPosition ,Position newRobotPosition , Robot robot){
        return (robotPosition.getX() < robot.getPosition().getX() && newRobotPosition.getX() > robot.getPosition().getX()) || (robotPosition.getX() > robot.getPosition().getX() && newRobotPosition.getX() < robot.getPosition().getX());
    }
}
