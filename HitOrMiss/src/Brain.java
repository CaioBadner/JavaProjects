import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Brain {

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
	
	//this is a list of all possible guesses that will only be filled up if there are less than 50 guesses left
	private List <Integer> eliminatedGuesses;
	
	//and this is the main boolean array that will concentrate the definitive information that the brain has about the digits so far
	//one row for each number(0-9) and one column for each position that the digit can occupy in the answer
	//plus, another last column for overall information about the digit, a true in the last column means that this digit is out of all positions
	private boolean [][] eliminatedDigits;
	
	//this is where he will keep quick track of how many digits he is working with, to decide whether or not to start formulating guesses
	private int[] possibleDigitsSum;
	
	//this is a quick calculation of how many possible guesses are possible in total, and how many are left after the elimination 
	private int possibleGuesses;
	
	//and this is the mini guess list that he will prepare when there are a few options left
	private List <Integer> miniList;
	
	//this is where the brain will keep the history of past games to keep track of his form
	//private List <int [][]> gameHistory = new ArrayList <int[][]>();
		
		
	//this is a simple football inspired 'form' stat which will be filled with nothing but characters
	//and it will be a quick register of the last 5 games, in this format: W is a win, and L is a loss
	//so an example of this string, after 5 games could be - WLLWW - which means that the brain won the last two games
	//lost the two games before that and won the game that happened before that;
	//private String currentForm;
	
	
	public Brain(int length, int maxAmountOfGuesses) {
		
		this.length = length;
		
		//this is the table of results that the brain will be fed every round
		this.table = new int [maxAmountOfGuesses][length + 2];
		
		//and then we make the boolean array according to the length of the number we're dealing with
		this.eliminatedDigits = new boolean [10][length];
		
		//here we set the only special rule of this game, no number can ever start with the digit 0
		this.eliminatedDigits[0][length-1] = true;
		
		//and here we just keep track of how many digits have been eliminated from each position
		//this basically will be just a sum of all fields from the previous array to make the brain's life easier
		this.possibleDigitsSum = new int [length];
		
		this.eliminatedGuesses = new ArrayList <Integer>();
		
	}
	
	

	public void setTable(int round, int guess, int hits, int nearHits) {
		//this will receive the game table and break it down in digits
		this.round = round;
		
		int width = this.table[0].length;
		
		//here we add the current guess to the list of wrong guesses
		eliminatedGuesses.add(guess);

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
		
		//System.out.println("There are still " + possibleGuesses + " possible guesses at this point in the game");
			//here is just a quick update of the sum of the eliminated digits
		
		
		//here he decides whether to keep on guessing or to make a list of possible guesses
		if (possibleGuesses > 24) {
			System.out.println("\nIf I were you, I would guess " + generateValidGuess());
		} else {
			//if the list is empty then we generate a list
			if (miniList == null) {
				this.miniList = generateMiniList();
			}
			System.out.println("\nIf I were you, I would guess " + chooseGuessFromMiniList());
		}
	}

	
	private int chooseGuessFromMiniList() {
		
		if (miniList.size() == 0) {
			System.out.println("I am sorry, I am out of ideas... You are on your own now, good luck!");
			return 0;
		}
		
			int guess = miniList.get(0);
			miniList.remove(0);
			eliminatedGuesses.add(guess);
		
		return guess;
		
	}	
	
	
	//this will go down the eliminated digits table and generate every possible guess that 
	//is still valid, it will only run when the Brain is really close to the answer
	//this method can only run once per game
	private List <Integer> generateMiniList() {
		
		List <Integer> miniList = new ArrayList <Integer>();
		
		String strGuess = "";
		int guess; 
		int pos;
					


		for (int j = 0; j < eliminatedDigits.length; j++) {
			pos = 0;
			
			if (!eliminatedDigits[j][pos]) {
				
				for (int k = 0; k < eliminatedDigits.length; k++) {
					pos = 1;
					
					if (!eliminatedDigits[k][pos] && k != j) {
						
						for (int l = 1; l < eliminatedDigits.length; l++) {
							pos = 2;
							
							if (!eliminatedDigits[l][pos] && k != l && j != l) {
								
								strGuess = "" + l + k + j;
								guess = Integer.parseInt(strGuess);
								if (isGuessLogical(guess)) {
									miniList.add(guess);	
								} 
								
							}
						}
					}
				}
		}
				
				}
		
		return miniList;
		
	}



	//this is the basic function that will be used to throw numbers in the beginning of the game
	//it will return guesses that will be checked against the eliminatedDigits table and the past results
	public int generateValidGuess() {

		int realGuess;
		String strGuess;
		
		//this is to keep track of the random loops
		int counter = 0;
		
		
		//here we ask for our random digits, after they were validated
		int digit;

				
		do {
			
			//this will keep track of which digits already have been used to make 
			//sure we get a valid guess with no repeating digits
			//basically this is how we teach the Brain about the rules of the game 
			boolean [] digitsAlreadyUsed = new boolean [10]; 
			
			strGuess = "";
			for (int i = 0; i < length; i++) {
				digit = getValidDigit(digitsAlreadyUsed, i, counter++);
				strGuess = digit + strGuess;
				digitsAlreadyUsed[digit] = true;
			}
			
			realGuess = Integer.parseInt(strGuess);
			
			
			//and we are going to take as many rounds here as necessary in this last check, 
			//otherwise the guess would be wrong and the brain would be hurting the player
			//so the brain doesn't mind even if he has to repeat this a million times
		} while (!isGuessLogical(realGuess));
		
		return realGuess;
	}

	//and this is the counterpoint to the previous function, this is the random function that gives digits back to the other one
	private int getValidDigit(boolean digitsAlreadyUsed[], int position, int counter) {
		
		Random brainFart = new Random();
		int digit = brainFart.nextInt(10);
		boolean foundDigit = false;
		
		//here we ask if this digit is already eliminated and if we have already used it in this answer
		while (!foundDigit) {
			if (eliminatedDigits[digit][position] == false && !digitsAlreadyUsed[digit]) {
				foundDigit = true;
			} else {
				
				
				//if the number is not valid, we tell him to go one up until he finds it. 
				// then next time he will go one down to make sure he won't get stuck getting the same digits
				if (counter % 2 == 0) {
					digit -= 1;
					if (digit < 0) {
						digit = 9;
					}
				} else {
					digit += 1;
					if (digit > 9) {
						digit = 0;
					}
				}
				
			}
		}
		return digit;
	}
	
	//here we go down the table and double check this guess against all the other past guesses
	//to see if it makes sense to try this guess, 
	//even though it is valid and all its digits are in possible positions
	private boolean isGuessLogical(int guess) {
		
		if (eliminatedGuesses.contains(guess)) {
			return false;
		}
		
		int digit, hitsCounter = 0, nearHitsCounter = 0;
		int guessCopy = guess;
		
		
		
		// the main loop will run all the previous rounds, from round 1 to the current
		for (int i = 0; i <= round; i++) {
			
		
			//this will do one loop for each digit of the guess
			// so J is the position of the digit in the guess
			for (int j = 0; j < length; j++) {
				digit = guessCopy % 10;
				
				//and it will run against each digit of the previous guesses
				// here K is the position of the digits in the previous guesses
				for (int k = 0; k < length; k++) {
					
					//here we ask if this digit appeared in the guess
					if (digit == table[i][k]) {
						//and we add to the appropriate counter
						if (k == j) {
							hitsCounter++;
						} else {
							nearHitsCounter++;
						}
						//here we need this break to avoid double results
						break;
					}
				}
				
				if (hitsCounter > table[i][length] || nearHitsCounter > table[i][length+1]) {
					//System.out.println("\nI was thinking of " + guess + ", "
					//	+ "but I just realized we tried something similar on round " + (i+1));
					eliminatedGuesses.add(guess);
					return false;
				}
				guessCopy = guessCopy / 10;	
					
		}
			hitsCounter = 0;
			nearHitsCounter = 0;
			guessCopy = guess;
		}
			
		
		return true;
	}



	private void eliminateDigitsFromOtherPositions() {
		
		int digit;
		
		for (int i = 0; i < length; i++) {
			digit = table[round][i];
			for (int j = 0; j < eliminatedDigits[0].length; j++) {
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
		
			int counter = 0; 
			
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < length; j++) {
					if (i != table[round][j]) {
						counter++;
					}
				}
				if (counter == length) {
					for (int k = 0; k < length; k++) {
						eliminatedDigits[i][k] = true;
					}
					
				}
				counter = 0;
			}
	
	}

	
	public int[] getPossibleDigitsSum() {
		return possibleDigitsSum;
	}


	public int getPossibleGuesses() {
		
		//first we prepare the sum of the possible digits left
		for (int i = 0; i < eliminatedDigits[0].length; i++) {
			possibleDigitsSum[i] = 0;
			for (int j = 0; j < eliminatedDigits.length; j++) {
				if (eliminatedDigits[j][i] == false) {
					possibleDigitsSum[i] ++;
				}
			}
		}
		
		int possibleGuesses = 1;
		//and here we just multiply every field and get the total number of guesses still possible
		for (int i = 0; i < possibleDigitsSum.length; i++) {
			possibleGuesses *= possibleDigitsSum[i];
		}
		
		return possibleGuesses;
	}



	public int[][] getCurrentTable() {
		return table;
	}




	public List <Integer> getEliminatedGuesses() {
		return eliminatedGuesses;
	}
	
	public boolean[][] getEliminatedDigits() {
		return eliminatedDigits;
	}

	public void setEliminatedDigits(boolean[][] eliminatedDigits) {
		this.eliminatedDigits = eliminatedDigits;
	}

	
	//here are the history variables for future implementation
	/*
	public String getCurrentForm() {
		return currentForm;
	}

	public void updateCurrentForm(char result) {
		
		if (currentForm.length() == 5) {
			currentForm = "" + currentForm.charAt(1) + currentForm.charAt(2) + currentForm.charAt(3) + currentForm.charAt(4); 
		}
		
		currentForm = currentForm + result;
	}
	
	public List<int[][]> getGameHistory() {
		return gameHistory;
	}
	
	public int[][] getGameHistory(int round) {
		return gameHistory.get(round);
	}

	public void addToGameHistory(int[][] table) {
		gameHistory.add(table);
	}
	*/
	
	




	
	
}
