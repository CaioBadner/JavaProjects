package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.Side;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

public class Queen extends Piece{

	private static final int[][] EIGHT_ROYAL_DIRECTIONS = {{1,0},{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1}};
	
	private static final boolean isRecursive = true;
	
	public Queen(Tile currentTile, Side pieceSide) {
		super(currentTile, pieceSide);
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		
		return calculateMyMoves(board, EIGHT_ROYAL_DIRECTIONS, isRecursive);
		
	}

	

}
