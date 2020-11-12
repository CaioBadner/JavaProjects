package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class BlackPlayer extends Player{

	public BlackPlayer(Board board) {
		super(board, board.getBlackPieces(), board.getWhitePieces(), board.getBlackLegalMoves(), board.getWhiteLegalMoves());
		board.getWhitePlayer().setOppMoves(board.getBlackLegalMoves());
	}

}
