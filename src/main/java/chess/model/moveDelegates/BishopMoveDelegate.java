package chess.model.moveDelegates;

import chess.model.Board;
import chess.model.Square;
import chess.model.MovementLogicUtil;

import java.util.ArrayList;
import java.util.List;

public class BishopMoveDelegate implements IMoveDelegate {

    @Override
    public List<Square> fetchMoves(Board board, Square squareToCheck, boolean pieceOnSquareHasMoved, boolean checkKingSuicide) {
        var legalSquares = new ArrayList<Square>();

        legalSquares.addAll(MovementLogicUtil.upLeft(board, squareToCheck, 7));
        legalSquares.addAll(MovementLogicUtil.upRight(board, squareToCheck, 7));
        legalSquares.addAll(MovementLogicUtil.downRight(board, squareToCheck, 7));
        legalSquares.addAll(MovementLogicUtil.downLeft(board, squareToCheck, 7));

        return legalSquares;
    }
}
