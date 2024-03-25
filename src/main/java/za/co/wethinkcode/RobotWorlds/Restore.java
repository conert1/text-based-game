package za.co.wethinkcode.RobotWorlds;

import za.co.wethinkcode.RobotWorlds.ServerCommands.RestoreCommand;

import java.sql.*;

public class Restore {
//    obstacles pits mines
    public void ClearAll(){
        World.clearPits();
        World.clearMines();
        World.clearObstacles();
    }

    public void RestoreWorld(){

            String url = "jdbc:sqlite:" + RestoreCommand.databaseName() + ".sqlite";
            try (Connection conn = DriverManager.getConnection(url);
                 Statement stmt = conn.createStatement()) {
                try {
                    ResultSet rs = stmt.executeQuery("select * from " + RestoreCommand.databaseName());
                    while (rs.next()) {
                        String obstacles = rs.getString("obstacles");
                        obstacles = obstacles.replace("(", "");
                        obstacles = obstacles.replace(")", "");
                        obstacles = obstacles.replace(",", " ");
                        int firstPos = obstacles.indexOf(" ");
                        int Obstacle1 = Integer.parseInt(obstacles.substring(0, firstPos));
                        int Obstacle2 = Integer.parseInt(obstacles.substring(obstacles.lastIndexOf(" ")+1));
                        World.obstacles.add(new SquareObstacle(Obstacle1 , Obstacle2));

                        String pits = rs.getString("pits");
                        if (!pits.equals("nothing")){
                            pits = pits.replace("(", "");
                            pits = pits.replace(")", "");
                            pits = pits.replace(",", " ");
                            int pitFirstPos = pits.indexOf(" ");
                            int pitObstacle1 = Integer.parseInt(pits.substring(0, pitFirstPos));
                            int pitObstacle2 = Integer.parseInt(pits.substring(pits.lastIndexOf(" ")+1));
                            World.pits.add(new SquareObstacle(pitObstacle1 , pitObstacle2));
                        }
                        String mine = rs.getString("mine");
                        if (!mine.equals("nothing")){
                            mine = mine.replace("(", "");
                            mine = mine.replace(")", "");
                            mine = mine.replace(",", " ");

                            int mineFirstPos = mine.indexOf(" ");
                            int mineObstacle1 = Integer.parseInt(mine.substring(0, mineFirstPos));
                            int mineObstacle2 = Integer.parseInt(mine.substring(mine.lastIndexOf(" ")+1));
                            World.mines.add(new Mine(new Position(mineObstacle1 , mineObstacle2)));
                        }
                    }
                } catch (SQLException e ) {
                    throw new Error("Problem", e);
                }

            } catch (SQLException e) {
                throw new Error("Problem", e);
            }
        }
//    }
}
