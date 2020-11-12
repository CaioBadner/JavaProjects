package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.Side;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

public abstract class Piece {

	protected Tile currentTile;
	
	protected final Side pieceSide;
	
	protected boolean isPinned;
	
	public List<Move> legalMoves;
	
	Piece(Tile currentTile, final Side pieceSide) {
		this.currentTile = currentTile;
		this.pieceSide = pieceSide;
	}
	
	public abstract List<Move> calculateLegalMoves(Board board);
	
	public List<Move> calculateMyMoves(Board board, int[][] MY_MOVES, boolean isRecursive) {
		
			List<Move> legalMoves = new ArrayList<>();
			
			if (!this.isPinned) {
				
				
				int file = this.getCurrentTile().getFile(); 
				int rank = this.getCurrentTile().getRank();
				int futureFile;
				int futureRank;
				Tile futureTile;
			
				for (int[] move : MY_MOVES) {
				
					futureFile = file + move[0];
					futureRank = rank + move[1];
					
					try {
						futureTile = board.getTile(futureFile, futureRank);
					} catch (ArrayIndexOutOfBoundsException ex) {
						continue;
					}
					
					if (futureTile.getPieceOnTile() == null) {
						legalMoves.add(new Move(this.currentTile, futureTile, false, 
								isCheck(board, futureFile, futureRank, MY_MOVES, isRecursive), isCheckMate()));
						this.getCurrentTile().addPieceAiming(this);
						if (isRecursive) {
							tileSequencer(board, legalMoves, move, futureFile + move[0], futureRank + move[1], MY_MOVES);
						}
					} else if (futureTile.getPieceOnTile().getPieceSide() != this.getPieceSide()) {
						legalMoves.add(new Move(this.currentTile, futureTile, true, false, false));
						this.getCurrentTile().addPieceAiming(this);
					}  
				}
			}	
		return legalMoves;
	}
	
	public List<Move> getLegalMoves() {
		return legalMoves;
	}

	public void setLegalMoves(List<Move> legalMoves) {
		this.legalMoves = legalMoves;
	}

	public void setCurrentTile(Tile currentTile) {
		this.currentTile = currentTile;
	}

	private boolean isCheckMate() {
		// TODO Auto-generated method stub
		return false;
	}

	private boolean isCheck(Board board, int file, int rank, int[][] MY_MOVES, boolean isRecursive) {
		
		if (!this.getClass().getSimpleName().contentEquals("King")) {
			
			int futureFile;
			int futureRank;
			Tile futureTile;
			
			for (int[] move : MY_MOVES) {
				
				futureFile = file + move[0];
				futureRank = rank + move[1];
				
				do {
					try {
						futureTile = board.getTile(futureFile, futureRank);
					} catch (ArrayIndexOutOfBoundsException ex) {
						break;
					}
					
					if (futureTile.getPieceOnTile() != null) {
						if (futureTile.getPieceOnTile().getPieceSide() != this.getPieceSide() &&
								futureTile.getPieceOnTile().getClass().getSimpleName().contentEquals("King")) {
							System.out.println(this.getPieceSide().toString() + " " + this.getClass().getSimpleName() + ": Found the enemy king at " 
								+ futureTile.getPieceOnTile().currentTile.getTileName());
							return true;
						} 
					} 
					
					futureFile += move[0];
					futureRank += move[1];
					
				} while (isRecursive && futureFile < 8);
				
			}
		}
		return false;	
	}

