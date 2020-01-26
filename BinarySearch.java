import java.util.Arrays;

import javax.swing.JOptionPane;

public class BinarySearch {

	public static void main(String[] args) {
		
		//int[] array = RandomArrayMaker.randomArrayMaker(false, 100, false, 20);
		
		int [] array = {1,1,1,1,1,11,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,16};
		System.out.println(Arrays.toString(array));
		do {
		int num = Integer.parseInt(JOptionPane.showInputDialog("What number do you want me to look for?"));
		int answer;
		int left = findLeft(array, num);
		
		if (left == -1) {
			answer = 0;
		} else {
			int right = findRight(array, left, num);
			answer = right - left + 1;
		}
		JOptionPane.showMessageDialog(null, "The number " + num + " was seen " + answer + " times in the array.");
		} while (JOptionPane.showConfirmDialog(null, "Another one?", "",JOptionPane.YES_NO_OPTION) == 0);
	}
	
	private static int findLeft(int[] a, int num) {
		int left;
		
		if (a[0] == num) {
			left = 0;
			return left;
		}
		
		int start = 0;
		int end = a.length - 1;
		
		
		while (start <= end) {
			
			left = (start + end) / 2;
		
			if (a[left] == num) {
				if (a[left - 1] != num) {
					return left;
				} else {
					end = left - 1;
				}
							
			} else if (a[left] < num) {
				start = left + 1;
			} else {
				end = left - 1;
				
			}
		}
		
		left = -1;
		return left;
	}	
	
	private static int findRight(int[] a, int left, int num) {
		int right = 0;
		int start = left;
		int end = a.length - 1;
		
		if (a[end] == num) {
			right = end;
			return right;
		}
		
		end --;
		
		while (start <= end) {
			
			right = (start + end) / 2;
		
			if (a[right] == num) {
				
				if (a[right + 1] != num) {
					return right;
				} else {
					start = right + 1;
				}
				
			} else if (a[right] < num) {
				start = right + 1;
			} else {
				end = right - 1;
			}
		}
		
		return right;
	}

	
}

	
