import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Deck {
	
	private LinkedList cards;
	private double maxMerged = 0.5;
	private String printString = "";
	private int prevMerged = 0;
	private int prevprevMerged = 0;
	private int currentMerged = 0;

	// My favorite quote to print before each sort.
	public static String quoteForMerge = "\n---- Logic will get you from A to Z; \n\t\tIMAGINATION will get you everywhere. (Albert Einstein) ----\n";

	// Constructor
	// Initialize the cards LinkedList.
	public Deck(){
		cards = new LinkedList();
	}

	// Read the filename.txt file row by row, create cards based on suits and ranks
	// Add each card to the double linked list.
	public void createDeck(String fileName, boolean withPrint){
		BufferedReader rd = null;
		try {
			rd = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while(true){
			String line = "";
			try {
				line = rd.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (line == null)
				break;

			// Tokens the string
			// add it to card list
			StringTokenizer st = new StringTokenizer(line, " ");
			String suit = st.nextToken();
			int rank = Integer.parseInt(st.nextToken());
			Card c = new Card(suit,rank);
			cards.addLast(c);
		}

		try {
			rd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//System.out.println("Initial Unsorted Deck:\n");
		//printDeck();
		
		if (withPrint)
			System.out.println("The file has been read without any error and the deck has been successfully created.\n");

	}

	// Merge two lists while comparing the cards in the list order
	// Choose the card which has priority, remove it from the its list and
	// add it to merged list.
	// continue until one of the lists is empty
	// move all cards in the remaining list to merged list.
	public void merge(LinkedList list1, LinkedList list2, LinkedList list){
		while(!list1.isEmpty() && !list2.isEmpty()){
			if (compareCards(list1.getFirst(), list2.getFirst()) < 0)
				list.addLast(list1.removeFirst());
			else list.addLast(list2.removeFirst());
		}
		
		while(!list1.isEmpty())
			list.addLast(list1.removeFirst());
		while(!list2.isEmpty())
			list.addLast(list2.removeFirst());
		
	}
	
	// Prints Quote.
	// Calls first divide method with parameter "cards" that containing all cards in the deck.
	public void mergeSort(){
		System.out.println(quoteForMerge);
		System.out.println("Initial Unsorted Deck:\n");
		printDeck();
		
		System.out.println("MergeSort Started:\n");
		//divideOld(cards,2);
		divideNew(cards);
		System.out.println("\nMergeSort is finished..\n");
		printString = "";
		maxMerged = 1;
	}
	
	/*
	 * My first implemented divide method.
	 * Create two lists, put first N/2 elements to first list.
	 * Other elements to the second list.
	 * Then call merge method to merge them.
	 * After merge method, print the merged list.
	 * But there, my println depends on some variables.
	 * I store the size of maximum merged list.
	 * If the newly merged list exceeds this number,
	 * then i go to new line and clear the string that holds printed strings.
	 * this string becomes newly merged list's elements.
	 * To print lists with 1 element, also we need extra checks.
	 * We need to determine whether the divide call is for left or right part.
	 * To pass this information to the recursively called method, I pass dir argument.
	 * (0 -> left, 1 -> right)
	 * If list size is 1 and dir == 0, Then print the previously printed string and this 1 element but do NOT
	 * update the previously printed list.
	 */
	public void divideOld(LinkedList list, int dir){
	
		if (list.getSize() < 2){
			
			if (dir == 0){
				if (maxMerged < 1){
					maxMerged *= 2;
					currentMerged = list.getSize();
					System.out.println(list.getPrintString());
				}
				else{
					System.out.println(printString + "   " + list.getPrintString());
				}
			}
			else{
				prevprevMerged = prevMerged;
				prevMerged = 2;
			}
			return;
		}
		
		LinkedList leftList = new LinkedList();
		LinkedList rightList = new LinkedList();
		double sizeL = list.getSize();
		
		while(leftList.getSize() < sizeL/2)
			leftList.addLast(list.removeFirst());
		
		while(!list.isEmpty())
			rightList.addLast(list.removeFirst());
		
		divideOld(leftList,0);
		divideOld(rightList,1);
		merge(leftList,rightList,list);
		currentMerged = list.getSize();
		
		if (list.getSize() > maxMerged){
			maxMerged = list.getSize();
			printString = list.getPrintString();
		}
		else{
			if (currentMerged == (prevMerged+prevprevMerged)){
				int  L = printString.length();
				// first one is printCard cost, second one is "->" cost, third one space cost between to print (constant = 3).
				int cost = (prevMerged+prevprevMerged)*3 + (prevMerged-1)*3 + ((prevprevMerged-1)*3) + 3 ;
				if (currentMerged == 3)
					cost = 9;
				
				//add cost of 2 digit numbers
				for (int i= 0; i< L; i++){
					if (printString.charAt(i) == '|'){
						if (Character.isDigit(printString.charAt(i+1)) && (i+2) < L)
							if (Character.isDigit(printString.charAt(i+2)))
								cost++;
					}
				}
				// crop the string and add newly merged list
				printString = printString.substring(0, L-cost);
				printString += list.getPrintString();
			}
			else{
				printString += "   ";
				printString += list.getPrintString();
			}
			
		}
		
		System.out.println(printString);
		prevprevMerged = prevMerged;
		prevMerged = currentMerged;
	}
	
	/*
	 * This is the my new divide method with some changes in print part.
	 * Other than print part, they are all same.
	 * 3 printlns are added to make this method work like our TA wanted.
	 */
	public void divideNew(LinkedList list){
		
		if (list.getSize() < 2){
			return;
		}
		
		double sizeL = list.getSize();
		LinkedList leftList = new LinkedList();
		LinkedList rightList = new LinkedList();
		
		while(leftList.getSize() < sizeL/2)
			leftList.addLast(list.removeFirst());
		
		while(!list.isEmpty())
			rightList.addLast(list.removeFirst());
		
		divideNew(leftList);
		divideNew(rightList);
		
		System.out.println("Left Part  : " + leftList.getPrintString());
		System.out.println("Right Part : " + rightList.getPrintString());
		merge(leftList,rightList,list);
		System.out.println("Merged Left and Right : " + list.getPrintString() + "\n");
	}

	/*
	 * Calls the inplace quick sort method with left parameter = first card
	 * right parameter = last card
	 */
	public void quickSort(){
		System.out.println(quoteForMerge);

		System.out.println("Initial Unsorted Deck:\n");
		printDeck();

		System.out.println("QuickSort Started:\n");
		quickSortSwapAndDivide(cards.getFirst(),cards.getLast());
		System.out.println("QuickSort is finished\n");
	}

	/*
	 * This is the optimized quick sort algorithm.
	 * For given left and right card pointers, pivot is chosen as the right most card.
	 * Starting from left, we search for cards that has a lower priority than pivot.
	 * Starting from right, we search for cards that has a higher priority than pivot.
	 * When we find these cards, we swap them until left index becomes greater than right index.
	 * Lastly, to put the pivot into its right place, we swap it with the left pointer.
	 * Method calls itself (recursion) by taking the Left and right part arguments of the pivot. 
	 */
	public void quickSortSwapAndDivide(Card left,Card right){
		if (left != null && right != null){

			if (left.getPosition()>= right.getPosition())
				return;

			Card pivot = right;
			Card currentLeft =left;
			Card currentRight = right.getPrev();
			

			System.out.println("Pivot " + pivot.printCard()+":");
			boolean exit = false;

			while(currentLeft.getPosition()<= currentRight.getPosition()){
				while(compareCards(currentLeft, pivot) < 0 && currentLeft.getPosition()<= currentRight.getPosition() ){
					currentLeft = currentLeft.getNext();
					if (currentLeft == null){
						exit = true;
						break;
					}

				}
				while(compareCards(currentRight, pivot) > 0 && currentLeft.getPosition()<= currentRight.getPosition()){
					currentRight = currentRight.getPrev();	
					if (currentRight == null){
						exit = true;
						break;
					}

				}
				if (currentLeft != null && currentRight != null){
					if (currentLeft.getPosition() < currentRight.getPosition()){

						//System.out.println("swapping .... : " + cLeft.printCard()+"("+cLeft.getPosition()+")" + " - " + cRight.printCard()+"("+cRight.getPosition()+")");
						swap(currentLeft,currentRight);
						currentRight = currentRight.getPrev();
						//System.out.println("after swap right : " + cRight.printCard());
						currentLeft = currentLeft.getNext();
						//System.out.println("after swap left: " + cLeft.printCard());
					}
				}
				if (exit)
					break;
			}
			if (currentLeft != null){

				swap(currentLeft,right);
				printDeck();
				quickSortSwapAndDivide(left,currentLeft.getPrev());
				quickSortSwapAndDivide(currentLeft.getNext(), right);
			}
		}
	}

	
	//Helper Method : Call the printList method of the List
	public void printDeck(){
		cards.printList();
		System.out.println("\n");
	}

	// Helper Method : Swap 2 cards' suit and rank
	public void swap(Card cLeft,Card cRight){
		String tempSuit = cLeft.getSuit();
		int tempRank = cLeft.getRank();

		cLeft.setSuit(cRight.getSuit());
		cLeft.setRank(cRight.getRank());

		cRight.setSuit(tempSuit);
		cRight.setRank(tempRank);
	}

	/* Helper Method: compare 2 cards according to their suit and rank priority
	 * Simple math: we think that a card should be printed in the left part if it has a priority.
	 * We define these cards with lower numbers and sort the cards like we sort the numbers in the ascending order.
	 * This method gives each card a number.
	 * For example Spider cards take numbers from 1 to 13.
	 * Heart cards take numbers from 14 to 26.
	 * Club cards take numbers from 27 to 39.
	 * Diamond cards take numbers from 40 to 52.
	 */
	public int compareCards(Card c1, Card c2){
		int p1 = (c1.getSuitPriority()-1)*13 + c1.getRank();
		int p2 = (c2.getSuitPriority()-1)*13 + c2.getRank();

		if (p1>p2)
			return 1;
		else if (p1==p2)
			return 0;
		else 
			return -1;
	}
}