	//this is the special constructor that only the pawns will use 
	//because they have different checks for movement and captures
	public List<Move> calculateMyMoves(Board board, int[][] MY_MOVES, int[][] MY_CAPTURES, boolean isRecursive) {
		
			List<Move> legalMoves = new ArrayList<>();
			
			if (!this.isPinned) {
				
				
				int file = this.getCurrentTile().getFile(); 
				int rank = this.getCurrentTile().getRank();
				int futureFile;
				int futureRank;
				Tile futureTile;
				
				for (int[] move : MY_MOVES) {
				
							
					futureFile = file + move[0];
					futureRank = rank + move[1];
					
					try {
						futureTile = board.getTile(futureFile, futureRank);
					} catch (ArrayIndexOutOfBoundsException ex) {
						continue;
					}
					
					if (futureTile.getPieceOnTile() == null) {
						legalMoves.add(new Move(this.currentTile, futureTile, false, 
								isCheck(board, futureFile, futureRank, MY_CAPTURES, isRecursive), false));
					} else {
						break;
					}
					
					if (this.getPieceSide() == Side.WHITE) {
						if (rank != 1) {
							break;
						}	
					} else {
						if (rank != 6) {
							break;
						}
					}						
				} 
				
				for (int[] move : MY_CAPTURES) {
					
								
					futureFile = file + move[0];
					futureRank = rank + move[1];
					
					try {
						futureTile = board.getTile(futureFile, futureRank);
					} catch (ArrayIndexOutOfBoundsException ex) {
						continue;
					}
					
					if (futureTile.getPieceOnTile() != null && futureTile.getPieceOnTile().getPieceSide() != this.getPieceSide()) {
						legalMoves.add(new Move(this.currentTile, futureTile, true, 
								isCheck(board, futureFile, futureRank, MY_CAPTURES, isRecursive), false));
						this.getCurrentTile().addPieceAiming(this);
					}
				}
			}		
		
			return legalMoves;
		}
		
	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	public Tile getCurrentTile() {
		return this.currentTile;
	}

	public Side getPieceSide() {
		return this.pieceSide;
	}

	//this is the recursive function that calculates the moves for the bishops, queens and rooks
	public List<Move> tileSequencer(Board board, List<Move> legalMoves, int [] move, int futureFile, int futureRank, int [][] MY_MOVES) {
		
		Tile futureTile;
		
		try {
			futureTile = board.getTile(futureFile, futureRank);
		} catch (ArrayIndexOutOfBoundsException ex) {
			return legalMoves;
		}
		
		if (futureTile.getPieceOnTile() == null) {
			legalMoves.add(new Move(this.currentTile, futureTile, false, 
					isCheck(board, futureFile + move[0], futureRank + move[1], MY_MOVES, true), false));
			this.getCurrentTile().addPieceAiming(this);
			return tileSequencer(board, legalMoves, move, futureFile + move[0], futureRank + move[1], MY_MOVES);
		} else if (futureTile.getPieceOnTile().getPieceSide() != this.getPieceSide()) {
			legalMoves.add(new Move(this.currentTile, futureTile, true, false, false));
			this.getCurrentTile().addPieceAiming(this);
		}  
		return legalMoves;
	}
	
	private boolean isCheckSequencer(Board board, int[] move, int futureFile, int futureRank) {

		Tile futureTile;
		
		try {
			futureTile = board.getTile(futureFile, futureRank);
		} catch (ArrayIndexOutOfBoundsException ex) {
			return false;
		}
		
		//System.out.println("Check possible check for " + this.getClass().getSimpleName() + " from tile " + board.getTile(futureFile, futureRank).getTileName());
		if (futureTile.getPieceOnTile() != null) {
			
				
			if (futureTile.getPieceOnTile().getPieceSide() != this.getPieceSide() && 
				futureTile.getPieceOnTile().getClass().getSimpleName().contentEquals("King")) {
					return true;
			} 
		} else {
			return isCheckSequencer(board, move, futureFile + move[0], futureRank + move[1]);
		} 
		
		return false;
	}

	
	//every piece has an individual way of printing back their own current legal moves
	public void printMoves(List<Move> legalMoves) {
		String pieceName = this.getClass().getSimpleName();
		
		if (pieceName.length() == 9) {
			pieceName = "Pawn";
		}
		
		if (legalMoves.size() > 0) { 
		
			System.out.println(this.getPieceSide() + " " + pieceName);
			
			for (int i = 0; i < legalMoves.size(); i++) {
				String typeOfMove = "";
				if (legalMoves.get(i).isCapture()) {
					
					typeOfMove = "CAPTURE " + legalMoves.get(i).getEnemyPiece();
				} else if (legalMoves.get(i).isCheck()) {
					typeOfMove = "CHECK";
				}
				
				System.out.println(legalMoves.get(i).getCurrentTile().getTileName() + " -> " 
						+ legalMoves.get(i).getPossibleTile().getTileName() + " " + typeOfMove);
				}
			System.out.println();
		}
		
	}



	
}
