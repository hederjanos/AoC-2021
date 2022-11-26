package day._8;

import util.common.Solver;

import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class SevenSegmentSearchSolver extends Solver<Integer> {

    private final List<Note> notes;

    protected SevenSegmentSearchSolver(String filename) {
        super(filename);
        notes = parseInput();
    }

    private List<Note> parseInput() {
        return puzzle.stream().map(this::parseNote).collect(Collectors.toList());
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
        return solve(this::getNumberOfUniqueDigits);
    }

    private Integer solve(ToIntFunction<Note> noteMapper) {
        return notes.stream().mapToInt(noteMapper).sum();
    }

    private int getNumberOfUniqueDigits(Note note) {
        Decoder decoder = new Decoder(note);
        return decoder.getNumberOfUniqueDigits().intValue();
    }

    @Override
    protected Integer solvePartTwo() {
        return solve(this::getDecodedInt);
    }

    private int getDecodedInt(Note note) {
        Decoder decoder = new Decoder(note);
        return decoder.decode();
    }

}
