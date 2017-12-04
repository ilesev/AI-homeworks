package Jaba;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Jaba {
    private static List<String> path = new ArrayList<>();
    private static List<String> generatedFrogs = new ArrayList<>();
    private static Stack<String> container = new Stack<>();
    private static boolean foundPath = false;

    private static String generateInitialString(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append('>');
        }
        sb.append('_');
        for (int i = 0; i < n; i++) {
            sb.append('<');
        }
        return sb.toString();
    }

    private boolean areFrogsArranged(String s) {
        int index = 0;
        for (; index < s.length() / 2; index++) {
            if (s.charAt(index) != '<') {
                return false;
            }
        }

        if (s.charAt(index) != '_') return false;
        index++;

        for (; index < s.length() / 2; index++) {
            if (s.charAt(index) != '>') {
                return false;
            }
        }

        return true;
    }

    public void generatePath(String frogs, int depth) {
        if (areFrogsArranged(frogs)) {
            path.addAll(container);
            foundPath = true;
            return;
        } else if (depth == 0) {
            if (!container.empty()) container.pop();
        }

        if (frogs.contains("_><") && path.isEmpty()) {
            String newFrog = frogs.replace("_><", "<>_");
            addFrogsIfNecessary(newFrog, depth);
        }
        if (frogs.contains("><_") && path.isEmpty()) {
            String newFrog = frogs.replace("><_", "_<>");
            addFrogsIfNecessary(newFrog, depth);
        }
        if (frogs.contains(">_") && path.isEmpty()) {
            String newFrog = frogs.replace(">_", "_>");
            addFrogsIfNecessary(newFrog, depth);
        }
        if (frogs.contains("_<") && path.isEmpty()) {
            String newFrog = frogs.replace("_<", "<_");
            addFrogsIfNecessary(newFrog, depth);
        }

        if (!container.empty()) {
            container.pop();
        }
    }

    private void addFrogsIfNecessary(String newFrog, int depth) {
        if (!generatedFrogs.contains(newFrog)) {
            container.push(newFrog);
            generatedFrogs.add(newFrog);
            generatePath(newFrog, depth - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println("Enter n:");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        long t1 = System.currentTimeMillis();

        String initial = generateInitialString(n);
        Jaba mnogoJabi = new Jaba();
        int depth = 0;

        while (!foundPath) {
            mnogoJabi.generatePath(initial, depth++);
        }

        System.out.println(initial);
        path.forEach(System.out::println);
        System.out.println("Time the program ran: " + (System.currentTimeMillis() - t1) + "ms.");
    }
}
