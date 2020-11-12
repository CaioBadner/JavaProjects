package com.chess.engine.pieces;

import com.chess.engine.Side;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.List;

public class Bishop extends Piece{

	private static final int[][] FOUR_BISHOP_DIAGONALS = {{1,1},{1,-1},{-1,-1},{-1,1}};
	
	private static final boolean isRecursive = true;
	
	public Bishop(Tile currentTile, Side pieceSide) {
		super(currentTile, pieceSide);
	}

	
	@Override
	public List<Move> calculateLegalMoves(Board board) {
		
		return calculateMyMoves(board, FOUR_BISHOP_DIAGONALS, isRecursive);
		
	}

}
