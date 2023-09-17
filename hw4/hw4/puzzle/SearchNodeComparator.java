package hw4.puzzle;

import java.util.Comparator;

public final class SearchNodeComparator implements Comparator<SearchNode> {
    @Override
    public int compare(SearchNode s1, SearchNode s2) {
        if (s1 == null || s2 == null) {
            throw new NullPointerException("SearchNode instance to compare cannot be null.");
        }

        return s1.estimatedDistanceToGoal() + s1.distanceFromStart()
                - s2.estimatedDistanceToGoal() - s2.distanceFromStart();
    }
}
