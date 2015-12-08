
public class FirstBid extends TableState {
	
	private Table tab;
	private Integer player;
	
	private void setTab(Table tab){
		this.tab=tab;
	}
	private void setPlayer(Integer player){
		this.player=player;
	}
	
	public void Auto(Table tab){
		setTab(tab);
		
		for(int i=0;i<tab.countPlayers();i++){
				setPlayer(tab.nextPlayer(tab.getdealerButton()+3,i));
				Decode(getAction());
		}
		tab.setState(tab.FlopState);
		//tab.Auto();
	
		}

	
	public void Check() {
		// TODO Auto-generated method stub
		if(tab.getBets().get(player)<tab.getHighestBet()){
		tab.getPlayer(player).notify("Nie można podtrzymać stawki - została przebita.");
		Decode(getAction());
		}
	}

	
	public void Bet(Integer val) {
		tab.getPlayer(player).notify("Pierwszy zakład już został postawiony!");
		Decode(getAction());
		
	}

	
	public void Raise(Integer bet) {
		
	if(tab.getHighestBet()==tab.getBets.get(player)){
		tab.getPlayer(player).notify("Nie mozna przebijać swoich zakładów!");
		Decode(getAction());
	}else if(bet>tab.getHighestBet()){
		if()
		
	}else{
		tab.getPlayer(player).notify("Trzeba założyć więcej niż najwyższy aktualny zakład!");
		Decode(getAction());
	}
	
	}

	
	public void Call() {
	if(tab.getBets().get(player)<tab.getHighestBet()){
		tab.setBet(player, tab.getHighestBet());
		tab.setBank(tab.getBank()+tab.getHighestBet());
		tab.setCredit(player, tab.getCredits(player)-tab.getHighestBet());
	}else{
		tab.getPlayer(player).notify("Aktualna stawka jest już najwyższa.");
		//Decode(getAction());
	}
		
	}

	
	public void Fold() {
		tab.setFolded(player, true);	
	}

	
	public void AllIn() {
		if((tab.getCredits(player)+tab.getBets().get(player))<tab.getHighestBet()){
			tab.setAllIn(player, true);
		}else{
			tab.getPlayer(player).notify("Masz wystarczająco dużo żetonów aby wyrównać!");
			Decode(getAction());
		}
		
	}
	
	//public void exit() {
	//	tab.getPlayer(player).notify("Nie można zrezygnować z gry w trakcie rozdania!");
	//	Decode(getAction());
	//}
	
	private VAction getAction(){
		return tab.getPlayer(player).makeAction(tab.getgameInfo(player));
	}
	
	public void Decode(VAction a){
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
