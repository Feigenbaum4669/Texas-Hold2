
public class CollectBigBlind extends TableState {

	private TableState nextState;
	
	public void Auto(Table tab){
		
		nextState=tab.FirstDealState;
		Integer bb=tab.getBigBlind();
		try{
		Integer CurrPlayer=tab.nextNonFoldedPlayer(tab.getlastActivePlayer());
		tab.setlastActivePlayer(CurrPlayer);
			try{
		tab.getSystemPlayer(CurrPlayer).incrPlayerBet(bb);
		tab.incrBank(bb);
		tab.getSystemPlayer(CurrPlayer).setPlayerStatus(PlayerStatus.max_bet_bb);
		tab.ChangeActivePlayersStatusExcept(PlayerStatus.under_max_bet,CurrPlayer);
			}catch(NotEnoughCreditsException ex){
				if(tab.getSystemPlayer(CurrPlayer).getPlayerCredits()==0){
					tab.getSystemPlayer(CurrPlayer).setPlayerStatus(PlayerStatus.folded);
				}else{
					tab.getSystemPlayer(CurrPlayer).allIn();
					tab.incrBank(tab.getSystemPlayer(CurrPlayer).getPlayerCredits());
					tab.getSystemPlayer(CurrPlayer).setPlayerStatus(PlayerStatus.all_in);
					tab.ChangeActivePlayersStatusExcept(PlayerStatus.under_max_bet,CurrPlayer);
				}
				
		}
			
		}catch(RunOutOfPlayersException ex){
	    	tab.getSystemPlayer(tab.getdealerButton()).notify(ex.getMessage());
	    	nextState=tab.stopGameState;
	    	}
		System.out.println("Big: "+tab.getlastActivePlayer()+" BET: "+tab.getSystemPlayer(tab.getlastActivePlayer()).getPlayerBet()+" cards: "+tab.getSystemPlayer(tab.getlastActivePlayer()).countCards());
		tab.setState(nextState);
		//tab.Auto();
	    }
	}
	




