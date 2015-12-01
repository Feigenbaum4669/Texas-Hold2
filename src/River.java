
public class River extends TableState {

	
	public void Auto(TableAppliance tab){
		tab.addCardOnTable(tab.takeCardFromDeck());
		tab.setState(tab.LastBidState);
		//tab.Auto();
	}

}
