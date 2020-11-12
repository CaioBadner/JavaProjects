package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;

import com.chess.engine.Side;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.*;
import com.chess.engine.player.*;

public class Board {

	private Tile[][] board;
	
	private final String fileLetters = "abcdefgh";
	
	private Collection<Piece> whitePieces;
	private Collection<Piece> blackPieces;
	private Collection<Move> whiteLegalMoves;
	private Collection<Move> blackLegalMoves;
	
	private WhitePlayer whitePlayer;
	private BlackPlayer blackPlayer;
	
	public Board (int size) {
		this.board = makeNewEmptyBoard (size);
		//weirdPosition();
		classicalSetUp();
		this.whitePieces = getPieces(this, Side.WHITE);
		this.blackPieces = getPieces(this, Side.BLACK);
		this.whiteLegalMoves = getLegalMoves(whitePieces);
		this.blackLegalMoves = getLegalMoves(blackPieces);
		this.whitePlayer = new WhitePlayer(this);
		this.blackPlayer = new BlackPlayer(this);
	}
	
	public Tile [][] makeNewEmptyBoard(int size) {
		
		Tile [][] newBoard = new Tile [size][size];
		String tileName;
		
		for (int file = 0; file < size; file++) {
			char fileChar = fileLetters.charAt(file);
			for (int rank = 0; rank < size; rank++) {
				tileName = "" + fileChar + (rank + 1);
				newBoard[file][rank] = new Tile(tileName, file, rank);
			}
		}

		return newBoard;
	}
	
	private void classicalSetUp() {
		this.createPieceOnTile("a3", Side.WHITE, "Rook");
		this.createPieceOnTile("c1", Side.WHITE, "Knight");
		this.createPieceOnTile("c1", Side.WHITE, "Bishop");
		this.createPieceOnTile("d1", Side.WHITE, "Queen");
		this.createPieceOnTile("e1", Side.WHITE, "King");
		this.createPieceOnTile("f1", Side.WHITE, "Bishop");
		this.createPieceOnTile("g1", Side.WHITE, "Knight");
		this.createPieceOnTile("h1", Side.WHITE, "Rook");
		this.createPieceOnTile("a2", Side.WHITE, "Pawn");
		this.createPieceOnTile("b2", Side.WHITE, "Pawn");
		this.createPieceOnTile("c2", Side.WHITE, "Pawn");
		this.createPieceOnTile("d2", Side.WHITE, "Pawn");
		this.createPieceOnTile("e4", Side.WHITE, "Pawn");
		this.createPieceOnTile("f2", Side.WHITE, "Pawn");
		this.createPieceOnTile("g2", Side.WHITE, "Pawn");
		this.createPieceOnTile("h2", Side.WHITE, "Pawn");

		
		this.createPieceOnTile("a8", Side.BLACK, "Rook");
		this.createPieceOnTile("b8", Side.BLACK, "Knight");
		this.createPieceOnTile("c8", Side.BLACK, "Bishop");
		this.createPieceOnTile("d8", Side.BLACK, "Queen");
		this.createPieceOnTile("f7", Side.BLACK, "King");
		this.createPieceOnTile("f8", Side.BLACK, "Bishop");
		this.createPieceOnTile("g8", Side.BLACK, "Knight");
		this.createPieceOnTile("h8", Side.BLACK, "Rook");
		this.createPieceOnTile("a7", Side.BLACK, "Pawn");
		this.createPieceOnTile("b7", Side.BLACK, "Pawn");
		this.createPieceOnTile("c7", Side.BLACK, "Pawn");
		this.createPieceOnTile("d7", Side.BLACK, "Pawn");
		//this.putPieceOnTile("e6", Side.BLACK, "Pawn");
		//this.putPieceOnTile("f7", Side.BLACK, "Pawn");
		this.createPieceOnTile("g7", Side.BLACK, "Pawn");
		this.createPieceOnTile("h7", Side.BLACK, "Pawn");
	}

	private void weirdPosition() {
		
		this.createPieceOnTile("a4", Side.WHITE, "Rook");
		this.createPieceOnTile("b3", Side.WHITE, "Knight");
		this.createPieceOnTile("c2", Side.WHITE, "Bishop");
		this.createPieceOnTile("d6", Side.WHITE, "Queen");
		this.createPieceOnTile("d7", Side.WHITE, "King");
		this.createPieceOnTile("h3", Side.WHITE, "Bishop");
		this.createPieceOnTile("f3", Side.WHITE, "Knight");
		this.createPieceOnTile("h1", Side.WHITE, "Rook");
		this.createPieceOnTile("a5", Side.WHITE, "Pawn");
		this.createPieceOnTile("b4", Side.WHITE, "Pawn");
		this.createPieceOnTile("c3", Side.WHITE, "Pawn");
		this.createPieceOnTile("d2", Side.WHITE, "Pawn");
		this.createPieceOnTile("e4", Side.WHITE, "Pawn");
		this.createPieceOnTile("f2", Side.WHITE, "Pawn");
		this.createPieceOnTile("g3", Side.WHITE, "Pawn");
		this.createPieceOnTile("h2", Side.WHITE, "Pawn");

		
		this.createPieceOnTile("a8", Side.BLACK, "Rook");
		this.createPieceOnTile("c6", Side.BLACK, "Knight");
		this.createPieceOnTile("b7", Side.BLACK, "Bishop");
		this.createPieceOnTile("d8", Side.BLACK, "Queen");
		this.createPieceOnTile("e8", Side.BLACK, "King");
		this.createPieceOnTile("g7", Side.BLACK, "Bishop");
		this.createPieceOnTile("f6", Side.BLACK, "Knight");
		this.createPieceOnTile("h8", Side.BLACK, "Rook");
		this.createPieceOnTile("a6", Side.BLACK, "Pawn");
		this.createPieceOnTile("b6", Side.BLACK, "Pawn");
		this.createPieceOnTile("c5", Side.BLACK, "Pawn");
		this.createPieceOnTile("d4", Side.BLACK, "Pawn");
		this.createPieceOnTile("e5", Side.BLACK, "Pawn");
		this.createPieceOnTile("f7", Side.BLACK, "Pawn");
		this.createPieceOnTile("g6", Side.BLACK, "Pawn");
		this.createPieceOnTile("h3", Side.BLACK, "Pawn");
	}


