package byog.Core;

import byog.TileEngine.TETile;
import byog.lab5.HexWorld;

import java.util.List;
import java.io.Serializable;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * GameState
 * To save and load, to disk, the state of a game.
 */
public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    /* File to save the game state. */
    private static final String PATH = "./game.txt";

    //public MapGenerator.Coordinate player;

    public TETile[][] world;
    public Random rand;

    public GameState() {
        world = null;
        rand = null;
        //player = null;
    }

    public GameState(TETile[][] world, Random rand) {
        this.world = world;
        this.rand = rand;
    }

    /**
     * Reads the file that contains the game state.
     * @return  a GameState instance.
     */
    public static GameState loadWorld() {
        File f = new File(PATH);
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                GameState loadWorld = (GameState) os.readObject();
                os.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
        /* In the case no World has been saved yet, we return a new one. */
        return null;
    }


    /**
     * Saves the game state.
     * @param gameState the instance of GameState to be saved.
     */
    public static void saveWorld(GameState gameState) {
        //gameState.world = null;     // TODO: potential bug
        File f = new File(PATH);
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(gameState);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    /*
    public void setState(TETile[][] world, MapGenerator.Coordinate player) {
        this.world = world;
        this.player = player;
    }

    public void setWorld(TETile[][] world, MapGenerator.Coordinate player) {
        this.world = world;
        this.player = player;
    }
     */

}
