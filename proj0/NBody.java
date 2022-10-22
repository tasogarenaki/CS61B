/**
 * NBody
 * Runs simulation of mutual forces between planets.
 * @author TC
 */
public class NBody {

    /* Simulate from time 0 to T. */
    private static double T;
    private static double dt;
    private static double radius;
    private static String filename;
    private static Planet[] planets;


    /**
     * Reads the radius of the universe in the file.
     * @param   filename    file that contains data
     * @return              the radius of the universe, SI unit
     */
    public static double readRadius(String filename) {
        In data = new In(filename);
        /* Skip the first value. */
        data.readInt();
        double radius = data.readDouble();
        return radius;
    }

    /**
     * Reads the planets containing data.
     * @param   filename    file that contains data
     * @return              an array of Planets in the file passed as parameter
     */
    public static Planet[] readPlanets(String filename) {
        In data = new In(filename);
        int numPlanets = data.readInt();
        /* Skip the second value. */
        data.readDouble();
        Planet[] planets = new Planet[numPlanets];
        for (int i = 0; i < numPlanets; i++) {
            double x = data.readDouble();
            double y = data.readDouble();
            double vx = data.readDouble();
            double vy = data.readDouble();
            double m = data.readDouble();
            String img = data.readString();
            planets[i] = new Planet(x, y, vx, vy, m, img);
        }
        return planets;
    }

    public static void main (String[] args) {
        /* Convert the Strings to doubles. */
        T = Double.parseDouble(args[0]);
        dt = Double.parseDouble(args[1]);
        filename = args[2];
        radius = readRadius(filename);
        planets = readPlanets(filename);

        /* Sets up the universe. */
        StdDraw.setScale(-radius, radius);
        /* Enables double buffering: prevent flickering in the animation. */
        StdDraw.enableDoubleBuffering();
        /* Clears the drawing window. */
        StdDraw.clear();
        /* Draw the background. */
        StdDraw.picture(0, 0, "images/starfield.jpg");
        /* Draw all of the planets. */
        for (Planet p : planets) {
            p.draw();
        }
        /* Shows the drawing to the screen. */
        StdDraw.show();

        for (double t = 0; t < T; t += dt) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];
            /* Calculate the net x and y forces for each planet */
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            /* update on each of the planets */
            for (int i = 0; i <planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }

            /* Create the Animation */
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.show();
            /* Pause the animation for 10 milliseconds. */
            StdDraw.pause(10);
        }

        /* Print out the final state of the universe */
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}

