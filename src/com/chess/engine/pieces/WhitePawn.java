package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.Side;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

public class WhitePawn extends Piece{

	private static final int[][] PAWN_MOVES = {{0,1},{0,2}};
	private static final int[][] PAWN_CAPTURES = {{-1,1},{1,1}};
	
	private static final boolean isRecursive = false;
	
	public WhitePawn(Tile currentTile, Side pieceSide) {
		super(currentTile, pieceSide);
	}

	@Override
	public List<Move> calculateLegalMoves(Board board) {
		
		return  calculateMyMoves(board, PAWN_MOVES, PAWN_CAPTURES, isRecursive);
		
	}
}
