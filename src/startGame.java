
public class startGame extends TableState {
	
	public void Auto(Table tab){
	tab.initializeCredits();
	tab.setdealerButton(0);
	tab.setState(tab.NewTurnState);
	//tab.Auto();
	}
}
