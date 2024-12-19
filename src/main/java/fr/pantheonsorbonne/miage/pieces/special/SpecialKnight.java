package fr.pantheonsorbonne.miage.pieces.special;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;

public class SpecialKnight extends SpecialPiece {

    public SpecialKnight(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<int[]> getPossibleActions(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int[][] directions = {
                { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 },
                { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 }
        };

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (board.isValidCell(newRow, newCol)) {
                ChessPiece target = board.getPiece(newRow, newCol);
                if (target == null || !target.getColor().equals(this.color)) {
                    moves.add(new int[] { newRow, newCol });
                }
            }
        }
        return moves;
    }

    @Override
    public char getSymbol() {
        return 'N';
    }
}
