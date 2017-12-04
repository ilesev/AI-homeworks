package Solver;

public class Knapsack implements Comparable<Knapsack>{
    private boolean[] items;
    private int weight;
    private int value;

    public Knapsack() {
        weight = 0;
        value = 0;
        items = new boolean[Solver.KNAPSACK.size()];
        for (int i = 0; i < Solver.KNAPSACK.size(); i++) {
            if(Math.random() >= 0.5) {
                items[i] = true;
            }
        }
        calculateWeightAndValue();
    }

    public int getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    public boolean[] getItems() {
        return items;
    }

    public void calculateWeightAndValue() {
        weight = 0;
        value = 0;

        for (int i = 0; i < items.length; i++) {
            if (items[i]) {
                weight += Solver.KNAPSACK.get(i).getWeight();
                value += Solver.KNAPSACK.get(i).getValue();
            }
        }

        if (weight > Solver.MAX_WEIGHT) {
            value = 0;
        }
    }

    public void mutate() {
        for (int i = 0; i < items.length; i++) {
            if (Math.random() <= 0.4) {
                items[i] = !items[i];
            }
        }
    }

    @Override
    public int compareTo(Knapsack o) {
        return Integer.compare(value, o.getValue());
    }
}
