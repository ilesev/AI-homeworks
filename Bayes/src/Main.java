import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> training = Parser.parse("BEIS.txt");
        Collections.shuffle(training);
        CrossValidator.crossValidate(training);
    }
}
