package SlidingBlocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class Board {
    private int[] board;
    private int steps;
    private int heuristic;
    private int indexOfZero;
    private List<Directions> previousDirections;

    Board(int[] initialState, int n) {
        board = initialState;
        steps = 0;
        heuristic = getManhattanDistance(initialState, n);
        indexOfZero = getIndexOfZero(initialState);
        previousDirections = new LinkedList<Directions>();
    }

    Board(Board previous, int newIndexOfZero, Directions direction, int n) {
        board = Arrays.copyOf(previous.board, previous.board.length);
        board[previous.indexOfZero] = board[newIndexOfZero];
        board[newIndexOfZero] = 0;
        steps = previous.steps + 1;
        heuristic = getManhattanDistance(board, n);
        indexOfZero = newIndexOfZero;
        if (previousDirections == null) {
            previousDirections = new LinkedList<Directions>();
        }
        previousDirections.addAll(previous.previousDirections);
        previousDirections.add(direction);
    }

    public int getF() {
        return steps + heuristic;
    }

    public int getIndexOfZero(int[] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    public boolean isSolved() {
        for (int i = 0; i < board.length-2; i++) {
            if (board[i] > board[i+1]) return false;
        }
        return board[0] == 1;
    }

    public Board generateNextState(Board board, Directions directions, int n) {
        switch (directions) {
            case Up: {
                return board.indexOfZero > n-1 ? new Board(board, board.indexOfZero - n, Directions.Down, n) : null;
            }
            case Down: {
                return board.indexOfZero < n*(n-1) ? new Board(board, board.indexOfZero + n, Directions.Up, n) : null;
            }
            case Left: {
                return board.indexOfZero % n > 0 ? new Board(board, board.indexOfZero - 1, Directions.Right, n) : null;
            }
            case Right: {
                return board.indexOfZero % n < n-1 ? new Board(board, board.indexOfZero + 1, Directions.Left, n) : null;
            }
            default: {
                return null;
            }
        }
    }

    public void printStepsAndMoves() {
        System.out.println("Steps: " + steps);
        System.out.print("Directions: ");
        for (Directions direction : previousDirections) {
            System.out.print(direction.getDirection() + " ");
        }
    }

    public int getManhattanDistance(int[] board, int n) {
        int count = 0;
        for (int index = 0; index < board.length; index++) {
            if (board[index] != 0) {
                count += Math.abs(index / n - (board[index]-1) / n) + Math.abs(index % n - (board[index]-1) % n);
            }
        }
        return count;
    }

    //shte trqbvat za set-a v koito shte pazim poseteni systoqniq
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board1 = (Board) o;

        return Arrays.equals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board);
    }
}