package graphsystem.path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path<N> extends ArrayList<N> implements Comparable<Path<N>> {

    private final List<Double> weightIncrements = new ArrayList<>();

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
        return weightIncrements.stream().mapToDouble(Double::doubleValue).sum();
    }

    public void addNode(N node, Double weight) {
        weightIncrements.add(weight);
        add(node);
    }

    public N removeNode(int index) {
        weightIncrements.remove(index);
        return remove(index);
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

    public Path<N> copy() {
        Path<N> newPath = new Path<>();
        for (int i = 0; i < weightIncrements.size(); i++) {
            newPath.addNode(this.get(i), weightIncrements.get(i));
        }
        return newPath;
    }

}
