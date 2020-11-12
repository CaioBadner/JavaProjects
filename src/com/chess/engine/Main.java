package com.chess.engine;

import com.chess.engine.board.*;



//import com.chess.engine.Side;

public class Main {

	public static Board board;
	
	public static void main(String[] args) {
		
		board = new Board(8);
		
		
		
		board.printAllLegalMoves(Side.WHITE);
		board.printAllLegalMoves(Side.BLACK);
			
		
	}

	
}
