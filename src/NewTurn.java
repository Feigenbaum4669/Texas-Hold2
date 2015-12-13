
public class NewTurn extends TableState {
	
	private TableState nextState;
	
    public void Auto(Table tab){
    	nextState=tab.collectSmallBlindState;
    	Integer CurrPlayer;
    	tab.newDeck();
    	//System.out.println("After newdeck size is:"+ tab.countDeck());
    	try{
    	CurrPlayer=tab.nextNonFoldedPlayer(tab.getdealerButton());
    	tab.setdealerButton(CurrPlayer);
    	tab.setlastActivePlayer(CurrPlayer);
    	}catch(RunOutOfPlayersException ex){
    	tab.getSystemPlayer(tab.getdealerButton()).notify(ex.getMessage());
    	nextState=tab.stopGameState;
    	}
	tab.setState(nextState);
	//tab.Auto();
    }
    

}
