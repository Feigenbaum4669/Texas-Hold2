
public class FirstDeal extends TableState {

	
	public void Auto(TableAppliance tab){
		tab.deal(2);
		tab.setState(tab.FirstBidState);
		//tab.Auto();
	    }

}
