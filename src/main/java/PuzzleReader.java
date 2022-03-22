import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public class PuzzleReader {

    public static List<String> readPuzzle(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException io) {
            System.out.println(io.getMessage());
            return Collections.emptyList();
        }
    }
}
