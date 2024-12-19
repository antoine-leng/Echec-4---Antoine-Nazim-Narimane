package fr.pantheonsorbonne.miage.game;

public class Action {
    private final int startRow;
    private final int startCol;
    private final int targetRow;
    private final int targetCol;

    public Action(int startRow, int startCol, int targetRow, int targetCol) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.targetRow = targetRow;
        this.targetCol = targetCol;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public int getTargetCol() {
        return targetCol;
    }
}
