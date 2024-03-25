package za.co.wethinkcode.RobotWorlds;


import za.co.wethinkcode.RobotWorlds.ServerCommands.SaveCommand;

import java.sql.*;
import java.util.ArrayList;


import static javax.swing.plaf.synth.Region.SEPARATOR;

public class DbConnect {

    static String tableName = "jdbc:sqlite:" + SaveCommand.tableName() + ".sqlite" ;

    public static final String DISK_DB_URL = "jdbc:sqlite:";


    public static void createDataBase() {
        try( final Connection connection = DriverManager.getConnection( tableName ) ){
            System.out.println( "Connected to database " );
            createTable( connection );
        }catch( SQLException e ){
            System.err.println( e.getMessage() );
        }
    }

    private static void createTable (Connection connection ){
        try (final Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE " + SaveCommand.tableName() + "( size, obstacles, pits, mine )");
            System.out.println("Success creating test table!");
            insertData(connection);
            System.out.println("data created!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void insertData ( final Connection connection ) throws SQLException {
        String edges;
        String mines;
        String obstacles;
        String pits;
        int i = 0;
        while (true) {
            if (i >= World.obstacleNum && i >= World.mines.size() && i >= World.pitNum)
                break;
            try {
                edges = World.edges.get(i).getX() + "x" + World.edges.get(i).getY();
            } catch (Exception e) {
                edges = null;
            }
            try {
                obstacles = "(" + (World.obstacles.get(i).getBottomLeftX()) + "," + (World.obstacles.get(i).getBottomLeftY()) + ")";
            } catch (Exception e) {
                obstacles = "nothing";
            }
            try {
                pits = "(" + World.pits.get(i).getBottomLeftX() + "," + World.pits.get(i).getBottomLeftY() + ")";
            } catch (Exception e) {
                pits = "nothing";
            }
            try {
                mines = "(" + World.mines.get(i).getX() + "," + World.mines.get(i).getY() + ")";
            } catch (Exception e) {
                mines = "nothing";
            }
            i++;
            String toInsert = "INSERT INTO " + SaveCommand.tableName() + "(size, obstacles, pits, mine) VALUES (?,?,?,?)";
            try (final Statement stmt = connection.createStatement()) {
                boolean gotAResultSet = stmt.execute(
                        toInsert);

                PreparedStatement pstmt = connection.prepareStatement(toInsert
                );
                pstmt.setString(1, edges);
                pstmt.setString(2, obstacles);
                pstmt.setString(3, pits);
                pstmt.setString(4, mines);
                    pstmt.executeUpdate();
                    if (gotAResultSet) {
                        throw new RuntimeException("Unexpectedly got a SQL resultset.");
                    } else {
                        final int updateCount = stmt.getUpdateCount();
                        if (updateCount == 1) {
                            System.out.println("1 row INSERTED into " + SaveCommand.tableName());
                        } else {
                            throw new RuntimeException("Expected 1 row to be inserted, but got " + updateCount);
                        }
                    }
            }
        }
        String toInsert = "DELETE FROM " + SaveCommand.tableName() + "\n" +
                "            WHERE\n" +
                "        (size IS NULL OR size  = '')\n" +
                "        AND (pits IS NULL OR pits = '')\n" +
                "        AND (obstacles IS NULL OR obstacles = '')";
        try (final Statement stmt = connection.createStatement()) {
            boolean gotAResultSet = stmt.execute(
                    toInsert);

            PreparedStatement pstmt = connection.prepareStatement(toInsert
            );
            pstmt.executeUpdate();
        }
    }

//    public static void oo(){
//        public static void main(String[] args) {
//
//        String sql = "SELECT id, username FROM users WHERE id = ?";
//        String url = "jdbc:sqlite:robo.sqlite";
//        try (Connection conn = DriverManager.getConnection(url);
//             Statement stmt = conn.createStatement()) {
//
//            try {
//                ResultSet rs = stmt.executeQuery("select * from robo");
//                while (rs.next()) {
//                    String name = rs.getString("obstacles");
//                    name = name.replace("(", "");
//                    name = name.replace(")", "");
//                    name = name.replace(",", " ");
////                    System.out.println(name);
//                    int firstPos = name.indexOf(" ");
//                    int Obstacle1 = Integer.parseInt(name.substring(0, firstPos));
//                    int Obstacle2 = Integer.parseInt(name.substring(name.lastIndexOf(" ")+1));
//                    World ob = new World();
//                    ob.setObstacle(Obstacle1, Obstacle2);
//                    if(Obstacle2 > 0 && Obstacle1 >0){
//                    World.obstacles.add(Obstacles.getObstacle(Obstacle1, Obstacle2));
//                    System.out.println("added obstacles are  "+ name);}
//                }
//            } catch (SQLException e ) {
//                throw new Error("Problem", e);
//            }
//
//        } catch (SQLException e) {
//            throw new Error("Problem", e);
//        }
//    }
    }






//    public void readData( final Connection connection )
//            throws SQLException {
//        try (final Statement stmt = connection.createStatement()) {
//            boolean gotAResultSet = stmt.execute(
//                    "SELECT p.name productname, i.type, i.name ingredname, r.qty, r.units "
//                            + "FROM products p, recipe_quantities r, ingredients i "
//                            + "WHERE "
//                            + "        productname = 'Buffalo Bay Blonde'"
//                            + "    AND p.id = r.product_id "
//                            + "    AND r.ingredient_id = i.id"
//            );
//            if (!gotAResultSet) {
//                throw new RuntimeException("Expected a SQL resultset, but we got an update count instead!");
//            }
//            try (ResultSet results = stmt.getResultSet()) {
//                int rowNo = 1;
//                while (results.next()) {
//                    final String productName = results.getString("productname");
//                    final String ingredType = results.getString("type");
//                    final String ingredName = results.getString("ingredname");
//                    final int qty = results.getInt("qty");
//                    final String units = results.getString("units");
//
//                    final StringBuilder b = new StringBuilder("Row ").append(rowNo).append(SEPARATOR)
//                            .append(productName).append(SEPARATOR)
//                            .append(ingredType).append(SEPARATOR)
//                            .append(ingredName).append(SEPARATOR)
//                            .append(qty).append(SEPARATOR)
//                            .append(units);
//                    System.out.println(b.toString());
//                }
//            }
//        }
//    }
