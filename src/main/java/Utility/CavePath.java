package Utility;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class CavePath {
    private final Vector<Cave> path = new Vector<>();
    private final Set<Cave> small_caves = new HashSet<>();
    private Integer small_cave_visits = 0;
    private Boolean has_start = false;

    public Boolean add(Cave cave) {
        if (cave.getId().equals("start") && has_start) {
            return false;
        }
        has_start = true; // Assume first path added is the start.
        path.add(cave);
        if (cave.isSmall()) {
            small_caves.add(cave);
            small_cave_visits++;
        }
        return true;
    }

    public CavePath copy() {
        CavePath copy = new CavePath();
        for (Cave cave: path) {
            copy.add(cave);
        }
        copy.has_start = this.has_start;
        return copy;
    }

    public boolean isValid(Boolean allow_two_visits) {
        return small_cave_visits <= small_caves.size() + (allow_two_visits ? 1 : 0);
    }
}
