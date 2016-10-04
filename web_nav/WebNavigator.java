package web_nav;
	
import java.util.ArrayList;
import java.util.Scanner;
	
public class WebNavigator {
	
	 // Fields
	private String current; // Tracks currently visited site
	private ArrayList<String> webHistory;
	private int size;
	private int currentItem;
	 
	 // Constructor
	WebNavigator () {
	    current = null;
		webHistory = new ArrayList<String>();
	    size = 0;
	    currentItem = -1;
	 }
	 
	 // Methods
	 // [!] YOU DO NOT HAVE TO MODIFY THIS METHOD FOR YOUR SOLUTION
	public boolean getNextUserCommand (Scanner input) {
        String command = input.nextLine();
        String[] parsedCommand = command.split(" ");
        
        // Switch on the command (issued first in input line)
        switch(parsedCommand[0]) {
        case "exit":
            return false;
        case "visit":
            visit(parsedCommand[1]);
            break;
        case "back":
            back();
            break;
        case "forward":
            forw();
            break;
        default:
            System.out.println("[X] Invalid command, try again");
        }
        
        System.out.println("Currently Visiting: " + current);
        
        return true;
    }
	 
	 /*
	  *  Visits the current site, clears the forward history,
	  *  and records the visited site in the back history
	  */
	public void visit (String site) {
		while (currentItem < size - 1 && size > 1) {
			webHistory.remove(currentItem + 1);
			size--;
		}
		current = site;
		webHistory.add(current);
		size++;
		currentItem++;
	}
	 
	 /*
	  *  Changes the current site to the one that was last
	  *  visited in the order on which visit was called on it
	  */
	public void back () {
		if (size < 2 || currentItem == 0) {return;}
		currentItem--;
		current = webHistory.get(currentItem);
	}
	 
	public void forw () {
		if (currentItem == size - 1) {return;}
		currentItem++;
		current = webHistory.get(currentItem);
	}
	 
	public static void main(String[] args) {
	     Scanner input = new Scanner(System.in);
	     WebNavigator navi = new WebNavigator();
	     
	     System.out.println("Welcome to ForneyFox, enter a command from your ForneyFox user manual!");
	while (navi.getNextUserCommand(input)) {}
	System.out.println("Goodbye!");
	}
	
}
