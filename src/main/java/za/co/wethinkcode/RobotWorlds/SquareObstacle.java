package za.co.wethinkcode.RobotWorlds;

public class SquareObstacle {

    private int x;
    private int y;

    /**
     * sets bottom left coordinates of the obstacle
     * @param x bottom left x coordinate
     * @param y bottom left y coordinate
     */
    public SquareObstacle(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**Function returns bottom left x-value of obstacle
     *
     * @return int x-vlaue of bottom left corner
     */
    public int getBottomLeftX() {


        return this.x;
    }

    /**Function returns bottom left y-value of obstacle
     *
     * @return int y-vlaue of bottom left corner
     */
    public int getBottomLeftY() {


        return this.y;
    }

    /**Function returns the size of a side of the obstacle
     *
     * @return int value of the side
     */

    public int getSize() {

        return 5;
    }

    /**Function checks to see if position is within the area of
     * the SquareObstacle
     *
     * @param position the Position value to check
     * @return boolean for if the Position is in the area
     */
    public boolean blocksPosition(Position position) {
        if(x <= position.getX() && position.getX() <= x + 4 &&
            y <= position.getY() && position.getY() <= y + 4){
            return true;
        }
        else{
            return false;
        }
    }

    /**Function check to see if there is a SquareObstacle between 2 Positions
     *
     * @param a the Position object that contains the start
     * @param b the Position object that contains the end
     * @return boolean if the path is blocked
     */
    public boolean blocksPath(Position a, Position b) {

        if(passObstacle(a.getX(), b.getX(), x, x + 4) && a.getY() == b.getY() &&
                a.getY() >= y && a.getY() <= y + 4) {
            return true;
        }
        else if(passObstacle(a.getY(), b.getY(), y, y + 4) && a.getX() == b.getX() &&
                a.getX() >= x && a.getX() <= x + 4){
            return true;
        }
        else{
            return false;
        }
    }

    /**Function checks if one line passes another
     *
     * @param startPath Int containing the start of the first line
     * @param endPath Int containing the end of the first line
     * @param startObs Int containing the start of the second line
     * @param endObs Int containing the end of the second line
     * @return boolean if the line passes the other line
     */

    public boolean passObstacle(int startPath, int endPath, int startObs, int endObs){

        return (startPath <= startObs && startObs <= endPath ||
                startPath <= endObs && endObs <= endPath ||
                startObs <= startPath && startPath <= endObs ||
                startObs <= endPath && endPath <= endObs ||
                endPath <= endObs && startObs <= startPath);
    }
}
