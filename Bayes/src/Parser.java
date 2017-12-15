import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {
    // file should be in the same directory as project
    public static List<String> parse(String filename) {
        try(Stream<String> stream = Files.lines(Paths.get(filename))) {
            return stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Couldn't read file " + filename);
        }
        return Collections.emptyList();
    }
}
