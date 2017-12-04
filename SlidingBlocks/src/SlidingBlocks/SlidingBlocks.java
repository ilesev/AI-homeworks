package SlidingBlocks;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

enum Directions {
    Left("left"),
    Right("right"),
    Down("down"),
    Up("up");

    Directions(String direction) {
        this.direction = direction;
    }

    private String direction;

    public String getDirection() {
        return direction;
    }
}

public class SlidingBlocks {
    private static PriorityQueue<Board> boardPriorityQueue = new PriorityQueue<Board>(Comparator.comparingInt(Board::getF));
    private static Set<Board> visitedBoards = new HashSet<Board>();

    public static void solve(Board board, int n) {
        boardPriorityQueue.add(board);

        while (!boardPriorityQueue.isEmpty()) {
            Board current = boardPriorityQueue.poll();
            if (current.isSolved()) {
                current.printStepsAndMoves();
                return;
            }

            visitedBoards.add(current);

            addUniqueBoardsToQueue(board.generateNextState(current, Directions.Up, n));
            addUniqueBoardsToQueue(board.generateNextState(current, Directions.Down, n));
            addUniqueBoardsToQueue(board.generateNextState(current, Directions.Left, n));
            addUniqueBoardsToQueue(board.generateNextState(current, Directions.Right, n));
        }
        System.out.println("Could not find solution");
    }

    private static void addUniqueBoardsToQueue(Board board) {
        if (board != null && !visitedBoards.contains(board)) {
            boardPriorityQueue.add(board);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter n");
        int n = scanner.nextInt();

        System.out.println("Enter board");
        int[] initialBoard = new int[n + 1];
        for (int i = 0; i < n + 1; i++) {
            initialBoard[i] = scanner.nextInt();
        }

        Board board = new Board(initialBoard, n);
        solve(board, (int) Math.sqrt(n + 1));
    }
}
