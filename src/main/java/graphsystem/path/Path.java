package graphsystem.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path<N> extends ArrayList<N> implements Comparable<Path<N>> {

    private double weight = Double.MAX_VALUE;

    public static <N> List<Path<N>> findPermutations(List<N> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return Collections.emptyList();
        }
        List<Path<N>> permutations = new ArrayList<>();
        Path<N> initPath = new Path<>();
        initPath.add(nodes.get(0));
        permutations.add(initPath);

        for (int i = 1; i < nodes.size(); i++) {
            for (int j = permutations.size() - 1; j >= 0; j--) {
                Path<N> lastTempPath = permutations.remove(j);
                for (int k = 0; k <= lastTempPath.size(); k++) {
                    Path<N> newPath = new Path<>();
                    newPath.addAll(lastTempPath.subList(0, k));
                    newPath.add(nodes.get(i));
                    newPath.addAll(lastTempPath.subList(k, lastTempPath.size()));
                    permutations.add(newPath);
                }
            }
        }
        return permutations;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Path<N> o) {
        if (this.getWeight() < o.getWeight()) {
            return -1;
        } else if (this.getWeight() > o.getWeight()) {
            return 1;
        }
        return 0;
    }
}