	private Collection<Move> getLegalMoves(Collection<Piece> pieces) {
		Collection<Move> legalMoves = new ArrayList<Move>();
		
		for (Piece piece : pieces) {
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}
			
		return legalMoves;
	}

	private static Collection<Piece> getPieces(Board board, Side side) {
		Collection<Piece> collection = new ArrayList<Piece>();
		for (int file = 0; file < board.board.length; file++) {
			for (int rank = 0; rank < board.board[0].length; rank++) {
				if (board.getTile(rank, file).getPieceOnTile() != null) {
					Piece piece = board.getTile(rank, file).getPieceOnTile();
					if (piece.getPieceSide() == side) {
						collection.add(piece);
					}
				}
			}
		}
		return collection;
	}

	public Tile getTile (String tileName) {
		
		char fileChar = tileName.charAt(0);
		int file = fileLetters.indexOf(fileChar);
		
		String rankStr = "" + tileName.charAt(1);
		int rank = Integer.parseInt(rankStr) - 1;
		
		return this.board[file][rank];
	}
	
	public Tile getTile (int file, int rank) {
		
		return this.board[file][rank];
	}
	
	public void createPieceOnTile(String tileName, Side pieceSide, String piece) {
		
		
		if (piece == "Knight") {
			this.getTile(tileName).setPieceOnTile(new Knight(this.getTile(tileName), pieceSide));
		} else if (piece == "Bishop") {
			this.getTile(tileName).setPieceOnTile(new Bishop(this.getTile(tileName), pieceSide));
		} else if (piece == "Rook") {
			this.getTile(tileName).setPieceOnTile(new Rook(this.getTile(tileName), pieceSide));
		} else if (piece == "Queen") {
			this.getTile(tileName).setPieceOnTile(new Queen(this.getTile(tileName), pieceSide));
		} else if (piece == "King") {
			this.getTile(tileName).setPieceOnTile(new King(this.getTile(tileName), pieceSide));
		} else if (piece == "Pawn" && pieceSide == Side.WHITE) {
			this.getTile(tileName).setPieceOnTile(new WhitePawn(this.getTile(tileName), pieceSide));
		} else if (piece == "Pawn" && pieceSide == Side.BLACK) {
			this.getTile(tileName).setPieceOnTile(new BlackPawn(this.getTile(tileName), pieceSide));
		}
		
	}

	public void printAllLegalMoves(Side side) {
		
		System.out.println(side.toString() + " has " + this.getWhiteLegalMoves().size() + " legal moves");
		System.out.println();
		
		for (int file = 0; file < board.length; file++) {
			for (int rank = 0; rank < board[0].length; rank++) {
				Piece pieceOnTile = this.getTile(rank,file).getPieceOnTile();
				if (pieceOnTile != null && 
						pieceOnTile.getPieceSide() == side) {
					pieceOnTile.printMoves(pieceOnTile.calculateLegalMoves(this));
				}
			}
		}
		
	}

	public Collection<Piece> getWhitePieces() {
		return this.whitePieces;
	}

	public void updateWhitePieces() {
		this.whitePieces = getPieces(this, Side.WHITE);
	}
	
	public Collection<Piece> getBlackPieces() {
		return this.blackPieces;
	}

	public void updateBlackPieces() {
		this.blackPieces = getPieces(this, Side.BLACK);
	}	

	public Collection<Move> getWhiteLegalMoves() {
		return this.whiteLegalMoves;
	}

	public void updatewhiteLegalMoves() {
		this.whiteLegalMoves = getLegalMoves(this.getWhitePieces());
	}
	
	public Collection<Move> getBlackLegalMoves() {
		return this.blackLegalMoves;
	}

	public void updateBlackLegalMoves() {
		this.blackLegalMoves = getLegalMoves(this.getBlackPieces());
	}

	public WhitePlayer getWhitePlayer() {
		return whitePlayer;
	}

	public BlackPlayer getBlackPlayer() {
		return blackPlayer;
	}

}
