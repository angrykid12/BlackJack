import java.util.List;
import java.util.ArrayList;

public abstract class Board 
{
	private Card[] cards;
	
	private List<Card> cardList;
	
	private List<Card> dealerCardList;
	
	private int totalPoints = 0;
	
	private int dealerTotalPoints = 0;

	private final static int dealerStopsAt = 17;

	private Deck deck;

	private static final boolean I_AM_DEBUGGING = false;

	public Board(int size, String[] ranks, String[] suits, int[] pointValues)
	{
		cards = new Card[size];
		cardList = new ArrayList<>();
		dealerCardList = new ArrayList<>();
		deck = new Deck(ranks, suits, pointValues);
		if (I_AM_DEBUGGING) 
		{
			System.out.println(deck);
			System.out.println("----------");
		}
		dealMyCards();
	}
	
	public void newGame() 
	{
		deck.shuffle();
		cardList = new ArrayList<>();
		dealerCardList = new ArrayList<>();
		totalPoints = 0;
		dealerTotalPoints = 0;
		dealMyCards();
	}

	public int size() 
	{
		return cards.length;
	}
	
	public int cardListSize()
	{
		return cardList.size();
	}
	
	public int dealerCardListSize()
	{
		return dealerCardList.size();
	}


	
	private void deal()
	{
		if(totalPoints < 21)
		{
			Card myCard = deck.deal();
			cardList.add(myCard);
			totalPoints = calculateTotalPoints(cardList);
		}
	}
	
	public void dealerDeal()
	{
		while (dealerTotalPoints < dealerStopsAt)
		{
			Card dealerCard = deck.deal();
			dealerCardList.add(dealerCard);
			dealerTotalPoints = calculateTotalPoints(dealerCardList);
		}
	}

	private int calculateTotalPoints(List<Card> cards)
	{
		int total1 = 0;
		int total2 = 0;

		for (Card card : cards) {
			if (card.rank().equals("ace")) {
				total1 += 1;
				total2 += 11;
			} else {
				total1 += card.pointValue();
				total2 += card.pointValue();
			}
		}
		if (total1 <= 21 && total2 <= 21)
		{
			return max(total1, total2);
		}
		else 
		{
			return min(total1, total2);
		}
	}
	
	private int max(int a, int b)
	{
		if (a > b)
			return a;
		else
			return b;
	}
	
	private int min(int a, int b)
	{
		if (a < b)
			return a;
		else
			return b;
	}
	
	public int youWin()
	{
		if (totalPoints > 21)
			return -1;
		else if (dealerTotalPoints > 21)
			return 1;
		else return Integer.compare(totalPoints, dealerTotalPoints);
	}
	
	public int getTotalPoints()
	{
		return totalPoints;
	}
	
	public int getDealerTotalPoints()
	{
		return dealerTotalPoints;
	}

	public int deckSize() 
	{
		return deck.size();
	}

	public Card cardAt(int k)
	{
		return cardList.get(k);
	}

	public Card dealerCardAt(int k)
	{
		return dealerCardList.get(k);
	}
	
	public void dealACard()
	{
		deal();
	}


	public String toString() 
	{
		StringBuilder s = new StringBuilder();
		for (int k = 0; k < cards.length; k++) 
		{
			s.append(k).append(": ").append(cards[k]).append("\n");
		}
		return s.toString();
	}

	private void dealMyCards() 
	{
		for (int k = 0; k < 2; k++)
		{
			Card myCard = deck.deal();
			cardList.add(myCard);
			Card dealerCard = deck.deal();
			dealerCardList.add(dealerCard);
		}
		
		totalPoints = calculateTotalPoints(cardList);
		dealerTotalPoints = calculateTotalPoints(dealerCardList);
	}
}