package tictactoe;

import java.util.Scanner;

public class TicTacToe {
    private Board ticTacToe;
    private Scanner scanner;

    public TicTacToe() {
        ticTacToe = new Board();
        scanner = new Scanner(System.in);
    }

    public TicTacToe(Player player) {
        ticTacToe = new Board(player);
        scanner = new Scanner(System.in);
    }

    public void play() {
        while (!ticTacToe.hasGameEnded()) {
            ticTacToe.print();

            if (ticTacToe.getPlayer() == Player.PLAYER) {
                System.out.println("Enter coordinates of next move: ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();
                int indexToBeSet = (row-1)*3 + col - 1;
                if (ticTacToe.isFieldSet(indexToBeSet)) {
                    System.out.println("Already placed there!");
                    continue;
                }
                ticTacToe.setNextMove(indexToBeSet);
            } else {
                alphaBeta(ticTacToe, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
            System.out.println();
        }

        ticTacToe.print();
        if (ticTacToe.getWinner() != null) {
            System.out.println("Winner!" + ticTacToe.getWinner().toString());
        } else {
            System.out.println("Draw!");
        }
    }

    private int alphaBeta(Board board, int alpha, int beta) {
        if(board.hasGameEnded()) {
            return score(board);
        } else if (board.getPlayer() == Player.PLAYER) {
            return getMax(board, alpha, beta);
        }
        return getMin(board,alpha, beta);
    }

    private int score(Board board) {
        if (board.hasGameEnded() && board.getWinner() != null && board.getWinner() == Player.PLAYER) {
            return 1;
        } else if (board.hasGameEnded() && board.getWinner() != null && board.getWinner() == Player.COMPUTER) {
            return -1;
        }
        return 0;
    }

    private int getMin(Board board, int alpha, int beta) {
        int indexOfBestMove = -1;

        for (int n : board.getAvailableMoves()) {

            Board copy = board.getCopy();
            copy.setNextMove(n);

            int score = alphaBeta(copy, alpha, beta);

            if (score < beta) {
                beta = score;
                indexOfBestMove = n;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.setNextMove(indexOfBestMove);
        }
        return beta;
    }

    private int getMax(Board board, int alpha, int beta) {
        int indexOfBestMove = -1;

        for (int n : board.getAvailableMoves()) {

            Board copy = board.getCopy();
            copy.setNextMove(n);
            int score = alphaBeta(copy, alpha, beta);

            if (score > alpha) {
                alpha = score;
                indexOfBestMove = n;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (indexOfBestMove != -1) {
            board.setNextMove(indexOfBestMove);
        }
        return alpha;
    }
}
