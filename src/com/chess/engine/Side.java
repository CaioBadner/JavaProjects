package com.chess.engine;

public enum Side {
	
	WHITE {
		@Override
		public String toString() {
			return "WHITE";
		}
	},
	BLACK {
		@Override
		public String toString() {
			return "BLACK";
		}
	};
	
	public abstract String toString();
	
	
}
