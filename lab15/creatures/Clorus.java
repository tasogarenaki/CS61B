package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.*;
import java.util.Map;
import java.util.List;

public class Clorus extends Creature {
    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    private static final double REPLICATE_MIN_ENERGY = 1;
    private static final double MOVE_ENERGY = -0.03;
    private static final double STAY_ENERGY = -0.01;

    /** creates plip with energy equal to E. */
    public Clorus(double e) {
        // TODO
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    /** creates a plip with energy equal to 1. */
    public Clorus() {
        this(1);
    }

    /** Should return a color with red = 34, blue = 231, and green = 0. */
    @Override
    public Color color() {
        return color(r, g, b);
    }

    /**  When attacks another creature, it gains the other creature's energy. */
    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    /** Clorus should lose 0.03 units of energy when moving. */
    @Override
    public void move() {
        energy += MOVE_ENERGY;
    }


    /** Clorus should lose 0.01 energy when staying. */
    @Override
    public void stay() {
        energy += STAY_ENERGY;
    }

    /** Clorus and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby Clorus.
     */
    @Override
    public Clorus replicate() {
        double keep = energy / 2;
        energy = keep;
        return new Clorus(keep);
    }

    /** Clorus take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     *  3. Otherwise, if energy >= 1, REPLICATE to any empty square.
     *  4. Otherwise, MOVE to a random empty square.
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (empties.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else if (!plips.isEmpty()) {
            Direction attackDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, attackDir);
        } else if (energy() >= REPLICATE_MIN_ENERGY) {
            Direction replicateDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, replicateDir);
        } else {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.MOVE, moveDir);
        }
    }
}
