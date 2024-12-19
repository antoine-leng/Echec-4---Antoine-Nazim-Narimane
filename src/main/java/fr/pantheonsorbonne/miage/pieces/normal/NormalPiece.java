package fr.pantheonsorbonne.miage.pieces.normal;

import fr.pantheonsorbonne.miage.pieces.ChessPiece;

public abstract class NormalPiece extends ChessPiece {
    public NormalPiece(String color, int row, int col) {
        super(color, row, col);
    }

    @Override
    public boolean isSpecialPiece() {
        return false; 
    }
}
