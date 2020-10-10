package chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Player represents a player playing chess and contains attributes for that player
 */
public class Player {
    private final ChessTimer chessTimer = new ChessTimer();
    private List<Piece> pieces = new ArrayList<>();
    private String name;
    private final ChessColor chessColor;
    private Player opponent;

    public Player(String name, ChessColor chessColor) {
        this.name = name;
        this.chessColor = chessColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChessColor getColor() {
        return chessColor;
    }

    public ChessTimer getTimer() {
        return chessTimer;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public Player getOpponent() {
        return opponent;
    }

}
