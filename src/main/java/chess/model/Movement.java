package chess.model;

import java.awt.*;
import java.util.*;
import java.util.List;

import static chess.model.Color.*;

/**
 * Is responsible for finding legal moves
 */
public class Movement {
    private Map<Point, Piece> boardMap = new HashMap<>();
    private List<Point> points = new ArrayList<>(); // Holds points which are valid to move to

    public void setBoardMap(Map<Point, Piece> boardMap) {
        this.boardMap = boardMap;
    }

    /**
     * Creates a list of current legal moves for a given piece (and its position)
     *
     * @param pieceToMove
     * @param markedPoint current position for the piece
     * @return current legal moves
     */
    public List<Point> pieceMoveDelegation(Piece pieceToMove, Point markedPoint) {
        points.clear();
        switch (pieceToMove.getPieceType()) {
            case ROOK:
                legalMovesRook(pieceToMove, markedPoint);
                break;
            case BISHOP:
                legalMovesBishop(pieceToMove, markedPoint);
                break;
            case KNIGHT:
                legalMovesKnight(pieceToMove, markedPoint);
                break;
            case QUEEN:
                legalMovesQueen(pieceToMove, markedPoint);
                break;
            case KING:
                legalMovesKing(pieceToMove, markedPoint);
                break;
            case PAWN:
                if (pieceToMove.getColor() == WHITE) {
                    legalMovesWhitePawn(pieceToMove, markedPoint);
                } else if (pieceToMove.getColor() == BLACK) {
                    legalMovesBlackPawn(pieceToMove, markedPoint);
                }
                break;
        }
        return new ArrayList<>(points);
    }

    private void legalMovesRook(Piece pieceToMove, Point markedPoint) {
        up(pieceToMove, markedPoint,7);
        down(pieceToMove, markedPoint,7);
        left(pieceToMove, markedPoint,7);
        right(pieceToMove, markedPoint,7);
    }

    private void legalMovesBishop(Piece pieceToMove, Point markedPoint) {
        upLeft(pieceToMove, markedPoint,7);
        upRight(pieceToMove, markedPoint,7);
        downRight(pieceToMove, markedPoint,7);
        downLeft(pieceToMove, markedPoint,7);
    }

    private void legalMovesKnight(Piece pieceToMove, Point markedPoint) {
        int x = markedPoint.x;
        int y = markedPoint.y;

        addPoint(new Point(x+1, y-2),pieceToMove);
        addPoint(new Point(x+2, y-1),pieceToMove);
        addPoint(new Point(x+2, y+1),pieceToMove);
        addPoint(new Point(x+1, y+2),pieceToMove);
        addPoint(new Point(x-1, y+2),pieceToMove);
        addPoint(new Point(x-2, y+1),pieceToMove);
        addPoint(new Point(x-2, y-1),pieceToMove);
        addPoint(new Point(x-1, y-2),pieceToMove);
    }

    private void legalMovesWhitePawn(Piece pieceToMove, Point markedPoint) {
        //Check if the pawn can move up, if they are at their starting position they can move two squares, the pawn can't take a piece on front of it
        if(markedPoint.getY() == 6){
            if(isUnoccupied(new Point(markedPoint.x,markedPoint.y-1)) && isUnoccupied(new Point(markedPoint.x,markedPoint.y-2)) ){
                up(pieceToMove,markedPoint,2);
            }else if(isUnoccupied(new Point(markedPoint.x,markedPoint.y-1))) {
                up(pieceToMove,markedPoint,1);
            }
        } else{
            if(isUnoccupied(new Point(markedPoint.x,markedPoint.y-1))) up(pieceToMove,markedPoint,1);
        }

        //if there are a opponent in its diagonal up squares it can take it
        if(!isUnoccupied(new Point(markedPoint.x+1,markedPoint.y-1)) && boardMap.get(new Point(markedPoint.x+1,markedPoint.y-1)).getColor() == BLACK){
            upRight(pieceToMove,markedPoint,1);
        }
        if(!isUnoccupied(new Point(markedPoint.x-1,markedPoint.y-1)) && boardMap.get(new Point(markedPoint.x-1,markedPoint.y-1)).getColor() == BLACK){
            upLeft(pieceToMove,markedPoint,1);
        }

    }

