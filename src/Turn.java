
public class Turn extends TableState {

	public void Auto(Table tab){
		tab.addCardOnTable(tab.takeCardFromDeck());
		tab.setState(tab.ThirdBidState);
		//tab.Auto();
	}
}
