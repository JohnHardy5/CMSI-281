package inputSplitter;

import java.util.Scanner;

public class UniqueWords {
	
	public static void main (String[] args) {
        Scanner input = new Scanner(System.in);//c1
        System.out.println("Enter a sentence.");//c2
        
        String[] words = input.nextLine().split(" ");//c3
        int count = 0;//c4
        for (int i = 0; i < words.length; i++) {//n
            boolean unique = true;//c5 * n
            for (int j = 0; j < words.length; j++) {//n * n = n^2
                if (words[i].equals(words[j]) && i != j) {//c6 * n^2
                    unique = false;//worst case: everything is unique, c7 * n^2
                    break;//c8 * n^2
                }
            }
            if (unique) {count++;}//c9 * n
        }
        System.out.println("There are " + count + " unique words in that sentence.");//c10
        input.close();//c11
    }//T(n) = c1 + c2 + c3 + c4 + c5 * n + c6 * n^2 + c7 * n^2 + c8 * n^2 + c9 * n + c10 + c11 = O(n^2)
    
}
