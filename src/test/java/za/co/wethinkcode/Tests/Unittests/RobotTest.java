package za.co.wethinkcode.Tests.Unittests;
import org.json.JSONArray;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.RobotWorlds.Robot;
import static org.junit.jupiter.api.Assertions.*;

public class RobotTest {

    @Tag("Unittest")
    @Test
    public void testMakeSniper(){
        Robot sniper = new Robot("HAL" , new JSONArray().put("sniper"));
        assertEquals("sniper" , sniper.getKind());
        assertEquals(3 , sniper.getRepairTime());
        assertEquals(1 , sniper.getReloadTime());
        assertEquals(1 , sniper.getMaxShots());
        assertEquals(3 , sniper.getMaxShields());
        assertEquals(1 , sniper.getMaxShots());
        assertEquals(3 , sniper.getMaxShields());
        assertEquals(10 , sniper.getShotDistance());
        assertEquals(0 , sniper.getMaxMines());
    }

    @Tag("Unittest")
    @Test
    public void testMakeBomber() {
        Robot bomber = new Robot("HAL", new JSONArray().put("bomber"));
        assertEquals("bomber", bomber.getKind());
        assertEquals(5, bomber.getRepairTime());
        assertEquals(10, bomber.getReloadTime());
        assertEquals(0, bomber.getMaxShots());
        assertEquals(5, bomber.getMaxShields());
        assertEquals(0 , bomber.getMaxShots());
        assertEquals(5 , bomber.getMaxShields());
        assertEquals(0 , bomber.getShotDistance());
        assertEquals(2 , bomber.getMaxMines());
    }

    @Tag("Unittest")
    @Test
    public void testMakeShooter() {
        Robot shooter = new Robot("HAL", new JSONArray().put("shooter"));
        assertEquals("shooter", shooter.getKind());
        assertEquals(2, shooter.getRepairTime());
        assertEquals(5, shooter.getReloadTime());
        assertEquals(5, shooter.getMaxShots());
        assertEquals(2, shooter.getMaxShields());
        assertEquals(5 , shooter.getMaxShots());
        assertEquals(2 , shooter.getMaxShields());
        assertEquals(5 , shooter.getShotDistance());
        assertEquals(0 , shooter.getMaxMines());
    }

    @Tag("Unittest")
    @Test
    public void testName(){
        Robot shooter = new Robot("HAL", new JSONArray().put("shooter"));
        assertEquals("HAL", shooter.getName());
    }

    @Tag("Unittest")
    @Test
    public void testDirection(){
        Robot shooter = new Robot("HAL", new JSONArray().put("shooter"));
        assertEquals("North" , shooter.getDirectionString());
    }

    @Tag("Unittest")
    @Test
    public void testConvertSecondsToMilliseconds(){
        Robot shooter = new Robot("HAL", new JSONArray().put("shooter"));
        shooter.convertSecondsToMilliseconds(shooter.getReloadTime());
    }


}
