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

/** Tests the plip class   
 *  @authr FIXME
 */
public class TestPlip {

    /* Replace with the magic word given in lab.
     * If you are submitting early, just put in "early" */
    public static final String MAGIC_WORD = "";

    @Test
    public void testBasics() {
        Plip p = new Plip(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
        p.move();
        assertEquals(1.85, p.energy(), 0.01);
        p.move();
        assertEquals(1.70, p.energy(), 0.01);
        p.stay();
        assertEquals(1.90, p.energy(), 0.01);
        p.stay();
        assertEquals(2.00, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Plip parent = new Plip(1.5);
        Plip child = parent.replicate();
        assertEquals(1.5 / 2, child.energy(), 0.0001);
        assertEquals(1.5 / 2, parent.energy(), 0.0001);
        assertNotSame(parent, child);
    }

    @Test
    public void testChoose() {
        Plip p = new Plip(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseReplicate() {
        Plip p = new Plip(2.0); // Energy is greater than REPLICATE_MIN_ENERGY
        HashMap<Direction, Occupant> neighbors = new HashMap<Direction, Occupant>();
        neighbors.put(Direction.TOP, new Empty());
        neighbors.put(Direction.BOTTOM, new Impassible());
        neighbors.put(Direction.LEFT, new Impassible());
        neighbors.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(neighbors);
        Action expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseMoveWithCloruses() {
        Plip p = new Plip(0.9); // Energy is less than REPLICATE_MIN_ENERGY
        HashMap<Direction, Occupant> neighbors = new HashMap<Direction, Occupant>();
        neighbors.put(Direction.TOP, new Empty());
        neighbors.put(Direction.BOTTOM, new Clorus());
        neighbors.put(Direction.LEFT, new Impassible());
        neighbors.put(Direction.RIGHT, new Impassible());

        // Simulate a 50% probability of moving
        boolean shouldMove = Math.random() < 0.5;
        Action actual = p.chooseAction(neighbors);
        if (shouldMove) {
            assertEquals(Action.ActionType.MOVE, actual.type);
        } else {
            assertEquals(Action.ActionType.STAY, actual.type);
        }
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestPlip.class));
    }
} 
