package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
            "You got this!", "You're a star!", "Go Bears!",
            "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        Random rand = new Random(seed);
        MemoryGame game = new MemoryGame(40, 40, rand);
        game.startGame();
    }

    public MemoryGame(int width, int height, Random rand) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        this.rand = rand;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    /**
     * Generates a random String of lowercase letters.
     * @param n length of the String to generate.
     * @return  a String of random letters from the CHARACTERS String.
     */
    public String generateRandomString(int n) {
        String s = new String();
        for (int i = 0; i < n; i++) {
            s += CHARACTERS[rand.nextInt(CHARACTERS.length)];
        }
        return s;
    }

    /**
     * Displays a String centered on the window and the status menu of the game.
     * @param s is the String to be displayed.
     */
    public void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 30));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(width / 2, height / 2, s);
        if (!gameOver) {
            StdDraw.setFont(new Font("Arial", Font.BOLD, 20));
            StdDraw.textLeft(1, height - 1, "Round: " + round);
            if (playerTurn) {
                StdDraw.text(width / 2, height - 1, "Type!");
            } else {
                StdDraw.text(width / 2, height - 1, "Watch!");
            }
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.line(0, height - 2, width, height - 2);
        }
        StdDraw.show();
    }

    /**
     * Displays each character in letters, blanking the screen between characters.
     * @param letters String whose characters are to be displayed.
     */
    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(Character.toString(letters.charAt(i)));
            StdDraw.pause(1000);
            StdDraw.clear(Color.BLACK);
            StdDraw.pause(500);
        }
    }

    /**
     * Read n letters of player input
     * @param n number of letters to read
     * @return the letters
     */
    public String solicitNCharsInput(int n) {
        playerTurn = true;
        StringBuilder letters = new StringBuilder();
        while (n != 0) {
            if (StdDraw.hasNextKeyTyped()) {
                letters.append(StdDraw.nextKeyTyped());
                n--;
            }
            drawFrame(letters.toString());
        }
        StdDraw.pause(500);
        return letters.toString();
    }

    /**
     * Plays the memory game.
     */
    public void startGame() {
        round = 1;
        while (!gameOver) {
            playerTurn = false;
            drawFrame("Round: " + round);
            StdDraw.pause(1000);
            String random = generateRandomString(round);
            flashSequence(random);
            String answer = solicitNCharsInput(round);
            if (!answer.equals(random)) {
                drawFrame("Game Over! You made it to round: " + round);
                gameOver = true;
            }
            round++;
        }
    }
}