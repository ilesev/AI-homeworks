package tictactoe;

import java.util.ArrayList;
import java.util.List;

enum Player {
    PLAYER("Player"),
    COMPUTER("AI");

    String name;

    Player(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

public class Board {
    private String board;
    private Player player;
    private Player winner = null;

    public Board() {
        board = getInitialBoard();
        player = Player.PLAYER;
    }

    public Board(Player player) {
        board = getInitialBoard();
        this.player = player;
    }

    private String getInitialBoard() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public void print() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board.charAt(3*i + j) + " | ");
            }
            System.out.println();
            System.out.println("------------");
        }
    }

    public boolean isFieldSet(int index) {
        return !Character.isWhitespace(board.charAt(index));
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public void setNextMove(int index) {
        this.board = board.substring(0, index) + (player == Player.PLAYER ? "X" : "O") + board.substring(index+1);
        player = player == Player.PLAYER ? Player.COMPUTER : Player.PLAYER;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Integer> getAvailableMoves() {
        List<Integer> moves = new ArrayList<>();
        for (int i = 0; i < board.length(); i++) {
            if (Character.isWhitespace(board.charAt(i))) {
                moves.add(i);
            }
        }

        return moves;
    }

    public Player getWinner() {
        return winner;
    }

    public boolean hasGameEnded() {
        // vertical
        if ((board.charAt(0) == board.charAt(3) && board.charAt(3) == board.charAt(6) && (board.charAt(0) == 'X' || board.charAt(0) == 'O')) ||
                (board.charAt(1) == board.charAt(4) && board.charAt(4) == board.charAt(7) && (board.charAt(1) == 'X' || board.charAt(1) == 'O')) ||
                (board.charAt(2) == board.charAt(5) && board.charAt(5) == board.charAt(8) && (board.charAt(2) == 'X' || board.charAt(2) == 'O'))
                ) {
            winner = player == Player.PLAYER ? Player.COMPUTER : Player.PLAYER;
            return true;
        }
        // horizontal
        if ((board.charAt(0) == board.charAt(1) && board.charAt(1) == board.charAt(2) && (board.charAt(0) == 'X' || board.charAt(0) == 'O')) ||
                (board.charAt(3) == board.charAt(4) && board.charAt(4) == board.charAt(5) && (board.charAt(3) == 'X' || board.charAt(3) == 'O')) ||
                (board.charAt(6) == board.charAt(7) && board.charAt(7) == board.charAt(8)) && (board.charAt(6) == 'X' || board.charAt(6) == 'O')) {
            winner = player == Player.PLAYER ? Player.COMPUTER : Player.PLAYER;
            return true;
        }
        // diagonal
        if ((board.charAt(0) == board.charAt(4) && board.charAt(4) == board.charAt(8) && (board.charAt(0) == 'X' || board.charAt(0) == 'O')) ||
                (board.charAt(2) == board.charAt(4) && board.charAt(4) == board.charAt(6)) && (board.charAt(2) == 'X' || board.charAt(2) == 'O')) {
            winner = player == Player.PLAYER ? Player.COMPUTER : Player.PLAYER;
            return true;
        }

        return getAvailableMoves().size() == 0;
    }

    public Board getCopy() {
        Board newBoard = new Board(player);
        newBoard.setBoard(board);
        return newBoard;
    }
}
