package fr.pantheonsorbonne.miage.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameState {
    private Map<String, Integer> scores = new HashMap<>();
    private boolean gameOver = false;
    private Set<String> eliminatedPlayers = new HashSet<>();

    public void initializePlayers(String[] playerColors) {
        for (int i = 0; i < playerColors.length; i++) {
            scores.put(playerColors[i], 0);
        }
    }

    public void updateScore(String color, int points) {
        if (!scores.containsKey(color)) {
            scores.put(color, 0);
        }
        int newScore = scores.get(color) + points;
        scores.put(color, newScore);
    }

    public int getScore(String color) {
        if (scores.containsKey(color)) {
            return scores.get(color);
        }
        return 0;
    }

    public String getLeadingPlayer() {
        String leadingPlayer = null;
        int maxScore = Integer.MIN_VALUE;

        for (String player : scores.keySet()) {
            int score = scores.get(player);
            if (score > maxScore) {
                maxScore = score;
                leadingPlayer = player;
            }
        }

        return leadingPlayer;
    }

    public void displayScores() {
        for (String player : scores.keySet()) {
            int score = scores.get(player);
            System.out.println("Joueur " + player + ": " + score + " points");
        }
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public void checkEndConditions() {
        int activePlayers = 0;
        for (String player : scores.keySet()) {
            if (!eliminatedPlayers.contains(player)) {
                activePlayers++;
            }
        }

        if (activePlayers <= 1) {
            setGameOver(true);
        }
    }

    public void eliminatePlayer(String color) {
        eliminatedPlayers.add(color);
        System.out.println("Le joueur " + color + " est éliminé !");
    }

    public Set<String> getEliminatedPlayers() {
        Set<String> copy = new HashSet<>();
        for (String player : eliminatedPlayers) {
            copy.add(player);
        }
        return copy;
    }

    public Set<String> getActivePlayers() {
        Set<String> activePlayers = new HashSet<>();
        for (String player : scores.keySet()) {
            if (!eliminatedPlayers.contains(player)) {
                activePlayers.add(player);
            }
        }
        return activePlayers;
    }
}
