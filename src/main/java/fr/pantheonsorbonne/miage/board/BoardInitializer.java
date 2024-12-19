package fr.pantheonsorbonne.miage.board;

import fr.pantheonsorbonne.miage.game.ScoreManager;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;
import fr.pantheonsorbonne.miage.pieces.normal.*;
import fr.pantheonsorbonne.miage.pieces.special.SpecialPiece;

public class BoardInitializer {


public static ChessBoard initialize(ScoreManager scoreManager) {
    ChessBoard board = new ChessBoard(scoreManager);
    markUnplayableCorners(board);
    initializeYellowPieces(board);
    initializeRedPieces(board);
    initializeBluePieces(board);
    initializeGreenPieces(board);
    return board;
}


    private static void markUnplayableCorners(ChessBoard board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isValidCell(i, j)) { 
                    board.setPiece(i, j, null);
                }
                if (board.isValidCell(i, 13 - j)) { 
                    board.setPiece(i, 13 - j, null);
                }
                if (board.isValidCell(13 - i, j)) { 
                    board.setPiece(13 - i, j, null);
                }
                if (board.isValidCell(13 - i, 13 - j)) { 
                    board.setPiece(13 - i, 13 - j, null);
                }
            }
        }
    }


    private static void initializeYellowPieces(ChessBoard board) {

        board.setPiece(0, 3, new Rook("3", 0, 3));
        board.setPiece(0, 4, new Knight("3", 0, 4));
        board.setPiece(0, 5, new Bishop("3", 0, 5));
        board.setPiece(0, 6, new Queen("3", 0, 6));
        board.setPiece(0, 7, new King("3", 0, 7));
        board.setPiece(0, 8, new Bishop("3", 0, 8));
        board.setPiece(0, 9, new Knight("3", 0, 9));
        board.setPiece(0, 10, new Rook("3", 0, 10));

        for (int col = 3; col <= 10; col++) {
            board.setPiece(1, col, new Pawn("3", 1, col));
        }
    }

    private static void initializeRedPieces(ChessBoard board) {

        board.setPiece(13, 3, new Rook("1", 13, 3));
        board.setPiece(13, 4, new Knight("1", 13, 4));
        board.setPiece(13, 5, new Bishop("1", 13, 5));
        board.setPiece(13, 6, new Queen("1", 13, 6));
        board.setPiece(13, 7, new King("1", 13, 7));
        board.setPiece(13, 8, new Bishop("1", 13, 8));
        board.setPiece(13, 9, new Knight("1", 13, 9));
        board.setPiece(13, 10, new Rook("1", 13, 10));


        for (int col = 3; col <= 10; col++) {
            board.setPiece(12, col, new Pawn("1", 12, col));
        }
    }


    private static void initializeBluePieces(ChessBoard board) {

        board.setPiece(3, 0, new Rook("4", 3, 0));
        board.setPiece(4, 0, new Knight("4", 4, 0));
        board.setPiece(5, 0, new Bishop("4", 5, 0));
        board.setPiece(6, 0, new Queen("4", 6, 0));
        board.setPiece(7, 0, new King("4", 7, 0));
        board.setPiece(8, 0, new Bishop("4", 8, 0));
        board.setPiece(9, 0, new Knight("4", 9, 0));
        board.setPiece(10, 0, new Rook("4", 10, 0));

        for (int row = 3; row <= 10; row++) {
            board.setPiece(row, 1, new Pawn("4", row, 1));
        }
    }

    public static boolean validateBoard(ChessBoard board) {
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                ChessPiece piece = board.getPiece(row, col);
                if (piece instanceof SpecialPiece && !board.isValidCell(row, col)) {
                    return false;
                }
            }
        }
        return true;
    }


    private static void initializeGreenPieces(ChessBoard board) {

        board.setPiece(3, 13, new Rook("2", 3, 13));
        board.setPiece(4, 13, new Knight("2", 4, 13));
        board.setPiece(5, 13, new Bishop("2", 5, 13));
        board.setPiece(6, 13, new Queen("2", 6, 13));
        board.setPiece(7, 13, new King("2", 7, 13));
        board.setPiece(8, 13, new Bishop("2", 8, 13));
        board.setPiece(9, 13, new Knight("2", 9, 13));
        board.setPiece(10, 13, new Rook("2", 10, 13));

        for (int row = 3; row <= 10; row++) {
            board.setPiece(row, 12, new Pawn("2", row, 12));
        }
    }

    

}
