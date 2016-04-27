
public class Card {

	private String suit;
	private int rank;
	private Card next;
	private Card prev;
	private int position;

	/*
	 * Constructor with suit and rank.
	 */
	public Card(String suit, int rank){
		this.suit = suit;
		this.rank = rank;
	}

	/*
	 * Each suit has different priority. This value will be used in comparison of the cards.
	 */
	public int getSuitPriority(){
		if (suit.equalsIgnoreCase("S"))
			return 1;
		else if (suit.equalsIgnoreCase("H"))
			return 2;
		else if (suit.equalsIgnoreCase("C"))
			return 3;
		else if (suit.equalsIgnoreCase("D"))
			return 4;
		else
			return 0;
	}

	/*
	 * This method is used only to print pivot.
	 */
	public String printCard(){
		return suit + "|" + rank;
	}

	/*
	 * Getter and setter methods for all private fields.
	 */
	public int getPosition(){
		return position;
	}

	public void setPosition(int p){
		this.position = p;
	}

	public void setSuit(String suit){
		this.suit = suit;
	}

	public void setRank(int rank){
		this.rank = rank;
	}

	public void setPrev(Card prev){
		this.prev = prev;
	}

	public void setNext(Card next){
		this.next = next;
	}

	public Card getNext(){
		return next;
	}

	public Card getPrev(){
		return prev;
	}

	public String getSuit(){
		return suit;
	}

	public int getRank(){
		return rank;
	}
}
