package com.chess.engine.pieces;

import com.chess.engine.Side;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.List;

public class King extends Piece{

	private static final int[][] EIGHT_ROYAL_DIRECTIONS = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
	
	private static final boolean isRecursive = false;
	
	public King(Tile currentTile, Side pieceSide) {
		super(currentTile, pieceSide);
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		
		return calculateMyMoves(board, EIGHT_ROYAL_DIRECTIONS, isRecursive);
		
	}
	

	
	
}