    private void legalMovesBlackPawn(Piece pieceToMove, Point markedPoint) {
        //Check if the pawn can move down, if they are at their starting position they can move two squares, the pawn can't take a piece on front of it
        if(markedPoint.getY() == 1){
            if(isUnoccupied(new Point(markedPoint.x,markedPoint.y+1)) && boardMap.get(new Point(markedPoint.x,markedPoint.y+2)) == null ){
                down(pieceToMove,markedPoint,2);
            }else if(isUnoccupied(new Point(markedPoint.x,markedPoint.y+1))) {
                down(pieceToMove,markedPoint,1);
            }
        } else{
            if(isUnoccupied(new Point(markedPoint.x,markedPoint.y+1))) down(pieceToMove,markedPoint,1);
        }

        //if there are a opponent in its diagonal down squares it can take it
        if(!isUnoccupied(new Point(markedPoint.x+1,markedPoint.y+1)) && boardMap.get(new Point(markedPoint.x+1,markedPoint.y+1)).getColor() == WHITE){
            downRight(pieceToMove,markedPoint,1);
        }
        if(!isUnoccupied(new Point(markedPoint.x-1,markedPoint.y+1)) && boardMap.get(new Point(markedPoint.x-1,markedPoint.y+1)).getColor() == WHITE){
            downLeft(pieceToMove,markedPoint,1);
        }
    }

    private void legalMovesKing(Piece pieceToMove, Point markedPoint) {
        up(pieceToMove,markedPoint,1);
        right(pieceToMove,markedPoint,1);
        down(pieceToMove,markedPoint,1);
        left(pieceToMove,markedPoint,1);

        upLeft(pieceToMove,markedPoint,1);
        upRight(pieceToMove,markedPoint,1);
        downLeft(pieceToMove,markedPoint,1);
        downRight(pieceToMove,markedPoint,1);
    }

    public void legalMovesQueen(Piece pieceToMove, Point markedPoint) {
        up(pieceToMove, markedPoint,7);
        down(pieceToMove, markedPoint,7);
        left(pieceToMove, markedPoint,7);
        right(pieceToMove, markedPoint,7);

        upLeft(pieceToMove, markedPoint,7);
        upRight(pieceToMove, markedPoint,7);
        downRight(pieceToMove, markedPoint,7);
        downLeft(pieceToMove, markedPoint,7);
    }

    private boolean isUnoccupied(Point p){
        if(boardMap.get(p) == null) return true;
        return false;
    }
     /**
     * Creates a list with the legal moves possible for the given piece (and its position) in the upwards direction
     *
     * @param pieceToMove
     * @param markedPoint the piece's position
     * @return a list of points representing the legal moves
     */
    private void up(Piece pieceToMove, Point markedPoint, int iterations){
        for(int i = markedPoint.y - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Point p = new Point(markedPoint.x, i);
            if(addPoint(p,pieceToMove))break;
        }
    }

     /**
     * Creates a list with the legal moves possible for the given piece (and its position) in the downwards direction
     *
     * @param pieceToMove
     * @param markedPoint the piece's position
     * @return a list of points representing the legal moves
     */
    private void down(Piece pieceToMove, Point markedPoint, int iterations){
        for(int i = markedPoint.y + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(markedPoint.x, i);
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void left(Piece pieceToMove, Point markedPoint, int iterations){
        for(int i = markedPoint.x - 1; i >= 0 && iterations > 0; i--,iterations--) {
            Point p = new Point(i, markedPoint.y);
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void right(Piece pieceToMove, Point markedPoint,int iterations){
        for(int i = markedPoint.x + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(i, markedPoint.y);
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void upLeft(Piece pieceToMove, Point markedPoint, int iterations){
        Point p = new Point(markedPoint.x, markedPoint.y);
        for(int i = 0; i < 8 && iterations > 0; i++,iterations--) {
            p.x--;
            p.y--;
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void upRight(Piece pieceToMove, Point markedPoint,int iterations){
        Point p = new Point(markedPoint.x, markedPoint.y);
        for(int i = 0; i < 8 && iterations > 0; i++,iterations--) {
            p.x++;
            p.y--;
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void downRight(Piece pieceToMove, Point markedPoint,int iterations){
        Point p = new Point(markedPoint.x, markedPoint.y);
        for(int i = 0; i < 8 && iterations > 0; i++,iterations--) {
            p.x++;
            p.y++;
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void downLeft(Piece pieceToMove, Point markedPoint, int iterations){
        Point p = new Point(markedPoint.x, markedPoint.y);

        for(int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x--;
            p.y++;
            if(addPoint(p,pieceToMove))break;
        }
    }


    private boolean addPoint(Point p, Piece pieceToMove){
        boolean breakLoop;      //Used just to be extra clear, instead of return false or true
        if(p.x >= 0 && p.x < 8 && p.y >= 0 && p.y < 8) {
            if (boardMap.get(p) == null) {
                points.add(new Point(p.x, p.y));
                breakLoop = false;
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                points.add(new Point(p.x, p.y));
                breakLoop = true;
            } else {
                breakLoop = true;
            }
        }   else {
            breakLoop = true;
        }
            return breakLoop;
    }
}
