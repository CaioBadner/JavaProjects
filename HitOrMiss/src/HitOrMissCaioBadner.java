import java.util.Random;

import javax.swing.JOptionPane;

public class HitOrMissCaioBadner {

	public static void main(String[] args) {
		
		JOptionPane.showMessageDialog(null, "\n" + "\n         GuessingGame 2.0               \n  "
										  + "\n ","GuessingGame 2.0", 3);
		
		final String title = "GuessingGame 2.0";
		int difficultyLevel = getValidDifficultyLevel();
		int maxAmountOfGuesses = getAmountOfGuessesByDifficultyLevel(difficultyLevel);
		
		//this is the main loop of the game, he will get one full round and 
		//then be asked if he wants to play again in the same difficulty
		do {
			
		//here we define the most important variable of the game, 
		//this number cannot be larger than 9
			//brain only works with 3 so far
		int numberLength = 3;
		
		int compNumber = generateValidComputerNumber(numberLength);
		
		//here we create the basic variables for this round
		int userGuess = 0, amountOfHits = 0, amountOfNearHits = 0;
		int currentRound = 1;
		boolean isUserWon = false;
		
		
		
		JOptionPane.showMessageDialog(null, "I am thinking of a " + numberLength + " digit number. "
				+ "\nTry to guess it! You have " + maxAmountOfGuesses + " guesses!"
			    + "\nOh, just a tip, there are NO repeated digits ;) ", title, 1);
		
		Brain wilson = new Brain (numberLength, maxAmountOfGuesses);
		
		
		
		// 1st condition : I didn't run out of guesses
				// 2nd condition : I didn't win
		while(currentRound<=maxAmountOfGuesses && !isUserWon) {
			
			//here we update the table of results, like the newest edition of a newspaper
			//tableOfResults[0][n] is a row that for now is empty but in the future it will be the place where the results are written
			if (currentRound > 1) {
				wilson.setTable(currentRound - 2, userGuess,amountOfHits,amountOfNearHits);
			} else {
				System.out.println("If I were you, I would guess 123");
			}
			
			userGuess = getValidUserGuess(numberLength);
		
			int[] results = calcResults(compNumber, userGuess, numberLength); 
			amountOfHits = results[0];
			amountOfNearHits = results[1];
			
			if (amountOfHits == numberLength) {
				System.out.println("\nRound " + currentRound + " | Your Guess: " + userGuess + " | Hits: " 
						+ amountOfHits + " | Near Hits: " + amountOfNearHits + " | Rounds left: " 
						+ (maxAmountOfGuesses - currentRound));
				isUserWon = true;
			}
			else {
				System.out.println("\nRound " + currentRound + " | Your Guess: " + userGuess + " | Hits: " 
						+ amountOfHits + " | Near Hits: " + amountOfNearHits + " | Rounds left: " 
						+ (maxAmountOfGuesses - currentRound));
				currentRound++;
			}
			
			
		
		}
		
		System.out.println("");
		
		if (isUserWon) {
			System.out.println("Guess nº" + currentRound + " (" + userGuess + ") was correct.");
			JOptionPane.showMessageDialog(null, "Nice! You got it right!! My number was " + compNumber,	
																							title, 2);
			if (currentRound == 1) {
				JOptionPane.showConfirmDialog(null, "Did you cheat?", title, 0);
			}
		}
		else {
			System.out.println("You have no more guesses. The answer was " + compNumber);
			JOptionPane.showMessageDialog(null, "It's ok, try an easier level next time... My number was " 
												+ compNumber, title, 0);
		}
	
		} while (JOptionPane.showConfirmDialog(null, "Wanna play again?", title,JOptionPane.YES_NO_OPTION) == 0);
	}


	
	private static int [] calcResults(int compNumber, int userGuess, int length) {
	
		//we initialise the main variables
		int amountOfHits = 0, amountOfNearHits = 0;
		int userDigits, compDigits;
		int compCopy = compNumber;
		
		//and we do two loops, one for each number
		for (int i = 0; i < length; i++) {
			//every time we get one digit from the 1st number, we run it against all the digits of the second number
			userDigits = userGuess % 10;
		
			for (int j = 0; j < length; j++) {
				compDigits = compCopy % 10;
				
				//here we check both possibilities
				if (userDigits == compDigits) {
					if (i == j) {
						amountOfHits++;
					} else {
						amountOfNearHits++;
					}
					//we break to get out of the loop without wasting time on the rest of the check
					//since both numbers have no repeated digits, there is no need to continue checking after one match
					break;
				}
				
				compCopy = compCopy / 10;			
			}
			//here we update the copy for the next loop
			compCopy = compNumber;
			userGuess = userGuess / 10;
		}
		//then we couple up the results in this nice array and return it safely
		int[] results = {amountOfHits, amountOfNearHits};
		
		return results;
	}

	
	
