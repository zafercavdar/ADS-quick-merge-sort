public class LinkedList {
	private Card first;
	private Card last;
	private int size = 0;

	/*
	 * Initialization of LinkedList (actually it is double linked list)
	 */
	public LinkedList(){
		first = null;
		last = null;
	}
	
	public boolean isEmpty(){
		return (size==0);
	}

	/*
	 * Getter methods for first, last and size.
	 */
	public Card getFirst(){
		return first;
	}

	public Card getLast(){
		return last;
	}
	
	public int getSize(){
		return size;
	}
	
	/*
	 * This method adds the card to the end of the list.
	 */
	public void addLast(Card card){
		if (first == null){
			first = card;
			last = card;
			card.setNext(null);
			card.setPrev(null);
		}
		else{
			last.setNext(card);
			card.setNext(null);
			card.setPrev(last);
			last = card;
		}

		card.setPosition(size);
		size++;
	}

	/*
	 * This methods removes the first card in the list.
	 */
	public Card removeFirst(){
		if (first!=null){
			if (first.getNext() != null)
				first.getNext().setPrev(null);
			Card result = first;
			first = first.getNext();
			result.setNext(null);
			size--;
			return result;
		}
		else
			return null;
	}
	
	/*
	 * Iterate over all cards, print their suit and rank.
	 */
	public void printList(){
		Card current = first;
		
		if (current == null){
			System.out.println("Deck is empty ..");
		}
		
		while(current != null){
			System.out.print(current.getSuit() +"|"+current.getRank());

			if (current.getNext() != null)
				System.out.print("-> ");

			current = current.getNext();
		}
		
	}
	
	/*
	 * Return a string that printList prints.
	 * Output of the method can be used with println methods.
	 */
	public String getPrintString(){
		String result ="";
		Card current = first;
		while(current != null){
			result += (current.getSuit() +"|"+current.getRank());

			if (current.getNext() != null)
				result += ("-> ");

			current = current.getNext();
		}
		
		return result;
	}
}
