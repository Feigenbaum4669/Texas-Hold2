import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

class IntsComp implements Comparator<Integer>{
	 
    @Override
    public int compare(Integer e1, Integer e2) {
        if(e1 > e2){
            return 1;
        } else {
            return -1;
    }
}
}


public class SumUp extends TableState {
	
	private TableState nextState;
	private LinkedList<Boolean> winners;
	private LinkedList<Integer> cardRanks;
	private LinkedList<Integer> Pots;
	private LinkedList<Integer> PotsPerGamer;
	Integer winnersCount;
	Integer bestRank;
	Integer winner;
	Integer prize;
	HandEvaluator evaluator;

	public void Auto(Table tab){
		
		for(int i=0;i<tab.countPlayers();i++){
		System.out.println("player: "+i+"status: "+tab.getSystemPlayer(i).getPlayerStatus());
		}
		
		if(tab.countNonQuitPlayers()>0){
			nextState=tab.NewTurnState;
			if(tab.countNonQuitPlayers()==1){
				nextState=tab.stopGameState;
			}
		
		winners=new LinkedList<Boolean>();
		//initWinnersFalse(tab);
		cardRanks=new LinkedList<Integer>();
		Pots=new LinkedList<Integer>();
		PotsPerGamer=new LinkedList<Integer>();
		
		//wyłoń zwycięzców (sfoldowani i squitowani automatycznie przegrali)
		evaluator=new HandEvaluator();
		PlayerStatus status;
		for(int i=0;i<tab.countPlayers();i++){
		status=tab.getSystemPlayer(i).getPlayerStatus();
		if((status==PlayerStatus.folded)||(status==PlayerStatus.quit)){
			cardRanks.add(80000);
		}else{
		evaluator.initWithTableCards(tab.getTabofTableCards()).initWithHoleCards(tab.getTabofPlayerCards(i));
		cardRanks.add(evaluator.eval());
		}
		}
		//wysw ranki
		for(int i=0;i<cardRanks.size();i++){
			System.out.println(cardRanks.get(i));
		}
		
		//tworzy pule boczne
	    IntsComp comp=new IntsComp();
	    LinkedList <Integer> SortedBets=tab.getPlayersBets();
	    Collections.sort(SortedBets,comp);
	    Integer no=SortedBets.size();
	    Pots.add(SortedBets.get(0)*no);
	    PotsPerGamer.add(SortedBets.get(0));
	    for(int i=1;i<SortedBets.size();i++){
	    	int diff=SortedBets.get(i)-SortedBets.get(i-1);
	    	//System.out.println("Diff: "+diff);
	    	if(diff>0){
	    		//System.out.println("Diff is greter. Add: "+diff*(no-i));
	    		Pots.add(diff*(no-i));
	    		PotsPerGamer.add(diff);
	    	}
	    }
	    
	    for(int i=0;i<tab.countPlayers();i++){
  			System.out.println("BETS:"+tab.getSystemPlayer(i).getPlayerBet());
  		}
	    
	  //wysw pule
	  		for(int i=0;i<Pots.size();i++){
	  			System.out.println("POTS:"+Pots.get(i));
	  		}
	  		for(int i=0;i<PotsPerGamer.size();i++){
	  			System.out.println("PGamer:"+PotsPerGamer.get(i));
	  		}
	  		
	  		
	  //podziel bank między graczy (update kredytów)
	    for(int i=0;i<Pots.size();i++){
	    	prize=0;
	    	findWinners(i,tab);
	    	if(winnersCount!=0){
	    	prize=Pots.get(i)/winnersCount;
	    	}
	    	for(int j=0;j<winners.size();j++){
	    		if(winners.get(j)==true){
	    			tab.getSystemPlayer(j).incrPlayerCredits(prize);
	    			System.out.println("Winner: "+j+ "Pot: "+i+" +PRIZE: "+prize);
	    		}
	    	}
	    	
	    }
		/*
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
		*/
		//zabierz karty graczom oraz ze stołu
		tab.cleanCards();
	
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
	
		private void falseWinners(){
		for(int i=0;i<winners.size(); i++){
			winners.set(i, false);
		}
		}
		
		private int sumByPotsPerGamer(int p){
			int sum=0;
			for(int i=0;i<=p;i++){
				sum+=PotsPerGamer.get(i);
			}
			return sum;
		}
	

		
	private void findWinners(Integer pot,Table tab){
		winnersCount=0;
		initWinnersFalse(tab);
		int maxRank=79000;
		int plRank;
		int plBet;
		for(int i=0;i<tab.countPlayers();i++){
			plBet=tab.getSystemPlayer(i).getPlayerBet();
			plRank=cardRanks.get(i);
			
				if(plBet>=sumByPotsPerGamer(pot)){
					if(plRank<maxRank){
						maxRank=plRank;
					}
				}
			
		}
		
		for(int i=0;i<tab.countPlayers();i++){
			if(cardRanks.get(i)==maxRank){
				winnersCount+=1;
				winners.set(i,true);
			}
		}
		
	}
	
	private void initWinnersFalse(Table tab){
		for(int i=0;i<tab.countPlayers();i++){
			this.winners.add(false);
		}
	}
	
	

	
	}
	
		
		
	


