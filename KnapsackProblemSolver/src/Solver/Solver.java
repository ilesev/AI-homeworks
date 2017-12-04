package Solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Solver {
    public static final List<Item> KNAPSACK = new LinkedList<Item>();
    public static final int MAX_WEIGHT = 5000;
    public static final int POPULATION_SIZE = 500;
    public static final int GENERATIONS = 500;
    private static Random random = new Random();

    public static void main(String[] args) {
        KNAPSACK.add(new Item("map", 90, 150));
        KNAPSACK.add(new Item("compass", 130, 35));
        KNAPSACK.add(new Item("water", 1530, 200));
        KNAPSACK.add(new Item("sandwich", 500, 160));
        KNAPSACK.add(new Item("glucose", 150, 60));
        KNAPSACK.add(new Item("tin", 680, 45));
        KNAPSACK.add(new Item("banana", 270, 60));
        KNAPSACK.add(new Item("apple", 390, 40));
        KNAPSACK.add(new Item("cheese", 230, 30));
        KNAPSACK.add(new Item("beer", 520, 10));
        KNAPSACK.add(new Item("suntan", 110, 70));
        KNAPSACK.add(new Item("camera", 320, 30));
        KNAPSACK.add(new Item("tshirt", 240, 15));
        KNAPSACK.add(new Item("trousers", 480, 10));
        KNAPSACK.add(new Item("umbrella", 730, 40));
        KNAPSACK.add(new Item("wprf trousers", 420, 70));
        KNAPSACK.add(new Item("wprf overclothes", 430, 75));
        KNAPSACK.add(new Item("note-case", 220, 80));
        KNAPSACK.add(new Item("sunglasses", 70, 20));
        KNAPSACK.add(new Item("towel", 180, 12));
        KNAPSACK.add(new Item("socks", 40, 50));
        KNAPSACK.add(new Item("towel", 180, 12));
        KNAPSACK.add(new Item("book", 300, 10));
        KNAPSACK.add(new Item("notebook", 900, 1));
        KNAPSACK.add(new Item("tent", 2000, 150));

        Knapsack bestMatch = evolveAndGetBestMatch();
        print(bestMatch);
    }

    private static void print(Knapsack bestMatch) {
        System.out.println("Value: " + bestMatch.getValue());
        System.out.println("Weight: " + bestMatch.getWeight());
        System.out.println("Items: ");
        int count = 1;
        for (int i = 0; i < bestMatch.getItems().length; i++) {
            if (bestMatch.getItems()[i]) {
                System.out.println(count++ + ". " + KNAPSACK.get(i).getName());
            }
        }
    }

    private static Knapsack evolveAndGetBestMatch() {
        List<Knapsack> population = new ArrayList<Knapsack>(POPULATION_SIZE);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(new Knapsack());
        }

        Collections.sort(population);

        for (int i = 0; i < GENERATIONS; i++) {
            int presentChildren = 0;
            List<Knapsack> childrenList = new ArrayList<Knapsack>(POPULATION_SIZE);

            while(presentChildren < POPULATION_SIZE) {
                int firstParent = getParentIndex();
                int secondParent = getParentIndex();

                if (firstParent == secondParent) continue;

                Knapsack child = reproduce(population, firstParent, secondParent);
                if (child != null && child.getValue() > 0) {
                    presentChildren++;
                    childrenList.add(child);
                }
            }

            Collections.sort(childrenList);

            for (int j = 0; j < POPULATION_SIZE - 10; j++) {
                population.set(j, childrenList.get(POPULATION_SIZE - j - 1));
            }

            Collections.sort(population);
        }

        return population.get(POPULATION_SIZE-1);
    }

    private static int getParentIndex() {
        //make sure we have a higher change of getting one of the "better" children
        if (Math.random() <= 0.5) {
            return POPULATION_SIZE - random.nextInt(POPULATION_SIZE / 2) - 1;
        } else if (Math.random() <= 0.8) {
            return (POPULATION_SIZE / 2) - random.nextInt((POPULATION_SIZE * 3) / 10);
        }

        return random.nextInt(POPULATION_SIZE / 5);
    }

    private static Knapsack reproduce(List<Knapsack> population, int firstParent, int secondParent) {
        if (Math.random() > 0.25) return null;

        Knapsack parentOne = population.get(firstParent);
        Knapsack parentTwo = population.get(secondParent);

        Knapsack child = new Knapsack();
        int start = random.nextInt(parentOne.getItems().length);
        for (int i = 0; i < start; i++) {
            child.getItems()[i] = parentOne.getItems()[i];
        }

        int end = start + random.nextInt(parentOne.getItems().length - start);

        while (start++ < end) {
            child.getItems()[start] = parentTwo.getItems()[start];
        }

        child.mutate();
        child.calculateWeightAndValue();
        return child;
    }
}

/*
ITEM WEIGHT - g VALUE
map 90 150
compass 130 35
water 1530 200
sandwich 500 160
glucose 150 60
tin 680 45
banana 270 60
apple 390 40
cheese 230 30
beer 520 10
suntan cream 110 70
camera 320 30
T-shirt 240 15
trousers 480 10
umbrella 730 40
waterproof trousers 420 70
waterproof overclothes 430 75
note-case 220 80
sunglasses 70 20
towel 180 12
socks 40 50
book 300 10
notebook 900 1
tent 2000 150
 */