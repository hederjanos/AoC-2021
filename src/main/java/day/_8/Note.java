package day._8;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Note {

    private final List<SortedSet<Character>> signalPatterns = new ArrayList<>();
    private final List<SortedSet<Character>> outputs = new ArrayList<>();

    public List<SortedSet<Character>> getSignalPatterns() {
        return signalPatterns;
    }

    public List<SortedSet<Character>> getOutputs() {
        return outputs;
    }

    public void addSignalPattern(String pattern) {
        signalPatterns.add(getSortedCharsOfAPattern(pattern));
    }

    public void addOutput(String output) {
        outputs.add(getSortedCharsOfAPattern(output));
    }

    public List<SortedSet<Character>> getSixSegmentPatterns() {
        return signalPatterns.stream()
                .filter(s -> s.size() == 6)
                .collect(Collectors.toList());
    }

    public List<SortedSet<Character>> getFiveSegmentPatterns() {
        return signalPatterns.stream()
                .filter(s -> s.size() == 5)
                .collect(Collectors.toList());
    }

    private SortedSet<Character> getSortedCharsOfAPattern(String pattern) {
        SortedSet<Character> characters = new TreeSet<>();
        pattern.chars()
                .mapToObj(character -> (char) character)
                .forEach(characters::add);
        return characters;
    }

}
