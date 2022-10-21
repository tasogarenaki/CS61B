
/**
 * Planet
 * Representation of a planet, its position, velocity, and mass.
 * @author TC
 */

public class Planet {

    /* current x and y position */
    public double xxPos;
    public double yyPos;

    /* current velocity in the x and y direction */
    public double xxVel;
    public double yyVel;

    /* mass */
    public double mass;

    /* name of the file that corresponds to the image that depicts the planet */
    public String imgFileName;

    /**
     * the gravitational constant G, cannot invoke by outside of class
     * declare any constants as a `static final` variable
     * */
    private static final double G = 6.67e-11;

    /* initialize an instance of the Planet class */
    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /* take in a Planet object and initialize an identical Planet object (copy) */
    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    /**
     * calculate the distance between two plants
     * @param   p    the other planet
     * @return       distance between plant p and this planet
     */
    public double calcDistance(Planet p) {
        double dx = this.xxPos - p.xxPos;
        double dy = this.yyPos - p.yyPos;
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    /**
     * calculate the force exerted on this planet by another planet
     * @param   p     the planet whose force is exerted on this planet
     * @return        the force exerted on this planet by planet p
     */
    public double calcForceExertedBy(Planet p) {
        double r = this.calcDistance(p);
        return G * this.mass * p.mass / Math.pow(r, 2);
    }

    /**
     * calculate the force exerted in the x direction
     * @param   p      the planet whose force is exerted on this planet
     * @return         the force exerted on this planet by planet p in x direction
     */
    public double calcForceExertedByX(Planet p) {
        double f = calcForceExertedBy(p);
        double r = calcDistance(p);
        double dx = p.xxPos - this.xxPos;
        return f * dx / r;
    }

    /**
     * calculate the force exerted in the y direction
     * @param   p      the planet whose force is exerted on this planet
     * @return         the force exerted on this planet by planet p in y direction
     */
    public double calcForceExertedByY(Planet p) {
        double f = calcForceExertedBy(p);
        double r = calcDistance(p);
        double dx = p.yyPos - this.yyPos;
        return f * dx / r;
    }

    /**
     * calculate the net force exerted in the x direction
     * @param   allPlanets  array of planets
     * @return              the net x force exerted by all planes
     *
     */
    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double netFx = 0;
        for (Planet p : allPlanets) {
            // planets cannot exert gravitational forces on themselves
            if (p == this) continue;
            netFx += calcForceExertedByX(p);
        }
        return netFx;
    }

    /**
     * calculate the net force exerted in the y direction
     * @param   allPlanets  array of planets
     * @return              the net y force exerted by all planes
     *
     */
    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double netFy = 0;
        for (Planet p : allPlanets) {
            if (p == this) continue;
            netFy += calcForceExertedByY(p);
        }
        return netFy;
    }

    /**
     * calculates and updates the change in velocity and position,
     * based on the net exerted force on this planet.
     * @param   dt  the increment of time used for calc. the change in velocity
     * @param   fx  net force exerted on this planet in x direction
     * @param   fy  net force exerted on this planet in y direction
     */
    public void update(double dt, double fx, double fy) {
        double ax = fx / mass;
        double ay = fy / mass;
        xxVel += ax * dt;
        yyVel += ay * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

}
