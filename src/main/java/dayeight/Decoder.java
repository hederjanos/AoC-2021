package dayeight;

import java.util.*;

public class Decoder {

    private final Note note;
    private final Map<SortedSet<Character>, Integer> digitMap = new HashMap<>();

    public Decoder(Note note) {
        this.note = note;
    }

    private void initDigitMap() {
        note.getSignalPatterns().forEach(digitPattern -> digitMap.put(digitPattern, handleUniqueCases(digitPattern)));
    }

    private Integer handleUniqueCases(SortedSet<Character> pattern) {
        Integer digit = null;
        if (pattern.size() == 2) {
            digit = 1;
        }
        if (pattern.size() == 3) {
            digit = 7;
        }
        if (pattern.size() == 4) {
            digit = 4;
        }
        if (pattern.size() == 7) {
            digit = 8;
        }
        return digit;
    }

    public Integer decode() {
        int number = 0;
        initDigitMap();
        handleSixSegmentPatterns();
        handleFiveSegmentPatterns();
        for (int i = 0; i < note.getOutputs().size(); i++) {
            number += digitMap.get(note.getOutputs().get(i)) * Math.pow(10, note.getOutputs().size() - i - 1);
        }
        return number;
    }

    public Long getNumberOfUniqueDigits() {
        return note.getOutputs().stream()
                .filter(pattern -> pattern.size() == 2 || pattern.size() == 3 || pattern.size() == 4 || pattern.size() == 7)
                .count();
    }

    private void handleSixSegmentPatterns() {
        Optional<SortedSet<Character>> oneDigitOptional = getDigitPatternFromMap(1);
        Optional<SortedSet<Character>> fourDigitOptional = getDigitPatternFromMap(4);
        if (oneDigitOptional.isPresent() && fourDigitOptional.isPresent()) {
            SortedSet<Character> oneDigitChars = oneDigitOptional.get();
            SortedSet<Character> fourDigitChars = fourDigitOptional.get();
            List<SortedSet<Character>> sixSegmentPatterns = note.getSixSegmentPatterns();
            for (SortedSet<Character> pattern : sixSegmentPatterns) {
                if (!pattern.containsAll(oneDigitChars)) {
                    digitMap.put(pattern, 6);
                    sixSegmentPatterns.remove(pattern);
                    break;
                }
            }
            for (SortedSet<Character> pattern : sixSegmentPatterns) {
                if (!pattern.containsAll(fourDigitChars)) {
                    digitMap.put(pattern, 0);
                    sixSegmentPatterns.remove(pattern);
                    break;
                }
            }
            digitMap.put(sixSegmentPatterns.get(0), 9);
        }
    }

    private void handleFiveSegmentPatterns() {
        Optional<SortedSet<Character>> oneDigitOptional = getDigitPatternFromMap(1);
        Optional<SortedSet<Character>> sixDigitOptional = getDigitPatternFromMap(6);
        if (oneDigitOptional.isPresent() && sixDigitOptional.isPresent()) {
            SortedSet<Character> oneDigitChars = oneDigitOptional.get();
            SortedSet<Character> sixDigitChars = sixDigitOptional.get();
            List<SortedSet<Character>> fiveSegmentPatterns = note.getFiveSegmentPatterns();
            for (SortedSet<Character> pattern : fiveSegmentPatterns) {
                if (pattern.containsAll(oneDigitChars)) {
                    digitMap.put(pattern, 3);
                    fiveSegmentPatterns.remove(pattern);
                    break;
                }
            }
            for (SortedSet<Character> pattern : fiveSegmentPatterns) {
                if (sixDigitChars.containsAll(pattern)) {
                    digitMap.put(pattern, 5);
                    fiveSegmentPatterns.remove(pattern);
                    break;
                }
            }
            digitMap.put(fiveSegmentPatterns.get(0), 2);
        }
    }

    private Optional<SortedSet<Character>> getDigitPatternFromMap(int digit) {
        return digitMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getValue() == digit)
                .findFirst()
                .map(Map.Entry::getKey);
    }

}
