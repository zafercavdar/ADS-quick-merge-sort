import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	/*
	 * Create the deck.
	 * Wait for commands.
	 * createdeck, quicksort, mergesort and printdeck commands are expected.
	 * Break the loop with given input = exit
	 */
	public static void main(String[] args) {
		Deck deck = new Deck();
		Scanner sc = new Scanner(System.in);
		String fileName = "";
		
		while(true){
			System.out.print("Type your command > ");
			String line = sc.nextLine();
			StringTokenizer st = new StringTokenizer(line, "* ");
			String command = st.nextToken();
			if (command.equalsIgnoreCase("createDeck")){
				fileName = st.nextToken();
				deck = new Deck();
				deck.createDeck(fileName,true);

			}
			else if (command.equalsIgnoreCase("mergeSort")){
				deck = new Deck();
				deck.createDeck(fileName,false);
				deck.mergeSort();
			}
			else if (command.equalsIgnoreCase("quickSort")){
				deck = new Deck();
				deck.createDeck(fileName,false);
				deck.quickSort();
			}
			else if (command.equalsIgnoreCase("printDeck")){
				deck.printDeck();
			}
			else if (command.equalsIgnoreCase("exit")){
				System.out.println("Program terminated successfully.");
				break;
			}
			else
				System.out.println("Could not understand your command.");
		}
		
		sc.close();
	}

}
