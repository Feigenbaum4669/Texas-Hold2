
public class CollectSmallBlind extends TableState {

	public void Auto(TableAppliance tab){
		tab.setBank(tab.getBank()+tab.getSmallBlind());
		tab.setCredits(tab.getdealerButton()+1, tab.getCredits(tab.getdealerButton()+1)-tab.getSmallBlind());
		tab.setState(tab.collectBigBlindState);
		tab.Auto();
	    }
	}
	

}
