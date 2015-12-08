
public class Flop extends TableState{

	
	public void Auto(Table tab){
		tab.addCardOnTable(tab.takeCardFromDeck());
		tab.addCardOnTable(tab.takeCardFromDeck());
		tab.addCardOnTable(tab.takeCardFromDeck());
		tab.setState(tab.SecondBidState);
		//tab.Auto();
	}

}
