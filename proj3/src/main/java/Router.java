import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Iterator;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        Node start = g.getNode(g.closest(stlon, stlat));
        Node dest = g.getNode(g.closest(destlon, destlat));
        Map<Long, SearchNode> visited = new HashMap<>();             // store the pre node in the shortest path
        SearchNode current = new SearchNode(start, null, dest);      // lowest f(n) for all nodes from start to destination
        PriorityQueue<SearchNode> fringe = new PriorityQueue<>();    // store nodes with their f(n)
        fringe.add(current);

        /* A* algorithm */
        while(!fringe.isEmpty()) {
            current = fringe.remove();
            Node currentNode = current.node;
            visited.put(currentNode.id, current);
            /* Reach the end node. */
            if (currentNode.equals(dest)) {
                break;
            }

            for (Node neighbor : currentNode.neighbors()) {
                SearchNode next = new SearchNode(neighbor, current, dest);
                Iterator<SearchNode> iter = fringe.iterator();
                boolean addNext = true;

                /* Skip nodes that have already been visited. */
                if (visited.containsKey(next.node.id)) {
                    continue;
                }

                while (iter.hasNext()) {
                    SearchNode n = iter.next();
                    if (next.equals(n) && n.fFunc() > next.fFunc()) {
                        iter.remove();
                    } else if (next.equals(n) && n.fFunc() <= next.fFunc()) {
                        addNext = false;
                        break;
                    }
                }
                if (addNext) {
                    fringe.add(next);
                }
            }
        }

        /* Start from dest, follow back the chain of search nodes until start, and save each in path. */
        List<Long> path = new ArrayList<>();
        while (current != null) {
            path.add(0, current.node.id);
            current = current.previousNode;
        }

        return path;
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        List<NavigationDirection> result = new ArrayList<>();
        if (route.size() <= 1) {
            return result;
        }
        NavigationDirection nd = new NavigationDirection();
        nd.direction = NavigationDirection.START;
        nd.distance = 0.0;
        nd.way = NavigationDirection.getHighway(g, route.get(0), route.get(1));
        boolean prevWayIsEmpty = nd.way.isEmpty(); // Track if the previous road name was empty
        for (int i = 1; i < route.size(); i++) {
            Long prev = route.get(i - 1);
            Long curr = route.get(i);
            nd.distance += g.distance(prev, curr);
            if (i == route.size() - 1) {
                if (!prevWayIsEmpty) { // Check if the previous road name was not empty
                    result.add(nd);
                }
                break;
            }
            Long next = route.get(i + 1);
            String nextWayName = NavigationDirection.getHighway(g, curr, next);
            if (nextWayName.isEmpty() || !nd.way.equals(nextWayName)) {
                double prevBearing = g.bearing(route.get(i - 1), route.get(i));
                double curBearing = g.bearing(route.get(i), route.get(i+1));
                if (!prevWayIsEmpty) { // Check if the previous road name was not empty
                    result.add(nd);
                }
                prevWayIsEmpty = nextWayName.isEmpty(); // Update prevWayIsEmpty for the current road
                nd = new NavigationDirection();
                nd.direction = NavigationDirection.getDirection(prevBearing, curBearing);
                nd.distance = 0.0;
                nd.way = nextWayName;
            }
        }
        return result;
    }








    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";

        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }


        private static int getDirection(double b1, double b2) {
            double shift = b2 - b1;
            if (shift > 180) {
                shift -= 360;
            } else if (shift < -180) {
                shift += 360;
            }
            if (shift <= 15 && shift >= -15) {
                return NavigationDirection.STRAIGHT;
            } else if (shift < -15 && shift >= -30) {
                return NavigationDirection.SLIGHT_LEFT;
            } else if (shift > 15 && shift <= 30) {
                return NavigationDirection.SLIGHT_RIGHT;
            } else if (shift < -30 && shift >= -100) {
                return NavigationDirection.LEFT;
            } else if (shift > 30 && shift <= 100) {
                return NavigationDirection.RIGHT;
            } else if (shift < -100) {
                return NavigationDirection.SHARP_LEFT;
            } else {
                return NavigationDirection.SHARP_RIGHT;
            }
        }

        private static String getHighway(GraphDB g, Long v1, Long v2) {
            Iterable<Highway> highways = g.highways();
            Set<Highway> highwaysForV1 = new HashSet<>();
            Set<Highway> highwaysForV2 = new HashSet<>();

            Node n1 = g.getNode(v1);
            Node n2 = g.getNode(v2);

            for (Highway highway : highways) {
                if (highway.contains(n1)) {
                    highwaysForV1.add(highway);
                }
                if (highway.contains(n2)) {
                    highwaysForV2.add(highway);
                }
            }

            Set<Highway> intersection = new HashSet<>();
            for (Highway highway : highwaysForV1) {
                if (highwaysForV2.contains(highway)) {
                    intersection.add(highway);
                }
            }

            // Handle the case where there are no intersecting highways
            if (intersection.isEmpty()) {
                return ""; // Return an empty string
            }

            return intersection.iterator().next().name;
        }

    }

    /**
     * SearchNode
     *
     * This class is used to perform A* search on a graph.
     * This class holds values for f(n), g(n), and h(n) as defined in A*.
     * g(n):     shortest path cost - distance between the Node contained in an instance
     *           and the Node contained in the instance referenced by the "previous" instance member.
     * h(n):     heuristic cost - distance between the current node and the target node.
     * Implements Comparable<T> on the value of f(n), as it's used as the priority
     * in the Priority Queue used as the fringe in A*.
     */
    private static class SearchNode implements Comparable<SearchNode> {
        private SearchNode previousNode;
        private final Node node;
        private final double hFunc;
        private double gFunc;

        SearchNode(Node node, SearchNode previousNode, Node target) {
            this.node = node;
            this.previousNode = previousNode;
            /* Calculate the cost of the path from the start node to this node (gn). */
            this.gFunc = previousNode != null
                    ? previousNode.gFunc
                    + GraphDB.distance(previousNode.node.lon, previousNode.node.lat, node.lon, node.lat)
                    : 0;
            /* Calculate the heuristic estimate of the remaining distance to the goal (hn). */
            this.hFunc = GraphDB.distance(node.lon, node.lat, target.lon, target.lat);
        }

        /**
         * Compares two SearchNode instances based on the f(n) value..
         *
         * @param other The other SearchNode instance to compare to.
         * @return -1 if the other node has a smaller f(n), +1 if this one's is smaller, or else 0.
         */
        @Override
        public int compareTo(SearchNode other) {
            return Double.compare(this.fFunc(), other.fFunc());
        }

        /**
         * Checks if two SearchNode instances are equal based on the wrapped node.
         *
         * @param o The other object to compare for equality.
         * @return true if the id of the wrapped node is the same.
         */
        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            if (this.getClass() != o.getClass()) {
                return false;
            }
            SearchNode other = (SearchNode) o;
            return this.node.equals(other.node);
        }

        @Override
        public int hashCode() {
            return this.node.hashCode();
        }

        double gFunc() {
            return gFunc;
        }

        double hFunc() {
            return hFunc;
        }

        double fFunc() {
            return gFunc + hFunc;
        }
    }
}
