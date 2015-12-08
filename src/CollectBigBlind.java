
public class CollectBigBlind extends TableState {
	

	public void Auto(Table tab){
		Integer bb=tab.getBigBlind();
		Integer ap=tab.getdealerButton()+2;
		tab.setlastActivePlayer(ap);
		tab.setBank(tab.getBank()+bb);
		tab.setBet(ap, bb);
		tab.setCredits(ap, tab.getCredits(ap)-bb);
		tab.setState(tab.FirstDealState);
		
		//tab.Auto();
	    }
	}


