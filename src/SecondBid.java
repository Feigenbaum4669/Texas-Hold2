
public class SecondBid extends TableState {
	
	//private TableState nextState;
	private Table tab;
	private Integer CurrPlayer;
	int raiseCounter=0;
	int initialbet;
	
	private void setTab(Table tab){
		this.tab=tab;
	}
	private void setPlayer(Integer CurrPlayer){
		this.CurrPlayer=CurrPlayer;
	}
	
	public void Auto(Table tab){
		setTab(tab);
		initialbet=tab.getMaxBet();
		tab.setlastActivePlayer(tab.getdealerButton());
		
		if(tab.countActivePlayers()>1){
			
		for(int i=0;i<tab.countActivePlayers();i++){
			try{
				setPlayer(tab.nextActivePlayer(tab.getlastActivePlayer()));
				tab.setlastActivePlayer(CurrPlayer);
				Decode(getAction());
			}catch(RunOutOfActivePlayersException ex){
				break;
			}
				
		}
		
		while(tab.BetsAreEqual()==false){
			try{
				setPlayer(tab.nextActivePlayer(tab.getlastActivePlayer()));
				tab.setlastActivePlayer(CurrPlayer);
				Decode(getAction());
			}catch(RunOutOfActivePlayersException ex){
				break;
			}
		}
		}
		tab.setState(tab.TurnState);
		//tab.Auto();
	
		}

	
	public void Check() {
		PlayerStatus status=tab.getSystemPlayer(CurrPlayer).getPlayerStatus();
		if(status==PlayerStatus.under_max_bet){
			tab.getSystemPlayer(CurrPlayer).notify("Nie można podtrzymać stawki - została przebita.");
			Decode(getAction());
		}else
		{
			//player zcheckował//
		}
	}
	/*
	 * public void Check() {
		// TODO Auto-generated method stub
		if(tab.getSystemPlayer(CurrPlayer).getPlayerStatus()!=PlayerStatus.max_bet_bb)
		if(tab.getBets().get(player)<tab.getHighestBet()){
		tab.getPlayer(player).notify("Nie można podtrzymać stawki - została przebita.");
		Decode(getAction());
		}
	}
	 */

	//val-wartość stawki jaką dodaje gracz
	public void Bet(Integer val) {
		int maxBet=tab.getMaxBet();
		if(initialbet<maxBet){
		tab.getSystemPlayer(CurrPlayer).notify("Pierwszy zakład już został postawiony!");
		Decode(getAction());
	   }else{
			if(val>0){
				try{
			tab.getSystemPlayer(CurrPlayer).incrPlayerBet(val);
			tab.incrBank(val);
			tab.getSystemPlayer(CurrPlayer).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.ChangeActivePlayersStatusExcept(PlayerStatus.under_max_bet, CurrPlayer);
				}catch(NotEnoughCreditsException ex){
					
				}
		}else{
			tab.getSystemPlayer(CurrPlayer).notify("Pierwszy zakład musi być >0!");
			Decode(getAction());
		}
		}
	}

