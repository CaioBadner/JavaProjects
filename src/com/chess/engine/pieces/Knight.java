package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.Side;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

public class Knight extends Piece {
	
	//this is where we teach the knight about its limitations and his L shape movement
	//movement [0] represents Nc3 to Nd5 for example, and the movements rotate clockwise until the last one 
	//which would be Nd5 to Nc7
	private static final int[][] EIGHT_KNIGHT_MOVES = {{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};
	
	private static final boolean isRecursive = false;
	
	public Knight(Tile currentTile, final Side pieceSide) {
		super(currentTile, pieceSide);
	}
	
	@Override
	public List<Move> calculateLegalMoves(Board board) {
		
		return calculateMyMoves(board, EIGHT_KNIGHT_MOVES, isRecursive);
	}
	
}