	private static int getValidUserGuess(int length) {
		
		String USER_TEXT = "Please enter a " + length + " digit number with no repeating digits";
		
		String strUserGuess = JOptionPane.showInputDialog(null,USER_TEXT,"GuessingGame 2.0", 1);
		
		int userGuess = Integer.parseInt(strUserGuess);
		
		while(!isNumberValid(userGuess, length)) {
			strUserGuess = JOptionPane.showInputDialog(null,"Invalid number. " 
															+ USER_TEXT,"GuessingGame 2.0", 0);
			userGuess = Integer.parseInt(strUserGuess);
		}
		
		return userGuess;
	}

	
	
	
	
	//this is the unified method to check if both the computer's number and the user's number are valid
	//it collects the number and the length used as the counter 
	public static boolean isNumberValid(int num, int length) {
		
		
		
		//we make an array with 10 cells for each different digit
		int [] digitCounter = new int[10];
		int digit;
	
		//we run a loop to check each digit
		while (num > 0) {
			
			length --;
			
			//if at any moment during this loop length is already at zero that means that it has less digits than length
			if (length < 0) {
				return false;
			}
			
			//here we extract the rightmost digit and add one to the correspondent cell in the digit array
			digit = num % 10;
			digitCounter[digit] ++;
		
			//then we use it to know if we already stopped by the same number twice
			if (digitCounter[digit] > 1) {
				return false;
			}
			num = num / 10;
			
			
		}
		//and here, after it passed both tests of the loop, we do one final check to make sure the number doesn't have more than 4 digits
		if (length > 0) {
			return false;
		}
		
		//if all checks fail then it appears we have a number with the right length and no repeating digits
		return true;
	}

	private static int generateValidComputerNumber(int length) {
		Random rnd = new Random();
		int num, maxRandom = 1;
		for (int i = 0; i < length; i++) {
			maxRandom = maxRandom * 10;
		}
		do  {
			num = rnd.nextInt(maxRandom - (maxRandom/10)) + (maxRandom/10+1); 
		} while (!isNumberValid(num, length));
		
		return num;
	}

	private static int getAmountOfGuessesByDifficultyLevel(int difficultyLevel) {
		if (difficultyLevel == 1) {
			return 12;
		}
		
		if (difficultyLevel == 2) {
			return 9;
		}

		return 7;
	}

	private static int getValidDifficultyLevel() {
		
		String DIFF_TEXT = "Please choose difficulty level : \n"+
							"1.Easy - 12 guesses\n"+
							"2.Medium - 9 guesses\n"+
							"3.Hard - 7 guesses";
		
		String strDifficultyLevel = JOptionPane.showInputDialog(null,DIFF_TEXT,"GuessingGame 2.0", 1);
		
		int difficultyLevel = Integer.parseInt(strDifficultyLevel);
		
		while(difficultyLevel<1 || difficultyLevel>3) {
			strDifficultyLevel = JOptionPane.showInputDialog(null,"Invalid number, it should be 1-3\n"
					+ DIFF_TEXT,"GuessingGame 2.0", 0);
			difficultyLevel = Integer.parseInt(strDifficultyLevel);
		}
		
		return difficultyLevel;
	}

}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

