package day._12;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CaveSystem {

    private Cave start;
    private Cave end;
    private final Map<Integer, Cave> caves = new HashMap<>();
    private final Map<Integer, Set<Integer>> connections = new HashMap<>();

    public CaveSystem(List<String> gridLines, Function<String, List<String>> tokenizer) {

        gridLines.forEach(line -> {
            List<Cave> cavePair = tokenizer.apply(line).stream()
                    .map(cave -> new Cave(cave, this::isABigCave))
                    .collect(Collectors.toList());
            int leftHash = insertCave(cavePair.get(0));
            int rightHash = insertCave(cavePair.get(1));
            connections.putIfAbsent(leftHash, new HashSet<>());
            connections.putIfAbsent(rightHash, new HashSet<>());
            connections.get(leftHash).add(rightHash);
            connections.get(rightHash).add(leftHash);
        });
        caves.values().forEach(this::setNotables);
    }

    private boolean isABigCave(String cave) {
        boolean areAllCharsUpperCase = true;
        for (int i = 0; i < cave.length(); i++) {
            if (!Character.isUpperCase(cave.charAt(i))) {
                areAllCharsUpperCase = false;
                break;
            }
        }
        return areAllCharsUpperCase;
    }

    private void setNotables(Cave cave) {
        if ("start".equals(cave.getLabel())) {
            start = cave;
        } else if ("end".equals(cave.getLabel())) {
            end = cave;
        }
    }

    private int insertCave(Cave cave) {
        int hashCode = cave.hashCode();
        caves.putIfAbsent(hashCode, cave);
        return hashCode;
    }

    public List<Cave> getNeighboursOfCave(int hashCode) {
        Cave cave = caves.get(hashCode);
        if (cave == null) {
            throw new IllegalArgumentException();
        } else {
            return connections.get(hashCode).stream().map(caves::get).collect(Collectors.toUnmodifiableList());
        }
    }

    public Cave getStart() {
        return start;
    }

    public Cave getEnd() {
        return end;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Integer, Set<Integer>> entry : connections.entrySet()) {
            stringBuilder.append(caves.get(entry.getKey()).getLabel()).append(": ");
            entry.getValue().forEach(integer -> {
                stringBuilder.append(caves.get(integer).getLabel());
                stringBuilder.append(" ");
            });
            stringBuilder.append("\n");
        }
        return stringBuilder.toString().trim();
    }

}
