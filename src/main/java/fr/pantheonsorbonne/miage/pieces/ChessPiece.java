package fr.pantheonsorbonne.miage.pieces;

import java.util.List;
import fr.pantheonsorbonne.miage.board.ChessBoard;
import fr.pantheonsorbonne.miage.utils.ColorUtil;

public abstract class ChessPiece {
    protected String color; 
    protected int row, col;

    public ChessPiece(String color, int row, int col) {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public String getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String getUnicode() {
        String baseSymbol;

        char symbol = getSymbol();
        if (symbol == 'K') {
            baseSymbol = "\u265A"; 
        } else if (symbol == 'Q') {
            baseSymbol = "\u265B"; 
        } else if (symbol == 'R') {
            baseSymbol = "\u265C"; 
        } else if (symbol == 'B') {
            baseSymbol = "\u265D"; 
        } else if (symbol == 'N') {
            baseSymbol = "\u265E"; 
        } else if (symbol == 'P') {
            baseSymbol = "\u265F"; 
        } else {
            baseSymbol = "?";
        }

        if (color.equals("1")) {
            return ColorUtil.colorize(baseSymbol, ColorUtil.RED); 
        } else if (color.equals("2")) {
            return ColorUtil.colorize(baseSymbol, ColorUtil.GREEN); 
        } else if (color.equals("3")) {
            return ColorUtil.colorize(baseSymbol, ColorUtil.YELLOW); 
        } else if (color.equals("4")) {
            return ColorUtil.colorize(baseSymbol, ColorUtil.BLUE); 
        } else {
            return baseSymbol; 
        }
    }


    public String getNotation() {
        String unicode = getUnicode();
        return isSpecialPiece() ? unicode + "s" : unicode;
    }

    public abstract boolean isSpecialPiece();

    public abstract List<int[]> getPossibleActions(ChessBoard board);


    public abstract char getSymbol();
}
