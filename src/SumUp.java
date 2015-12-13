
public class SumUp extends TableState {

	public void Auto(Table tab){
		for(int i=0;i<tab.countPlayers();i++){
		System.out.println("player: "+i+"status: "+tab.getSystemPlayer(i).getPlayerStatus());
		}
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
		
		//przywróć wszystkich niesfoldowanych fo stanu init;
		tab.initializeNonFoldedPlayers();
		
		tab.setState(tab.NewTurnState);
		//tab.Auto();
	    }
	

}
