import javax.swing.JOptionPane;

public class Exercise01 {

	public static void main(String[] args) {

		String input1 = JOptionPane.showInputDialog("Enter a number");
		int a = input1.length();
		int num1 = Integer.parseInt(input1);
		String input2;
		do {
			input2 = JOptionPane.showInputDialog("The first number was " + num1 + "\n" + "\n" + "Enter another number with " + a + " digits:");
		} while (input2.length() != a);
		
		int num2 = Integer.parseInt(input2);
		int digit;
		int[] digitsNum1 = new int[a]; 
		int[] digitsNum2 = new int[a];
		int i, j;
		
		boolean samePlace = false;
		
		for (i = 0; i < a; i++) {
			digit = num1 % 10;
			num1 = num1 / 10;
			digitsNum1[i] = digit;
			digit = num2 % 10;
			num2 = num2 / 10;
			digitsNum2[i] = digit;
		}
		
		for (i = 0; i < a; i++) {
			for (j = 0; j < a; j++) {
				if (digitsNum1[i] == digitsNum2[j]) {
					if (i == j) {
						System.out.println("The digit " + digitsNum1[i] + " appears in both numbers: " + input1 + " and " + input2 + " in the same position (" + i + ").");
						samePlace = true;
					} else {
						System.out.println("The digit " + digitsNum1[i] + " appears in both numbers: " + input1 + " and " + input2 + " but in different positions (" + i + ") & (" + j + ")");
					}
				}
			}
				
		}
		
		System.out.println("");
		System.out.println("The original numbers were " + input1 + " and " + input2);
		System.out.println("");
		
		if (samePlace == false) {
			System.out.println("There are no two digits that appear in the same position in both numbers");
		} else {
			System.out.println("There were one or more digits that appeared in the same position in both numbers");
		}
	
	}

}
