import java.util.ArrayList;
import java.util.List;

public class CrossValidator {
    public static final String DEMOCRAT = "democrat";
    public static final String REPUBLICAN = "republican";
    private static int totalCorrectGuesses = 0;

    public static void crossValidate(List<String> training) {
        int tenth = training.size() / 10;
        for (int i = 0; i < 10; i++) {
            List<String> testing = new ArrayList<>();
            for (int j = 0; j < tenth; j++) {
                testing.add(training.get(0));
                training.remove(0);
            }

            int[] positiveDemocraticResponses = new int[16];
            int[] negativeDemocraticResponses = new int[16];
            int[] positiveRepublicanResponses = new int[16];
            int[] negativeRepublicanResponses = new int[16];

            int democrats = calcCharacteristicsAndDemocrats(training, positiveDemocraticResponses,
                    negativeDemocraticResponses, positiveRepublicanResponses, negativeRepublicanResponses);

            double chanceToBeADemocrat = (double)democrats / (double)training.size();

            predict(testing, chanceToBeADemocrat, positiveDemocraticResponses, negativeDemocraticResponses,
                    positiveRepublicanResponses, negativeRepublicanResponses);

            for(String test : testing) {
                training.add(test);
            }
        }

        System.out.println("Overall: " + (int)(100*((double)totalCorrectGuesses / (double)(10*43))) + "%");
    }

    private static int calcCharacteristicsAndDemocrats(List<String> training, int[] positiveDemocratResponses, int[] negativeDemocratResponses,
                                                       int[] positiveRepublicanResponses, int[] negativeRepublicanResponses) {
        int democratsCount = 0;

        for(String row : training) {
            String[] characteristics = row.split(",");
            if (characteristics[0].equals(DEMOCRAT)) {
                democratsCount++;
                addCharacteristics(characteristics, positiveDemocratResponses, negativeDemocratResponses);
            } else {
                addCharacteristics(characteristics, positiveRepublicanResponses, negativeRepublicanResponses);
            }
        }

        return democratsCount;
    }

    private static void addCharacteristics(String[] characteristics, int[] positiveResponses, int[] negativeResponses) {
        for (int i = 1; i < characteristics.length; i++) {
            if (characteristics[i].charAt(0) == '?') {
                continue;
            } else if (characteristics[i].charAt(0) == 'y') {
                positiveResponses[i - 1]++;
            } else {
                negativeResponses[i - 1]++;
            }
        }
    }

    private static void predict(List<String> testing, double chanceToBeADemocrat, int[] positiveDemocrats, int[] negativeDemocrats,
                                int[] positiveRepublicans, int[] negativeRepublicans) {
        double chanceToBeARepublican = 1 - chanceToBeADemocrat;
        List<String> predictions = new ArrayList<>();
        List<String> actual = new ArrayList<>();

        for (String row : testing) {
            String[] characterstics = row.split(",");
            actual.add(characterstics[0]);
            String votedFor = getVotedFor(characterstics, chanceToBeADemocrat, chanceToBeARepublican,
                    positiveDemocrats, negativeDemocrats, positiveRepublicans, negativeRepublicans);
            predictions.add(votedFor);
        }

        int correctGuesses = 0;
        int index = 0;
        for (String predicted : predictions) {
            if (predicted.equals(actual.get(index++))) {
                correctGuesses++;
            }
        }

        totalCorrectGuesses += correctGuesses;
        System.out.println("Correct guesses: " + correctGuesses + " out of " + predictions.size() + "; As percentage: " + (int)(100*((double)correctGuesses / (double)predictions.size()) + 0.5) + "%");
    }

    private static String getVotedFor(String[] characterstics, double chanceToBeADemocrat, double chanceToBeARepublican,
                                      int[] positiveDemocrats, int[] negativeDemocrats, int[] positiveRepublicans, int[] negativeRepublicans) {
        for (int i = 1; i < characterstics.length; i++) {
            if(characterstics[i].charAt(0) == 'y') {
                chanceToBeADemocrat *= (double)positiveDemocrats[i-1] / (double)(positiveDemocrats[i-1] + negativeDemocrats[i-1]);
                chanceToBeARepublican *= (double)positiveRepublicans[i-1] / (double)(positiveRepublicans[i-1] + negativeRepublicans[i-1]);
            } else {
                chanceToBeADemocrat *= (double)negativeDemocrats[i-1] / (double)(positiveDemocrats[i-1] + negativeDemocrats[i-1]);
                chanceToBeARepublican *= (double)negativeRepublicans[i-1] / (double)(positiveRepublicans[i-1] + negativeRepublicans[i-1]);
            }
        }

        return chanceToBeADemocrat > chanceToBeARepublican ? DEMOCRAT : REPUBLICAN;
    }



}
