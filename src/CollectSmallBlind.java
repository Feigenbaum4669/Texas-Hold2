
public class CollectSmallBlind extends TableState {

	private TableState nextState;
	
	public void Auto(Table tab){
		nextState=tab.collectBigBlindState;
		Integer sb=tab.getSmallBlind();
		try{
		Integer CurrPlayer=tab.nextNonFoldedPlayer(tab.getlastActivePlayer());
		tab.setlastActivePlayer(CurrPlayer);
			try{
		tab.getSystemPlayer(CurrPlayer).incrPlayerBet(sb);
		tab.incrBank(sb);
		tab.getSystemPlayer(CurrPlayer).setPlayerStatus(PlayerStatus.max_bet_nbb);
		tab.ChangeActivePlayersStatusExcept(PlayerStatus.under_max_bet,CurrPlayer);
			}catch(NotEnoughCreditsException ex){
				if(tab.getSystemPlayer(CurrPlayer).getPlayerCredits()==0){
					tab.getSystemPlayer(CurrPlayer).setPlayerStatus(PlayerStatus.folded);
					nextState=tab.collectSmallBlindState;
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
		//System.out.println("Small: "+tab.getlastActivePlayer());
		System.out.println("Small: "+tab.getlastActivePlayer()+" BET: "+tab.getSystemPlayer(tab.getlastActivePlayer()).getPlayerBet()+" cards: "+tab.getSystemPlayer(tab.getlastActivePlayer()).countCards());
		tab.setState(nextState);
		//tab.Auto();
	    }
	}
	


