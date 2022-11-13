package day._8;

import util.common.Solver;

import java.util.ArrayList;
import java.util.List;

public class SevenSegmentSearchSolver extends Solver<Integer> {

    private final List<Note> notes = new ArrayList<>();

    protected SevenSegmentSearchSolver(String filename) {
        super(filename);
        parseInput();
    }

    private void parseInput() {
        this.puzzle.forEach(line -> notes.add(parseNote(line)));
    }

    private Note parseNote(String line) {
        Note note = new Note();
        String[] rawNote = line.split(" ");
        boolean isInOutputSection = false;
        for (String s : rawNote) {
            if (s.equals("|")) {
                isInOutputSection = true;
                continue;
            }
            if (isInOutputSection) {
                note.addOutput(s);
            } else {
                note.addSignalPattern(s);
            }
        }
        return note;
    }


    @Override
    protected Integer solvePartOne() {
        return notes.stream()
                .mapToInt(note -> {
                    Decoder decoder = new Decoder(note);
                    return decoder.getNumberOfUniqueDigits().intValue();
                })
                .sum();
    }

    @Override
    protected Integer solvePartTwo() {
        return notes.stream()
                .mapToInt(note -> {
                    Decoder decoder = new Decoder(note);
                    return decoder.decode();
                })
                .sum();
    }

}
