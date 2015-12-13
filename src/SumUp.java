import java.util.LinkedList;

public class SumUp extends TableState {
	
	private LinkedList<Boolean> winners;
	Integer winnersCount;

	public void Auto(Table tab){
		
		for(int i=0;i<tab.countPlayers();i++){
		System.out.println("player: "+i+"status: "+tab.getSystemPlayer(i).getPlayerStatus());
		}
		
		winners=new LinkedList<Boolean>();
		//wyłoń zwycięzców spośród wszystkich niesflodowanych
		//...
		//zabierz karty graczom oraz ze stołu
		tab.cleanCards();
		//podziel bank między graczy (update kredytów)
		  tab.getSystemPlayer(0).incrPlayerCredits(tab.getBank());
		//wykasuj bank
		tab.setBank(0);
		
		//wykasuj bety graczom
		tab.cleanBets();
		
		
		
		//przywróć wszystkich graczy  fo stanu init;
		tab.initializePlayers();
		
		//
		
		tab.setState(tab.NewTurnState);
		//tab.Auto();
	    }
	

}
