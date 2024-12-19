package fr.pantheonsorbonne.miage.board;

import fr.pantheonsorbonne.miage.game.ScoreManager;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;
import fr.pantheonsorbonne.miage.pieces.normal.*;


import java.util.ArrayList;
import java.util.List;

public class ChessBoard {
    private final int rows = 14;
    private final int cols = 14;
    private ChessPiece[][] grid;
    private ScoreManager scoreManager;

    public ChessBoard(ScoreManager scoreManager) {
        this.grid = new ChessPiece[rows][cols];
        this.scoreManager = scoreManager; 
    }

    public void display() {
        final String HORIZONTAL_LINE = "   " + "-----".repeat(cols);

        System.out.print("  ");
        for (int col = 0; col < cols; col++) {
            System.out.printf(" %3d ", col);
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.println(HORIZONTAL_LINE);
            System.out.printf("%2d |", i);
            for (int j = 0; j < cols; j++) {
                String content;
                if (!isValidCell(i, j)) {
                    content = "##";
                } else {
                    ChessPiece piece = grid[i][j];
                    content = (piece != null) ? piece.getNotation() + "  " : " ";
                }
                System.out.printf(" %-3s|", content);
            }
            System.out.println();
        }
        System.out.println(HORIZONTAL_LINE);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public boolean isValidCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        boolean inTopLeftCorner = (row < 3 && col < 3);
        boolean inTopRightCorner = (row < 3 && col >= cols - 3);
        boolean inBottomLeftCorner = (row >= rows - 3 && col < 3);
        boolean inBottomRightCorner = (row >= rows - 3 && col >= cols - 3);
        return !(inTopLeftCorner || inTopRightCorner || inBottomLeftCorner || inBottomRightCorner);
    }

    public ChessPiece getPiece(int row, int col) {
        if (!isValidCell(row, col)) {
            throw new IllegalArgumentException("Cellule invalide : (" + row + ", " + col + ")");
        }
        return grid[row][col];
    }

    public void setPiece(int row, int col, ChessPiece piece) {
        if (!isValidCell(row, col)) {
            throw new IllegalArgumentException("Cellule invalide : (" + row + ", " + col + ")");
        }
        grid[row][col] = piece;
    }


    public void movePiece(int row, int col, int targetRow, int targetCol) {
        ChessPiece piece = getPiece(row, col);
    
        if (piece == null) {
            throw new IllegalArgumentException("Aucune pièce à déplacer à (" + row + ", " + col + ")");
        }
    
        ChessPiece capturedPiece = getPiece(targetRow, targetCol); 
        if (capturedPiece != null) {
            scoreManager.updateScoreForCapture(piece.getColor(), capturedPiece);
        }
    
        setPiece(targetRow, targetCol, piece);
        setPiece(row, col, null);
        piece.setPosition(targetRow, targetCol);
    }
    
    

    public void promotePawn(int row, int col) {
        ChessPiece piece = getPiece(row, col);
        if (piece instanceof Pawn) {
            String color = piece.getColor();
            boolean isPromotionPosition = false;
    
            if (color.equals("1") && row == 0) {
                isPromotionPosition = true;
            } else if (color.equals("3") && row == 13) {
                isPromotionPosition = true;
            } else if (color.equals("4") && col == 13) {
                isPromotionPosition = true;
            } else if (color.equals("2") && col == 0) {
                isPromotionPosition = true;
            }
    
            if (isPromotionPosition) {
                setPiece(row, col, new Queen(piece.getColor(), row, col));
                scoreManager.updateScoreForSpecialMove(color, 1); 
                System.out.println("Pion promu en reine pour le joueur " + color + " !");
            }
        }
    }
    

    public boolean isKingInCheck(String kingColor) {
        int kingRow = -1, kingCol = -1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isValidCell(row, col)) {
                    ChessPiece piece = getPiece(row, col);
                    if (piece != null && piece.getColor().equals(kingColor) && piece.getSymbol() == 'K') {
                        kingRow = row;
                        kingCol = col;
                        break;
                    }
                }
            }
        }

        if (kingRow == -1 || kingCol == -1) {
            return false; 
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isValidCell(row, col)) {
                    ChessPiece piece = getPiece(row, col);
                    if (piece != null && !piece.getColor().equals(kingColor)) {
                        List<int[]> moves = piece.getPossibleActions(this);
                        for (int[] move : moves) {
                            if (move[0] == kingRow && move[1] == kingCol) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isEnemyPiece(int row, int col, String color) {
        ChessPiece piece = getPiece(row, col);
        return piece != null && !piece.getColor().equals(color);
    }

    public boolean isPlayerInPat(String playerColor) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isValidCell(row, col)) { 
                    ChessPiece piece = getPiece(row, col);
                    if (piece != null && piece.getColor().equals(playerColor)) {
                        List<int[]> moves = piece.getPossibleActions(this);
                        for (int[] move : moves) {
                            if (isValidCell(move[0], move[1])) { 
                                ChessPiece temp = getPiece(move[0], move[1]);
                                setPiece(move[0], move[1], piece);
                                setPiece(row, col, null);
                                boolean inCheck = isKingInCheck(playerColor);
                                setPiece(row, col, piece);
                                setPiece(move[0], move[1], temp);
                                if (!inCheck)
                                    return false; 
                            }
                        }
                    }
                }
            }
        }
        return true; 
    }

    public boolean hasLegalMoves(String playerColor) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!isValidCell(row, col)) {
                    continue; 
                }

                ChessPiece piece = getPiece(row, col);
                if (piece != null && piece.getColor().equals(playerColor)) {
                    List<int[]> moves = piece.getPossibleActions(this);
                    for (int[] move : moves) {
                        int targetRow = move[0];
                        int targetCol = move[1];

                        if (isValidCell(targetRow, targetCol)
                                && isValidMove(row, col, targetRow, targetCol, playerColor)) {
                            return true; 
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isValidMove(int startRow, int startCol, int targetRow, int targetCol, String playerColor) {
        ChessPiece piece = getPiece(startRow, startCol);
        ChessPiece target = getPiece(targetRow, targetCol);

        setPiece(targetRow, targetCol, piece); 
        setPiece(startRow, startCol, null);
        piece.setPosition(targetRow, targetCol);

        boolean isKingSafe = !isKingInCheck(playerColor);

        setPiece(startRow, startCol, piece);
        setPiece(targetRow, targetCol, target);
        piece.setPosition(startRow, startCol);

        return isKingSafe;
    }

    public void removePlayerPieces(String playerColor) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (isValidCell(row, col)) { 
                    ChessPiece piece = getPiece(row, col);
                    if (piece != null && piece.getColor().equals(playerColor)) {
                        setPiece(row, col, null);
                    }
                }
            }
        }
    }

    public List<String> getLegalMovesForPlayer(String playerColor) {
        List<String> legalMoves = new ArrayList<>();
    

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!isValidCell(row, col)) {
                    continue; 
                }
    
                ChessPiece piece = getPiece(row, col);
    
                if (piece != null && piece.getColor().equals(playerColor)) {
                    List<int[]> possibleMoves = piece.getPossibleActions(this);
    
                    for (int[] move : possibleMoves) {
                        int targetRow = move[0];
                        int targetCol = move[1];
    
                        if (isValidMove(row, col, targetRow, targetCol, playerColor)) {
                            legalMoves.add("Pièce: " + piece.getClass().getSimpleName() +
                                           " (" + row + "," + col + ") (" + targetRow + "," + targetCol + ")");
                        }
                        
                    }
                }
            }
        }
    
        return legalMoves;
    }

    
    
}
