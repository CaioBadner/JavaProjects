import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Brain {

	//these are the emotional variables - they are all naturally false
	private boolean isThinking, isHappy, isHungry, isConfident, isInterested;
	
	//this is how the brain keeps track of the results
	//the number will be sorted aligning the digits positions to their cells 
	//so for example, if the UserGuess was 123, then the number will be stored in this array like that
	//  
	//    [3][2][1][][]   because 3 was played in the position 0, so he will be stored in cell 0, 
	//    [3][2][1][0][2] the table will have one cell for each digit and two extra cells for 
	//       the hit and nearHit information. in this example the number 123 had 0 hits and 2 near hits
	//
	private int [][] table;
	
	//here is a quick way for the brain to know which round it is, it will be used throughout the class
	private int round;
	
	//and this for him to have quick access to the length of the number 
	private int length;
	
	//this is where the brain keeps the history of past games to keep track of his form
	private List <int [][]> gameHistory = new ArrayList <int[][]>();
	
	
	//this is a simple football inspired 'form' stat which will be filled with nothing but characters
	//and it will be a quick register of the last 5 games, in this format: W is a win, and L is a loss
	//so an example of this string, after 5 games could be - WLLWW - which means that the brain won the last two games
	//lost the two games before that and won the game that happened before that;
	private String currentForm;
	
	//this will be the flag for the brain to start formulating educated guesses
	private boolean knowsAllDigits;
	
	//this is a list of all possible guesses that will only be filled up if the brain decides that it makes sense to do it
	private List <Integer> myGuesses = new ArrayList <Integer>();
	
	//and this is the main boolean array that will concentrate the definitive information that the brain has about the digits so far
	//one row for each number(0-9) and one column for each position that the digit can occupy in the answer
	//plus, another last column for overall information about the digit, a true in the last column means that this digit is out of all positions
	private boolean [][] eliminatedDigits;
	
	//this is where he will keep quick track of how many digits he is working with, to decide whether or not to start formulating guesses
	private int[] possibleDigitsSum;
	
	//this is a quick calculation of how many possible guesses are possible in total, and how many are left after the elimination 
	private int possibleGuesses;
	
	
	
	
	public Brain(int length, int maxAmountOfGuesses) {
		
		this.length = length;
		
		//this is the table of results that the brain will be fed every round
		this.table = new int [maxAmountOfGuesses][length + 2];
		
		//and then we make the boolean array according to the length of the number we're dealing with
		this.eliminatedDigits = new boolean [10][length+1];
		
		//here we set the only special rule of this game, no number can ever start with the digit 0
		this.eliminatedDigits[0][length-1] = true;
		
		//and here we just keep track of how many digits  been eliminated from each position
		//this basically will be just a sum of all fields from the previous array to make the brain's life easier
		this.possibleDigitsSum = new int [length+1];
		
	}
	
	

	public void setTable(int round, int guess, int hits, int nearHits) {
		//this will receive the game table and break it down in digits
		this.round = round;
		
		int width = this.table[0].length;
					
		//this is the small loop that will break down the number in the first cells of the array
			for (int y = 0; y < width; y++) {
				if (y < width - 2) {
				int digit = guess % 10;
				this.table[round][y] = digit;
				guess = guess / 10;
				//then we copy the Amount of Hits and Near hits to the last two cells of the row
				//making effectively an improved version of the table for our brain to analyse with more ease
				} else if (y == width - 2) {
					this.table[round][y] = hits;
				} else {
					this.table[round][y] = nearHits;
				} 
			
			
			}
			
		//updating the table will set off a chain of events to make all the calculations and formulate a guess
		//so first thing we do is read the results and think of our course of action
		readResults();	
		}
	
	

	private void readResults() {
		
		//first we check all possible scenarios
		//if no digits returned anything we eliminate them completely
		if (table[round][length] + table[round][length + 1] == 0) {		
			eliminateTheseDigits();
		} 
		//or if we should do a simple elimination of each digit in their position because we got no hits
		else if (table[round][length] == 0) {		
			eliminateDigitsFromCurrentPositions();
		} 
		//or if we got no nearHits, then eliminate everybody from every position except the ones they were in
		else if (table[round][length + 1] == 0) {
			eliminateDigitsFromOtherPositions();
		} 
		//if we already know all digits because the sum of near hits and hits was equal to the length of the number
		else if (table[round][length] + table[round][length + 1] == length) {		
			eliminateEveryDigitExceptThese();
		}
		
		
		
		possibleGuesses = getPossibleGuesses();
		System.out.println("There are " + possibleGuesses + " possible guesses at this point in the game");
			//here is just a quick update of the sum of the eliminated digits
		
		
		//if (getTotalPossibleGuesses() < 100) {
		//	generateEducatedGuess();
		//} else {
			System.out.println("If I were you, I would guess " + generateValidGuess());
		//}
		
	}

	



	private int generateValidGuess() {
		Random brainFart = new Random();
		
		int realGuess;
		boolean [] digitsAlreadyUsed = new boolean [10]; 
		int digit = brainFart.nextInt(10);
		boolean foundPlace = false;
		int invertedGuess;
		do {
			
			invertedGuess = 0;
			
			for (int i = 0; i < eliminatedDigits[0].length - 1; i++) {
				while (!foundPlace) {
					
					if (digit > 9) {
						digit = digit - 10;
					}
					
					if (!eliminatedDigits[digit][i] && !digitsAlreadyUsed[digit]) {
						invertedGuess = invertedGuess * 10 + digit;
						foundPlace = true;
						digitsAlreadyUsed[digit] = true;
					} else { 
						digit += (brainFart.nextInt(3) + 1);
					} 
					
				}
				
				foundPlace = false;
				digit += (brainFart.nextInt(3) + 1);
			}
			
			realGuess = 0;
			
			while (invertedGuess != 0) {
				digit = invertedGuess % 10;
				realGuess = realGuess * 10 + digit;
				invertedGuess /= 10;
			}
			
			if (realGuess < 100) {
				realGuess = realGuess * 10;
			}
		
		} while (!isGuessValid(realGuess));
		
		
		
		//this will generate the appropriate guess but inverted
		
			//and before we send it, we need to reverse back to the brain's order [0][1][2]
			
			
			
		setMyGuesses(realGuess);
		return realGuess;
	}




	private boolean isGuessValid(int guess) 
	{
		/*if (!HitOrMissCaioBadner.isNumberValid(guess, length)) {
			return false;
		}*/
		if (myGuesses.contains(guess)) 
		{
			System.out.println("I was thinking of " + guess + ", "
					+ "but I just realized we tried this exact number on round " + (round + 1));
			return false;
		}
		
		int digit, counter = 0;
		int guessCopy = guess;
		
		for (int i = 0; i <= round; i++) 
		{
			for (int j = 0; j < table[0].length - 2; j++) {
				digit = guessCopy % 10;
				if (digit == table[i][j]) {
					counter++;
					if (counter > (table[i][length] + table[i][length+1])) {
						System.out.println("I was thinking of " + guess + ", "
							+ "but I just realized we tried something similar on round " + (round + 1));
						return false;
					}
				}
				guessCopy = guessCopy / 10;
			}
			counter = 0;
			guessCopy = guess;
		}
		
		return true;
	}



	private void eliminateDigitsFromOtherPositions() {
		
		int digit;
		
		for (int i = 0; i < length; i++) {
			digit = table[round][i];
			for (int j = 0; j < eliminatedDigits[0].length - 1; j++) {
				if (i != j) {
						eliminatedDigits[digit][j] = true;
				}
			}
		}
	
	}

	private void eliminateDigitsFromCurrentPositions() {
		
		int digit;
		
		for (int i = 0; i < length; i++) {
			digit = table[round][i];
			eliminatedDigits[digit][i] = true;
		}
		
	}

	private void eliminateTheseDigits() {
		
		int digit;
		
		for (int i = 0; i < length; i++) {
			digit = table[round][i];
			for (int j = 0; j < eliminatedDigits[0].length; j++) {
				eliminatedDigits[digit][j] = true;
			}
			
		}
	}
	
	private void eliminateEveryDigitExceptThese() {
		
		//int digit;
			
			for (int i = 0; i < 10; i++) {
				if (i != table[round][0] && i != table[round][1] && i != table[round][2]) {
					for (int j = 0; j < eliminatedDigits[0].length; j++) {
						eliminatedDigits[i][j] = true;
					}
				}
			}
	
	}

	public boolean doesItKnowAllDigits() {
		return knowsAllDigits;
	}



	public void setKnowsAllDigits(boolean knowsAllDigits) {
		this.knowsAllDigits = knowsAllDigits;
	}



	public int[] getPossibleDigitsSum() {
		return possibleDigitsSum;
	}



	
		
	
	public int getPossibleGuesses() {
		
		possibleGuesses = 0;
		
		for (int i = 0; i < eliminatedDigits[0].length - 1; i++) {
			possibleDigitsSum[i] = 0;
			for (int j = 0; j < eliminatedDigits.length; j++) {
				if (eliminatedDigits[j][i] == false) {
					possibleDigitsSum[i] ++;
				}
			}
		}
		
		int possibleGuesses = 1;
		
		for (int i = 0; i < possibleDigitsSum.length - 1; i++) {
			possibleGuesses *= possibleDigitsSum[i];
		}
		
		return possibleGuesses;
	}



	public int[][] getCurrentTable() {
		return table;
	}


	public List<int[][]> getGameHistory() {
		return gameHistory;
	}

	public void setGameHistory(List<int[][]> gameHistory) {
		this.gameHistory = gameHistory;
	}

	public List<Integer> getMyGuesses() {
		return myGuesses;
	}

	public void setMyGuesses(int guess) {
		this.myGuesses.add(guess);
	}

	public boolean[][] getEliminatedDigits() {
		return eliminatedDigits;
	}

	public void setEliminatedDigits(boolean[][] eliminatedDigits) {
		this.eliminatedDigits = eliminatedDigits;
	}

	public String getCurrentForm() {
		return currentForm;
	}

	public void setCurrentForm(String currentForm) {
		this.currentForm = currentForm;
	}
	
	
	//here are the emotional variables
	public boolean isThinking() {
		return isThinking;
	}

	public void setThinking(boolean isThinking) {
		this.isThinking = isThinking;
	}

	public boolean isHappy() {
		return isHappy;
	}

	public void setHappy(boolean isHappy) {
		this.isHappy = isHappy;
	}

	public boolean isHungry() {
		return isHungry;
	}

	public void setHungry(boolean isHungry) {
		this.isHungry = isHungry;
	}

	public boolean isConfident() {
		return isConfident;
	}

	public void setConfident(boolean isConfident) {
		this.isConfident = isConfident;
	}

	public boolean isInterested() {
		return isInterested;
	}

	public void setInterested(boolean isInterested) {
		this.isInterested = isInterested;
	}

	public int getRound() {
		return round;
	}


	
	
}
