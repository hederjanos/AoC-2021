package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PuzzleReader {

    private static final List<String> MAZE_DIM_PATTERNS = List.of("^[3-9]\\s[3-9]$",
            "^[1-9]\\d+\\s[1-9]\\d+$",
            "^[3-9]\\s[1-9]\\d+$",
            "^[1-9]\\d+\\s[3-9]$");
    private static final String MAZE_DIM_SPLITTER = " ";
    private static final char[] MAZE_ENABLED_CHARS = new char[]{'.', '#', 'c', 's'};

    private final List<String> puzzleLines = new ArrayList<>();

    public void readPuzzle(String filename) {
        try (InputStream inputStream = getPuzzleFromResourcesAsStream(filename);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                puzzleLines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // read more at https://mkyong.com/java/java-read-a-file-from-resources-folder/
    private InputStream getPuzzleFromResourcesAsStream(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    public List<String> getPuzzleLines() {
        return puzzleLines;
    }

    public void printPuzzle() {
        puzzleLines.forEach(System.out::println);
    }

    public void validateMaze() {
        checkDimensionDefinition();
        checkDimensions();
        checkCharacters();
    }

    private void checkDimensionDefinition() {
        boolean isAnyMatchWithEnabledPatterns = false;
        for (String patternString : MAZE_DIM_PATTERNS) {
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(puzzleLines.get(0));
            if (matcher.matches()) {
                isAnyMatchWithEnabledPatterns = true;
                break;
            }
        }
        if (!isAnyMatchWithEnabledPatterns) {
            throw new IllegalArgumentException();
        }
    }

    private void checkDimensions() {
        String[] dimensions = puzzleLines.get(0).split(MAZE_DIM_SPLITTER);
        int width = Integer.parseInt(dimensions[0]);
        int height = Integer.parseInt(dimensions[1]);
        if (puzzleLines.size() < height - 1) {
            throw new IllegalArgumentException();
        }
        long count = puzzleLines.subList(1, puzzleLines.size()).stream()
                .map(String::length)
                .distinct()
                .count();
        if (count != 1) {
            throw new IllegalArgumentException();
        }
        if (width != puzzleLines.get(1).length()) {
            throw new IllegalArgumentException();
        }
    }

    private void checkCharacters() {
        Map<Character, Integer> characterMap = puzzleLines.subList(1, puzzleLines.size()).stream()
                .flatMap(line -> IntStream.range(0, line.length()).mapToObj(line::charAt))
                .collect(Collectors.toMap(
                        character -> character,
                        character -> 1,
                        (curr, next) -> curr + 1)
                );
        long numberOfValidChars = characterMap.keySet().stream()
                .filter(this::isValidChar)
                .count();
        if (numberOfValidChars != MAZE_ENABLED_CHARS.length || numberOfValidChars != characterMap.keySet().size()) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isValidChar(Character character) {
        boolean isValidChar = false;
        for (char enabledChar : MAZE_ENABLED_CHARS) {
            if (character == enabledChar) {
                isValidChar = true;
                break;
            }
        }
        return isValidChar;
    }

}