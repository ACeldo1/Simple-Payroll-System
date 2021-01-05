package application;

public class NumberConverter {
	
	NumberConverter(){}
	
	//with thanks to codippa
	 public static String numberToWord(int number) {
	        // variable to hold string representation of number 
	       
		 
		 String words = "";
	        String unitsArray[] = { "Zero", "One", "Two", "Three", "Four", "Five", "Six", 
	                      "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
	                      "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", 
	                      "Eighteen", "Nineteen" };
		String tensArray[] = { "Zero", "Ten", "Twenty", "Thirty", "Forty", "Fifty",
	                      "Sixty", "Seventy", "Eighty", "Ninety" };
	 
		if (number == 0) {
		    return "zero";
		}
	
	        // check if number is divisible by 1 million
	     if ((number / 1000000) > 0) {
		   words += numberToWord(number / 1000000) + " Million ";
		   number %= 1000000;
		}
		// check if number is divisible by 1 thousand
		if ((number / 1000) > 0) {
		    words += numberToWord(number / 1000) + " Thousand ";
		    number %= 1000;
		}
		// check if number is divisible by 1 hundred
		if ((number / 100) > 0) {
		     words += numberToWord(number / 100) + " Hundred ";
		     number %= 100;
		}
	 
		if (number > 0) {
		     // check if number is within teens
		     if (number < 20) { 
	                    // fetch the appropriate value from unit array
	                    words += unitsArray[number];
	             } else { 
	                // fetch the appropriate value from tens array
	                words += tensArray[number / 10]; 
	                if ((number % 10) > 0) {
			    words += "-" + unitsArray[number % 10];
	                }  
		     }
	          }
		  return words;
	   }



}

	
	



