
public class River extends TableState {

	
	public void Auto(Table tab){
		tab.addCardOnTable(tab.takeCardFromDeck());
		tab.setState(tab.LastBidState);
		//tab.Auto();
	}

}
