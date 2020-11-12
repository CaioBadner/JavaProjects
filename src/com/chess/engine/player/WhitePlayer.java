package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class WhitePlayer extends Player{

	public WhitePlayer(Board board) {
		super(board, board.getWhitePieces(), board.getBlackPieces(), board.getWhiteLegalMoves(), board.getBlackLegalMoves());
	}

	public void setOppMoves(Collection<Move> blackLegalMoves) {
		this.oppMoves = blackLegalMoves;
	}

}
