
public class CollectBigBlind extends TableState {
	

	public void Auto(TableAppliance tab){
		tab.setBank(tab.getBank()+tab.getBigBlind());
		tab.setCredits(tab.getdealerButton()+1, tab.getCredits(tab.getdealerButton()+1)-tab.getBigBlind());
		tab.setState(tab.FirstDealState);
		tab.Auto();
	    }
	}


