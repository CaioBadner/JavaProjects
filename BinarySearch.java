import java.util.Arrays;

import javax.swing.JOptionPane;

public class BinarySearch {

	public static void main(String[] args) {
		
		//int[] array = RandomArrayMaker.randomArrayMaker(false, 174, false, 100);
		
		int [] array = {2,2,2,2,2,2,2,2,2,2};
		System.out.println(Arrays.toString(array));
		do {
		int num = Integer.parseInt(JOptionPane.showInputDialog("What number do you want me to look for?"));
		
		
		JOptionPane.showMessageDialog(null, "The number " + num + " was seen " + howManyOccurences(array,num) + 
					      " times in the array.");
		} while (JOptionPane.showConfirmDialog(null, "Another one?", "",JOptionPane.YES_NO_OPTION) == 0);
	}

	private static int howManyOccurences(int[] a, int num) {
		int found = 0;
		
		int start = 0;
		int end = a.length - 1;
		int place = 0, middle = 0;
				
		while (start <= end) {
			
			middle = (start + end) / 2;
		
			if (a[middle] == num) {
				
				found ++;
				place = middle - 1;
				//I used this instead of a simple BREAK because these variables won't be used again
				start = end + 1;
				
			} else if (a[middle] < num) {
				start = middle + 1;
						
			} else {
				end = middle - 1;
				
			}
		}
		//this skips the whole second part if the number wasnt found
		if (found == 0) {
			return found;
		}
		
		int copy = num;
		
		while (a[place] == copy) {
			
			found ++;
			
			if (place == 0) {
				//again, I left the loops by invalidating the main condition of the
				copy = a[place] - 1;
			} else {
			
				place --;
			}
		}
		
		copy = num;
		place = middle + 1;
		
		while (a[place] == copy) {
			
			found ++;
			
			if (place == a.length - 1) {
				//here one last time we change the variable to get a safe way out of the loop
				copy ++;
			} else {
			
				place ++;
			}
		}
		
		
		return found;
		
	}
	
	

}
