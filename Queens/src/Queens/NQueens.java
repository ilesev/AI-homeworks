package Queens;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class NQueens {
    int[] board;
    Random random;
    int dimension;
    private String initialString;
    private String result;

    public NQueens(int n) {
        this.board = new int[n];
        dimension = n;
        random = new Random();
        generateInitialString();
    }

    public String getResult() {
        return result;
    }

    private int getConflicts() {
        int count = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = i + 1; j < dimension; j++) {
                if (board[i] == board[j]) {
                    count++;
                }
                if (i + board[i] == j + board[j]) {
                    count++;
                }
                if (i - board[i] == j - board[j]) {
                    count++;
                }
            }
        }
        return count;
    }

    private int calculateQueenConflicts(int row) {
        int count = 0;
        for (int j = 0; j < dimension; j++) {
            if (board[row] == board[j] && row != j) {
                count++;
            }
            if (row - board[row] == j - board[j] && row != j) {
                count++;
            }
            if (row + board[row] == j + board[j] && row != j) {
                count++;
            }
        }
        return count;
    }

    public void solve() {
        while (getConflicts() != 0)
        {
            int row = random.nextInt(dimension);
            while (calculateQueenConflicts(row) == 0) {
                row = random.nextInt(dimension);
            }
            getNextRow(row);
        }
        generateResult();
    }

    public void generateResult() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < dimension; i++) {
            StringBuilder row = new StringBuilder(initialString);
            row.setCharAt(board[i], '*');
            result.append(row.toString());
            result.append(System.lineSeparator());
        }
        this.result = result.toString();
    }

    public void generateInitialString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dimension; i++) {
            sb.append('_');
        }
        initialString = sb.toString();
    }

    private void getNextRow(int row) {
        int queenConflicts = calculateQueenConflicts(row);
        List<Integer> listOfConflicts = new ArrayList<>();

        for (int i = 0; i < dimension; i++) {
            board[row] = i;
            int conf = calculateQueenConflicts(row);
            if (queenConflicts > conf) {
                queenConflicts = conf;
                listOfConflicts.clear();
                listOfConflicts.add(i);
            } else if (queenConflicts == conf) {
                listOfConflicts.add(i);
            }
        }

        board[row] = listOfConflicts.get(random.nextInt(listOfConflicts.size()));
//        board[row] = Collections.min(listOfConflicts);
//        board[row] = Collections.max(listOfConflicts);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter n");
        int n = scanner.nextInt();
        long t1 = System.currentTimeMillis();
        NQueens queens = new NQueens(n);
        System.out.println("STARTED");
        queens.solve();
        System.out.println("STOPPED");
        if (n > 100) {
            try (PrintWriter pw = new PrintWriter("result.txt")) {
                pw.write(queens.getResult());
            } catch (FileNotFoundException ignore) {
            }
        } else {
            System.out.println(queens.getResult());
        }
        System.out.println("Solved in " + (System.currentTimeMillis() - t1) + " ms.");
    }
}
