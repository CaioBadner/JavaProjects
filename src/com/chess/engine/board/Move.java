package com.chess.engine.board;

public class Move {

	
	private Tile currentTile;
	private Tile possibleTile;
	private boolean isCapture, isCheck, isMate;
	private String enemyPiece;
	
	public Move (Tile currentTile, Tile possibleTile) {
		this.currentTile = currentTile;
		this.possibleTile = possibleTile;
	}
	
	public Move (Tile currentTile, Tile possibleTile, boolean isCapture, boolean isCheck, boolean isMate) {
		this.currentTile = currentTile;
		this.possibleTile = possibleTile;
		this.isCapture = isCapture;
		this.isCheck = isCheck;
		this.isMate = isMate;
		
		if (this.isCapture) {
			setEnemyPiece(this.possibleTile.getPieceOnTile().getClass().getSimpleName());
		}
	}
	
	
	
	public Tile getCurrentTile() {
		return currentTile;
	}

	public void setCurrentTile(Tile currentTile) {
		this.currentTile = currentTile;
	}

	public Tile getPossibleTile() {
		return possibleTile;
	}

	public void setPossibleTile(Tile possibleTile) {
		this.possibleTile = possibleTile;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

	public boolean isCapture() {
		return isCapture;
	}

	public void setCapture(boolean isCapture) {
		this.isCapture = isCapture;
	}

	public boolean isMate() {
		return isMate;
	}

	public void setMate(boolean isMate) {
		this.isMate = isMate;
	}

	public String getEnemyPiece() {
		return enemyPiece;
	}

	public void setEnemyPiece(String enemyPiece) {
		this.enemyPiece = enemyPiece;
	}

	

}
