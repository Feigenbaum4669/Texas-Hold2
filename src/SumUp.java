import java.util.LinkedList;

public class SumUp extends TableState {
	
	private TableState nextState;
	private LinkedList<Boolean> winners;
	private LinkedList<Integer> cardRanks;
	Integer winnersCount;
	Integer bestRank;
	Integer winner;
	HandEvaluator evaluator;

	public void Auto(Table tab){
		
		for(int i=0;i<tab.countPlayers();i++){
		System.out.println("player: "+i+"status: "+tab.getSystemPlayer(i).getPlayerStatus());
		}
		
		if(tab.countNonQuitPlayers()>1){
			
		nextState=tab.NewTurnState;
		winners=new LinkedList<Boolean>();
		//initWinnersFalse(tab);
		cardRanks=new LinkedList<Integer>();
		
		//wyłoń zwycięzców (sfoldowani i squitowani automatycznie przegrali)
		evaluator=new HandEvaluator();
		PlayerStatus status;
		for(int i=0;i<tab.countPlayers();i++){
		status=tab.getSystemPlayer(i).getPlayerStatus();
		if((status==PlayerStatus.folded)||(status==PlayerStatus.quit)){
			cardRanks.add(-1);
		}else{
		evaluator.initWithTableCards(tab.getTabofTableCards()).initWithHoleCards(tab.getTabofPlayerCards(i));
		cardRanks.add(evaluator.eval());
		}
		}
		
		winnersCount=0;
		bestRank=0;
		for(int i=0;i<cardRanks.size();i++){
			if(cardRanks.get(i)>bestRank){
				bestRank=cardRanks.get(i);
			}
		}
		
		for(int i=0;i<tab.countPlayers();i++){
			if(cardRanks.get(i)==bestRank){
				winnersCount+=1;
				winners.set(i,true);
			}
		}
		//zabierz karty graczom oraz ze stołu
		tab.cleanCards();
		//podziel bank między graczy (update kredytów)
		if(winnersCount==1){
			for(int i=0;i<winners.size();i++){
				if(winners.get(i)==true){
					winner=i;
					break;
				}
			}
		  tab.getSystemPlayer(winner).incrPlayerCredits(tab.getBank());
		}
		//wykasuj bank
		tab.setBank(0);
		
		//wykasuj bety graczom
		tab.cleanBets();
		
		//przywróć wszystkich graczy  fo stanu init (poza squitowanymi);
		tab.initializePlayers();
		
		//
		}else{
			nextState=tab.stopGameState;
			if(tab.countNonQuitPlayers()>0){
				try {
					tab.getSystemPlayer(tab.nextNonQuitPlayer(0)).notify("W grze pozostał jeden gracz. Koniec gry.");
				} catch (RunOutOfPlayersException e) {
					
				}
			}
			
		}
		
		tab.setState(nextState);
		//tab.Auto();
	    }
	/*
		private void initWinnersFalse(Table tab){
			for(int i=0;i<tab.countPlayers();i++){
				this.winners.add(false);
			}
		}
		*/
	

}
