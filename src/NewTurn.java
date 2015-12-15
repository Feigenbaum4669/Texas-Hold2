
public class NewTurn extends TableState {
	
	private TableState nextState;
	
    public void Auto(Table tab){
    	nextState=tab.collectSmallBlindState;
    	Integer CurrPlayer;
    	tab.newDeck();
    	
    	for(int i=0;i<tab.countPlayers();i++){
    		System.out.println("player: "+i+"status: "+tab.getSystemPlayer(i).getPlayerStatus());
    		}
    	
    	try{
    	CurrPlayer=tab.nextNonQuitPlayer(tab.getdealerButton());
    	tab.setdealerButton(CurrPlayer);
    	System.out.println("Dealer: "+CurrPlayer);
    	tab.setlastActivePlayer(CurrPlayer);
    	}catch(RunOutOfPlayersException ex){
    	tab.getSystemPlayer(tab.getdealerButton()).notify(ex.getMessage());
    	nextState=tab.SumUpState;
    	}
    	
	tab.setState(nextState);
	//tab.Auto();
    }
    

}
