package inputSplitter;
/*
 * Group members: John Hardy, Albert Didde, and Ryan Frizel.
 */

import java.util.Scanner;

public class InputSplitter {
	
	public static void main(String [] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Give me any sentence and I will tell you how many individual words it has.");
		String sentence = input.nextLine();
		String[] words = sentence.split(" ");
		for (int i = 0; i != words.length; i++) {
			boolean shouldSetToNull = false;
			for (int j = 0; j != words.length; j++) {
				if (words[j] == null || words[i] == null || j == i) {
					continue;
				} else if (words[i].equals(words[j])) {
					shouldSetToNull = true;//here is your flag prof. Forney
					words[j] = null;
				}
			}
			if (shouldSetToNull) {
				words[i] = null;
			}
		}
		boolean foundIndividual = false;
		int numIndvWordsFound = 0;
		for (int i = 0; i != words.length; i++) {
			if (words[i] != null) {
				System.out.println(words[i]);
				numIndvWordsFound++;
				foundIndividual = true;
			}
		}
		if (foundIndividual) {
			System.out.println("The above words are individual.");
		} else {
			System.out.println("No individual words found...");
		}
		System.out.println("Number of individual words found: " + numIndvWordsFound);
        input.close();
	}
}
