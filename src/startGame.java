
public class startGame extends TableState {
	
    public void Auto(TableAppliance tab){
	tab.initializeCredits();
	tab.setdealerButton(1);
	tab.setState(tab.collectSmallBlindState);
	tab.Auto();
    }
    

}
