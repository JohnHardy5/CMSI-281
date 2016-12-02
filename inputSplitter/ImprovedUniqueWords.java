package inputSplitter;
/**
 ** Completed by John Hardy
 **/

import java.util.Scanner;
import java.util.Hashtable;

//-----------------------------------------------------------
// PROBLEM 1:
// -----------------------------------------------------------
// [PART I]
public class ImprovedUniqueWords {

      public static void main (String[] args) {
          Scanner input = new Scanner(System.in);//c1
          System.out.println("Enter a sentence.");//c2
          String[] words = input.nextLine().split(" ");//c3
          Hashtable<String, Integer> h = new Hashtable<String, Integer>();//c4
          for (String s : words) {//n
        	  Integer bucketValue = h.get(s);//c5 * n
        	  if (bucketValue == null) {//c6 * n
        		  h.put(s, 1);//worst case: every word is unique, put operation is O(1) * n
        	  } else {
        		  h.put(s, bucketValue + 1);//O(1) * n
        	  }
          }
          System.out.println("There are " + h.size() + " unique words in that sentence.");//c9
          input.close();//c10
      }//T(n) = c1 + c2 + c3 + c4 + c5 * n + c6 * n + O(1) * n + O(1) * n + c9 + c10 = O(n)
      
}

//[PART II]
// Asymptotic Runtime Complexity of OLD UniqueWords.java (the solution):  O(n^2)
// Asymptotic Runtime Complexity of NEW UniqueWords.java (the above):     O(n)

// -----------------------------------------------------------
// PROBLEM 2:
// -----------------------------------------------------------
// T_1(n) = \Theta(n)
// T_2(n) = \Theta(n^2 * log(n))