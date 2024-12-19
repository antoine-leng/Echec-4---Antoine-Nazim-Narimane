package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.pieces.ChessPiece;
import fr.pantheonsorbonne.miage.pieces.normal.Queen;

public class ScoreManager {
    private final GameState gameState;

    public ScoreManager(GameState gameState) {
        this.gameState = gameState;
    }

    
    public void updateScoreForCapture(String playerColor, ChessPiece capturedPiece) {
        if (capturedPiece != null) {
            int points = 0;
            String pieceName = capturedPiece.getClass().getSimpleName();

            if (pieceName.equals("Pawn")) {
                points = 1;
            } else if (pieceName.equals("Knight") || pieceName.equals("Bishop")) {
                points = 3;
            } else if (pieceName.equals("Rook")) {
                points = 5;
            } else if (pieceName.equals("Queen")) {
                points = 9;
            } else if (pieceName.equals("King")) {
                points = 20;
            }

            if (capturedPiece.isSpecialPiece()) {
                points *= 2; 
            }

            gameState.updateScore(playerColor, points);
        }
    }

  
    public void updateScoreForQueenCheck(String playerColor, ChessPiece piece, ChessBoard board) {
        if (piece instanceof Queen) {
            int checkCount = 0;
            for (String opponentColor : gameState.getActivePlayers()) {
                if (!opponentColor.equals(playerColor) && board.isKingInCheck(opponentColor)) {
                    checkCount++;
                }
            }

            if (checkCount == 2) {
                updateScoreForSpecialMove(playerColor, 1); 
            } else if (checkCount == 3) {
                updateScoreForSpecialMove(playerColor, 5); 
            }
        }
    }

    
    public void updateScoreForNonQueenCheck(String playerColor, ChessPiece piece, ChessBoard board) {
        if (!(piece instanceof Queen)) {
            int checkCount = 0;
            for (String opponentColor : gameState.getActivePlayers()) {
                if (!opponentColor.equals(playerColor) && board.isKingInCheck(opponentColor)) {
                    checkCount++;
                }
            }

            if (checkCount == 2) {
                updateScoreForSpecialMove(playerColor, 5);
            } else if (checkCount == 3) {
                updateScoreForSpecialMove(playerColor, 20); 
            }
        }
    }


    public void updateScoreForPat(String playerColor) {

        updateScoreForSpecialMove(playerColor, 20);


        for (String opponentColor : gameState.getActivePlayers()) {
            if (!opponentColor.equals(playerColor)) {
                updateScoreForSpecialMove(opponentColor, 10);
            }
        }
    }

    public void updateScoreForCheckmate(String attackerColor) {
        updateScoreForSpecialMove(attackerColor, 20);
    }


    public void updateScoreForPawnPromotion(String playerColor) {
        updateScoreForSpecialMove(playerColor, 1);
    }

   
    public void updateScoreForSpecialMove(String playerColor, int points) {
        gameState.updateScore(playerColor, points);
    }
}
