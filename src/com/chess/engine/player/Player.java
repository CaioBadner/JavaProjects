package com.chess.engine.player;

import java.util.Collection;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.*;

public abstract class Player {
	
	public String playerName;
	protected Board board;
	protected King myKing;
	protected Collection<Piece> myPieces;
	protected Collection<Piece> oppPieces;
	protected Collection<Move> myMoves;
	protected Collection<Move> oppMoves;
	
	//UMA ZONA FICOU
	public Player(Board board, Collection<Piece> myPieces, Collection<Piece> oppPieces, Collection<Move> myMoves, Collection<Move> oppMoves) {
		this.board = board;
		this.myPieces = myPieces;
		this.oppPieces = oppPieces;
		this.myKing = findHeirToTheThrone(board);
		this.myMoves = myMoves;
		this.oppMoves = oppMoves;
	}

	protected King findHeirToTheThrone(Board board) {
		for (Piece piece : myPieces) {
			if (piece.getClass().getSimpleName().contentEquals("King")) {
				return (King) piece;
			}
		}
		return null;
	}
	
	
	
}
