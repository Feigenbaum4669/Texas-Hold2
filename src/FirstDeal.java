
public class FirstDeal extends TableState  {

	private TableState nextState;
	public void Auto(Table tab){
		nextState=tab.FirstBidState;
		try{
		tab.deal(2);
		}catch(sizeofSetException ex){
			System.err.println("sizeofSetException: " + ex.getMessage());
		}catch(RunOutOfPlayersException ex){
			tab.getSystemPlayer(tab.getdealerButton()).notify(ex.getMessage());
			nextState=tab.stopGameState;
			}
		tab.setState(nextState);
		//tab.Auto();
	    }

}

