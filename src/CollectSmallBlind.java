
public class CollectSmallBlind extends TableState {

	public void Auto(Table tab){
		Integer sb=tab.getSmallBlind();
		Integer ap=tab.getdealerButton()+1;
		tab.setlastActivePlayer(ap);
		tab.setBank(tab.getBank()+sb);
		tab.setBet(ap, sb);
		tab.setCredits(ap, tab.getCredits(ap)-sb);
		tab.setState(tab.collectBigBlindState);
		//tab.Auto();
	    }
	}
	


