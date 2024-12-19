package fr.pantheonsorbonne.miage.pieces.normal;

import java.util.ArrayList;
import java.util.List;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;

public class Pawn extends NormalPiece {

    private boolean firstMove = true; 

    public Pawn(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public List<int[]> getPossibleActions(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int[][] directions = getPawnDirections(); 

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (board.isValidCell(newRow, newCol) && board.getPiece(newRow, newCol) == null) {
                moves.add(new int[]{newRow, newCol});

                if (firstMove) {
                    int newRow2 = newRow + direction[0];
                    int newCol2 = newCol + direction[1];
                    if (board.isValidCell(newRow2, newCol2) && board.getPiece(newRow2, newCol2) == null) {
                        moves.add(new int[]{newRow2, newCol2});
                    }
                }
            }

            for (int[] diagonal : getPawnDiagonals()) {
                int captureRow = row + diagonal[0];
                int captureCol = col + diagonal[1];
                if (board.isValidCell(captureRow, captureCol)) {
                    ChessPiece target = board.getPiece(captureRow, captureCol);
                    if (target != null && !target.getColor().equals(this.color)) {
                        moves.add(new int[]{captureRow, captureCol});
                    }
                }
            }
        }

        return moves;
    }

    @Override
    public void setPosition(int row, int col) {
        super.setPosition(row, col);
        firstMove = false; 
    }

    @Override
    public char getSymbol() {
        return 'P';
    }


    private int[][] getPawnDirections() {
        if (this.color.equals("1")) {        
            return new int[][] { { -1, 0 } };
        } else if (this.color.equals("3")) { 
            return new int[][] { { 1, 0 } };
        } else if (this.color.equals("4")) { 
            return new int[][] { { 0, 1 } };
        } else if (this.color.equals("2")) { 
            return new int[][] { { 0, -1 } };
        } else {
            return new int[][] {}; 
        }
    }
    
    private int[][] getPawnDiagonals() {
        if (this.color.equals("1")) {      
            return new int[][] { { -1, -1 }, { -1, 1 } };
        } else if (this.color.equals("3")) { 
            return new int[][] { { 1, -1 }, { 1, 1 } };
        } else if (this.color.equals("4")) { 
            return new int[][] { { -1, 1 }, { 1, 1 } };
        } else if (this.color.equals("2")) { 
            return new int[][] { { -1, -1 }, { 1, -1 } };
        } else {
            return new int[][] {}; 
        }
    }
    
}
