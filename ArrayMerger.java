import java.util.Arrays;

public class ArrayMerger {

	public static void main(String[] args) {
		
		
		
		
		
		// superArrayMerger (int [], int [], boolean, int)
		// 
		// 
		// It's another simple and useful function that takes two arrays and 
		// combines it into a new one.
		// 
		// It has a few options that can be used through its 4 parameters:
		// 
		//  1 & 2 - the two arrays containing integers to be combined
		//
		//  3 - this asks the user if he wants to merge the two arrays or not
		//      
		//      (true) -  if the arrays are the same length, 
		//              the function will combine the two arrays into a new one, 
		//				adding up their values (or performing a different operation according 
		//              to the next parameter)
		//               if the arrays aren't the same length the function will return an empty array
		//     
		//
		//  or (false) - this will create a longer array while preserving all the initial values 
		//               performing a simple concatenation where array1 will come before array2
		//                                                                       
		//	4 - This parameter has multiple uses and it gives you different options depending 
		//	    on your previous choices. 
		//        
		//         If 3 was true, then the number entered here will decide which mathematical operation 
		//         will be performed between the cells of the same sized arrays
		//
		//      Option 0 = + 
		//      Option 1 = - 
		//      Option 2 = * 
		//      Option 3 = / 
		//      Option 4 = % 
		//      Option 5 = max 
		//      Option 6 = min 
		//      All other options = + 
		//     
		//     Obs.: Options 3 and 4 will return '0' if one of the cells = 0;
		// 
		//         But if 3 was false, then this parameter will decide whether or not to sort the new
		//         concatenated array. 
		//         
		//      Option 0 = leaves the array as it is, with a1 followed by a2
		//      Option 1 = sorts the new array numerically from smallest to largest
		// 	Option 2 = sorts the new array numerically but from largest to smallest
		
		//examples
		
		int[] array3 = {12,45,23,67,44,87,34,12,56};
		System.out.println(Arrays.toString(array3));
		System.out.println();
		
		int[] array4 = {45,89,78,67,56,45,56,67,56,12,31,41,51};
		System.out.println(Arrays.toString(array4));
		System.out.println();
		
		// sample usage
		// 
		// this will return an array like this:
		//
		//   array5[n] = array3[n] * array4[n]
		// 
		//int[] array5 = superArrayMerger(array3, array4, true, 2);
		
		
		//this will combinee] the two arrays and then sort the new array numerically from smallest to largest
		int[] array5 = superArrayMerger(array3, array4, false, 1);
		System.out.println(Arrays.toString(array5));
		System.out.println();
		
		
		
	}

		
	
	
	
	

	
	private static int[] superArrayMerger(int[] a1, int[] a2, boolean mergeArrays, int option) {
		
				
		int length;
	
		
		
		//Here we decide the length based on the two options
		if (mergeArrays == true) {
			length = a1.length;
		} else {
			length = a1.length + a2.length;
		}
		
		//Then we create the empty array
		int [] array = new int[length];
		
		
	
			//this the loop that will fill up the array
			for (int counter = 0; counter < length; counter++) {
				
				
				if (mergeArrays == true) { 
					
					if (a1.length == a2.length) {
						
						//These are all the arithmetical operations the function allows
						//With 0 and ELSE being the triggers for the addition mode
						if (option == 0) {
				
							array[counter] = a1[counter] + a2[counter];
					
						} else if (option == 1) {
						
							array[counter] = a1[counter] - a2[counter];
						
						} else if (option == 2) {
					
							array[counter] = a1[counter] * a2[counter];
					
						} else if (option == 3) {
							
							//avoiding division by 0
							if (a1[counter] != 0 && a2[counter] != 0) {
							array[counter] = a1[counter] / a2[counter];
							} else {
								array[counter] = 0;
							}
						
						} else if (option == 4) {
						
							//avoiding division by 0
							if (a1[counter] != 0 && a2[counter] != 0) {
								array[counter] = a1[counter] % a2[counter];
								} else {
									array[counter] = 0;
								}
					
						} else if (option == 5) {
						
							array[counter] = Math.max(a1[counter],a2[counter]);
						
						} else if (option == 6) {
						
							array[counter] = Math.min(a1[counter],a2[counter]);
						
						} else {
						
							array[counter] = a1[counter] + a2[counter];
					
						} 
					
					} else { 
						//this returns the empty array in case they are not the same size
						return array;
					}
				
					
				//here we go into the options if the user wants to concatenate the two arrays
				} else {	
					
					
						//basic concatenation of two arrays, a1 followed by a2 in their original order	
						if (counter < a1.length) {
								array[counter] = a1[counter];
							} else {
								array[counter] = a2[counter - a1.length];
							}
				}
				
				
			}
				
				//and these are the two sub-methods that will sort the array after it's been created
				// this will sort it in an ascending order
				if (mergeArrays == false && option == 1) {
					
				int backward = 1;	
					for (int forward = 1; forward < array.length; forward++) {
						if (array[forward] < array[forward-1]) {
							backward = forward;
							do {
								int temp = array[backward];
								array[backward] = array[backward-1];
								array[backward-1] = temp;
								backward --;
								if (backward == 0) {
									break;
								}
							} while (array[backward] < array[backward-1]);
						}
					}
				
				// and this will sort it in a descending order	
				} else if (mergeArrays == false && option == 2) {
					int backward = 1;	
					for (int forward = 1; forward < array.length; forward++) {
						if (array[forward] > array[forward-1]) {
							backward = forward;
							do {
								int temp = array[backward];
								array[backward] = array[backward-1];
								array[backward-1] = temp;
								backward --;
								if (backward == 0) {
									break;
								}
							} while (array[backward] > array[backward-1]);
						}
					}
					
				}
			
		return array;
	}


	
	
//CaioBadner2020
}

