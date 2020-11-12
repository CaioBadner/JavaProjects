package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.Side;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

public class Rook extends Piece{

	private static final int[][] FOUR_ROOK_LINES = {{1,0},{-1,0},{0,-1},{0,1}};
	
	private static final boolean isRecursive = true;
	
	public Rook(Tile currentTile, Side pieceSide) {
		super(currentTile, pieceSide);
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		
		return calculateMyMoves(board, FOUR_ROOK_LINES, isRecursive);
		
	}
	
}
