package fr.pantheonsorbonne.miage.robots;

import fr.pantheonsorbonne.miage.board.ChessBoard;

import fr.pantheonsorbonne.miage.game.Action;


public abstract class Player {
    protected String color;

    public Player(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract Action playTurn(ChessBoard board);
}
