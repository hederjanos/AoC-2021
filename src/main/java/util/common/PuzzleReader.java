package util.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PuzzleReader {

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

}
