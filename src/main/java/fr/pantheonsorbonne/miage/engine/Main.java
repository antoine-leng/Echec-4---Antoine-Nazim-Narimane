package fr.pantheonsorbonne.miage.engine;

import fr.pantheonsorbonne.miage.board.BoardInitializer;
import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.robots.RandomBot;
import fr.pantheonsorbonne.miage.utils.ColorUtil;
import fr.pantheonsorbonne.miage.robots.Player;
import fr.pantheonsorbonne.miage.game.TurnManager;
import fr.pantheonsorbonne.miage.game.GameState;
import fr.pantheonsorbonne.miage.game.ScoreManager;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Player> robots = new ArrayList<>();
        robots.add(new RandomBot("1")); 
        robots.add(new RandomBot("2")); 
        robots.add(new RandomBot("3")); 
        robots.add(new RandomBot("4")); 

        GameState gameState = new GameState();
        gameState.initializePlayers(new String[] { "1", "2", "3", "4" });

        ScoreManager scoreManager = new ScoreManager(gameState);
        ChessBoard board = BoardInitializer.initialize(scoreManager);

        TurnManager turnManager = new TurnManager(robots);

        System.out.println("Début de la partie !");
        while (!gameState.isGameOver()) {
            System.out.println("\nPlateau actuel :");
            board.display();

            turnManager.playTurn(board, gameState);

            System.out.println("\nJoueurs actifs :");

            for (String player : gameState.getActivePlayers()) {
                String color;
                switch (player) {
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

                int score = gameState.getScore(player);
                System.out.println(ColorUtil.colorize("Joueur " + player + ": " + score + " points", color));
            }

            System.out.println("\nJoueurs éliminés :");

            for (String player : gameState.getEliminatedPlayers()) {
                String color;
                switch (player) {
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

                int score = gameState.getScore(player);
                System.out.println(ColorUtil.colorize("Joueur " + player + ": " + score + " points", color));
            }

            gameState.checkEndConditions();

        }

        System.out.println("\nFin de la partie !");
        System.out.println("Joueur gagnant : " + gameState.getLeadingPlayer());
    }
}
