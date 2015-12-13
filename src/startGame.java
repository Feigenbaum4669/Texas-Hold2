import java.util.Random;

public class startGame extends TableState {
	
	
	public void Auto(Table tab){
	Random randomGenerator=new Random();
	Integer randomInt=randomGenerator.nextInt(tab.countPlayers());
	System.out.println("dEALER: "+randomInt);
	tab.initializeCredits();
	tab.setdealerButton(randomInt);
	tab.setState(tab.NewTurnState);
	//tab.Auto();
	}
}
