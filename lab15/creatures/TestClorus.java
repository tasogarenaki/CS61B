package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the Clorus class
 *  @authr Terry
 */
public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.93, c.energy(), 0.01);
        c.stay();
        assertEquals(1.92, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus parent = new Clorus(1.5);
        Clorus child = parent.replicate();
        assertEquals(1.5 / 2, child.energy(), 0.0001);
        assertEquals(1.5 / 2, parent.energy(), 0.0001);
        assertNotSame(parent, child);
    }

    @Test
    public void testChooseStayWhenNoEmptySpaces() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> neighbors = new HashMap<Direction, Occupant>();
        neighbors.put(Direction.TOP, new Plip());
        neighbors.put(Direction.BOTTOM, new Plip());
        neighbors.put(Direction.LEFT, new Plip());
        neighbors.put(Direction.RIGHT, new Plip());

        Action actual = c.chooseAction(neighbors);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseAttackPlip() {
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> neighbors = new HashMap<Direction, Occupant>();
        neighbors.put(Direction.TOP, new Empty());
        neighbors.put(Direction.BOTTOM, new Plip());
        neighbors.put(Direction.LEFT, new Empty());
        neighbors.put(Direction.RIGHT, new Empty());

        Action actual = c.chooseAction(neighbors);
        Action expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseReplicate() {
        Clorus c = new Clorus(1.0); // Energy is greater than or equal to 1
        HashMap<Direction, Occupant> neighbors = new HashMap<Direction, Occupant>();
        neighbors.put(Direction.TOP, new Empty());
        neighbors.put(Direction.BOTTOM, new Impassible());
        neighbors.put(Direction.LEFT, new Impassible());
        neighbors.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(neighbors);
        Action expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseMoveToRandomEmptySpace() {
        Clorus c = new Clorus(0.9); // Energy is less than 1
        HashMap<Direction, Occupant> neighbors = new HashMap<Direction, Occupant>();
        neighbors.put(Direction.TOP, new Empty());
        neighbors.put(Direction.BOTTOM, new Impassible());
        neighbors.put(Direction.LEFT, new Impassible());
        neighbors.put(Direction.RIGHT, new Empty());

        Action actual = c.chooseAction(neighbors);
        Action expected = new Action(Action.ActionType.MOVE, Direction.TOP);
        assertEquals(expected, actual);
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestPlip.class));
    }
}
