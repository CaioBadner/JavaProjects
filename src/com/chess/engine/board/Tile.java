package com.chess.engine.board;

import java.awt.List;
import java.util.ArrayList;

import com.chess.engine.pieces.Piece;

public class Tile {

	private final String tileName;
	
	private final int file, rank;
	
	private Piece pieceOnTile;
	
	private ArrayList<Piece> piecesAiming;
	
	Tile (String tileName, int file, int rank) {
		this.tileName = tileName;
		this.file = file;
		this.rank = rank;
		this.pieceOnTile = null;
		this.piecesAiming = new ArrayList<Piece>();
	}
	
	public static Tile createTile (String tileName, int file, int rank) {
		return new Tile(tileName, file, rank);
	}
	
	public String getTileName() {
		return this.tileName;
	}
	
	public int getFile() {
		return this.file;
	}

	public int getRank() {
		return this.rank;
	}
	
	public Piece getPieceOnTile() {
		return this.pieceOnTile;
	}
	
	public void setPieceOnTile (Piece piece) {
		this.pieceOnTile = piece;
	}
	
	public void addPieceAiming (Piece piece) {
		this.piecesAiming.add(piece);
	}
	
}
