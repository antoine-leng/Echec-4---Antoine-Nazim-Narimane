package fr.pantheonsorbonne.miage.game;

import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.robots.Player;
import fr.pantheonsorbonne.miage.utils.ColorUtil;

import java.util.List;

public class TurnManager {
    private final List<Player> players;
    private int currentPlayerIndex = 0;

    public TurnManager(List<Player> players) {
        this.players = players;
    }

    public String getCurrentPlayerColor() {
        return players.get(currentPlayerIndex).getColor();
    }

    public void playTurn(ChessBoard board, GameState gameState) {
        Player currentPlayer = players.get(currentPlayerIndex);

        while (gameState.getEliminatedPlayers().contains(currentPlayer.getColor())) {
            nextTurn();
            currentPlayer = players.get(currentPlayerIndex);
        }

        String color;
        switch (currentPlayer.getColor()) {
            case "1":
                color = ColorUtil.RED;
                break;
            case "2":
                color = ColorUtil.GREEN;
                break;
            case "3":
                color = ColorUtil.YELLOW;
                break;
            case "4":
                color = ColorUtil.BLUE;
                break;
            default:
                color = ColorUtil.RESET; 
        }

        System.out.println(ColorUtil.colorize("Tour du joueur : " + currentPlayer.getColor(), color));

        pause();

        if (board.isKingInCheck(currentPlayer.getColor())) {
            System.out.println("Le roi du joueur " + currentPlayer.getColor() + " est en échec !");

            List<String> legalMoves = board.getLegalMovesForPlayer(currentPlayer.getColor());
            if (legalMoves.isEmpty()) {
                System.out.println("Le joueur " + currentPlayer.getColor() + " est en échec et mat !");
                gameState.eliminatePlayer(currentPlayer.getColor());
                board.removePlayerPieces(currentPlayer.getColor());
                nextTurn();
                return;
            } else {
                System.out.println("Coups légaux disponibles pour sortir de l'échec :");
                for (String move : legalMoves) {
                    System.out.println(move);
                }

            }

        }

        Action action = currentPlayer.playTurn(board);
        if (action != null) {
            try {
                board.movePiece(action.getStartRow(), action.getStartCol(),
                        action.getTargetRow(), action.getTargetCol());
                System.out.println("Joueur " + currentPlayer.getColor() + " a joué : " +
                        "(" + action.getStartRow() + ", " + action.getStartCol() + ") ==> (" +
                        action.getTargetRow() + ", " + action.getTargetCol() + ")");
                pause();

                for (String opponentColor : gameState.getActivePlayers()) {
                    if (!opponentColor.equals(currentPlayer.getColor()) && board.isKingInCheck(opponentColor)) {
                        String colorizedPlayer = ColorUtil.colorize("Joueur " + opponentColor,
                                getColorCode(opponentColor));
                        String message = ColorUtil.colorize(" est en échec !", ColorUtil.PURPLE);
                        System.out.println(colorizedPlayer + message);
                        pause();
                    }
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Mouvement invalide : " + e.getMessage());
                pause();
            }
        } else {
            System.out.println("Joueur " + currentPlayer.getColor() + " n'a pas de mouvement disponible.");
            pause();
        }

        if (board.isPlayerInPat(currentPlayer.getColor())) {
            String patMessage = ColorUtil.colorize("Le joueur " + currentPlayer.getColor() + " est en pat !",
                    ColorUtil.PURPLE);
            System.out.println(patMessage);
            pause();

            gameState.eliminatePlayer(currentPlayer.getColor());
            board.removePlayerPieces(currentPlayer.getColor());
            pause();
        }

        nextTurn();
        pause();
    }

    private void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    private String getColorCode(String playerColor) {
        if (playerColor.equals("1")) {
            return ColorUtil.RED;
        } else if (playerColor.equals("2")) {
            return ColorUtil.GREEN;
        } else if (playerColor.equals("3")) {
            return ColorUtil.YELLOW;
        } else if (playerColor.equals("4")) {
            return ColorUtil.BLUE;
        } else {
            return ColorUtil.RESET;
        }
    }

    private void pause() {
        try {
            Thread.sleep(140);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Pause interrompue : " + e.getMessage());
        }
    }

}