	 //int-wartośc o jaką gracz chce przebić
	public void Raise(Integer betincr) {
		int maxBet=tab.getMaxBet();
		int currBet=tab.getSystemPlayer(CurrPlayer).getPlayerBet();
		int maxRaiseValue=tab.getMaxRaise();
		int maxRaiseCount=tab.getFixed();
		int pot=tab.getBank();
		//int bigBlind=tab.getBigBlind();
		PlayerStatus status=tab.getSystemPlayer(CurrPlayer).getPlayerStatus();
		
		Limit lim=tab.getLimit();
		
	if((status==PlayerStatus.max_bet_nbb)){
		tab.getSystemPlayer(CurrPlayer).notify("Nie mozna przebijać swoich zakładów!");
		Decode(getAction());
	}else if(betincr>0) {
		int diff=maxBet+betincr-currBet;
		if((lim==Limit.no_limit)||(lim==Limit.fixed_limit&&betincr<=maxRaiseValue&&raiseCounter<maxRaiseCount)||(lim==Limit.pot_limit&&betincr<=pot)){
		try{
			tab.getSystemPlayer(CurrPlayer).incrPlayerBet(diff);
			raiseCounter+=1;
			tab.getSystemPlayer(CurrPlayer).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.ChangeActivePlayersStatusExcept(PlayerStatus.under_max_bet, CurrPlayer);
		}catch(NotEnoughCreditsException ex){
			tab.getSystemPlayer(CurrPlayer).notify("Za mało żetonów aby przebić maksymalny zakład o podaną stawkę!");
			Decode(getAction());
		}}
		else{
			if(lim==Limit.pot_limit){
			tab.getSystemPlayer(CurrPlayer).notify("Nie można podbijać o więcej niż jest w puli! (pot-limit)");
			}else{
				if(raiseCounter>=maxRaiseCount){
					tab.getSystemPlayer(CurrPlayer).notify("Przekroczono dopuszczalną liczbę podbić w tej rundzie licytacji (fixed-limit)!");
				}else{
					tab.getSystemPlayer(CurrPlayer).notify("Podana wartość przekracz limit podbicia (fixed-limit)!");
				}
			
			}
			Decode(getAction());
		}
		
	}else{
		tab.getSystemPlayer(CurrPlayer).notify("Trzeba przebić o wartość większą niz 0!");
		Decode(getAction());
	}
	
	}

	
	public void Call()  {
		if(tab.getSystemPlayer(CurrPlayer).getPlayerBet()<tab.getMaxBet()){
			try{
				int currBet=tab.getSystemPlayer(CurrPlayer).getPlayerBet();
			   int maxBet=tab.getMaxBet();
				tab.getSystemPlayer(CurrPlayer).incrPlayerBet(maxBet-currBet);
				tab.getSystemPlayer(CurrPlayer).setPlayerStatus(PlayerStatus.max_bet_nbb);
			}catch(NotEnoughCreditsException ex){
				tab.getSystemPlayer(CurrPlayer).notify("Za mało żetonów aby wyrównać!");
				Decode(getAction());
			}
		}else{
			tab.getSystemPlayer(CurrPlayer).notify("Aktualny zakład jest już najwyższy!");
			//throw new BidLogicErrorException("Błąd licytacji: kolejka wróciła do gracza o najwyższym zakładzie!");
			Decode(getAction());
		}	
	}
	//OK
	public void Fold() {
		tab.getSystemPlayer(CurrPlayer).setPlayerStatus(PlayerStatus.folded);	
	}

	//OK
	public void AllIn() {
		int currBet=tab.getSystemPlayer(CurrPlayer).getPlayerBet();
		int maxBet=tab.getMaxBet();
		int currCredits=tab.getSystemPlayer(CurrPlayer).getPlayerCredits();
		if((maxBet-currBet)>currCredits){
			tab.getSystemPlayer(CurrPlayer).allIn();
			tab.getSystemPlayer(CurrPlayer).setPlayerStatus(PlayerStatus.all_in);
		}else{
			tab.getSystemPlayer(CurrPlayer).notify("Masz wystarczająco dużo żetonów aby wyrównać lub już wyrównałeś!");
			Decode(getAction());
		}
		
		
	}
		
	private VAction getAction(){
		return tab.getSystemPlayer(CurrPlayer).makeAction(tab.getgameInfo(CurrPlayer));
	}
	
	public void Decode(VAction a)  {
		
		switch(a.action){
		case fold:
		Fold();
		break;
		case check:
		Check();
		break;
		case bet:
		Bet(a.value);
		break;
		case raise:
		Raise(a.value);
		break;
		case call:
		Call();
		break;
		case all_in:
		AllIn();
		break;
					}
		
		}
}



