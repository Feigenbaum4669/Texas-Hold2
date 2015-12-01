
public class NewTurn extends TableState {
	
    public void Auto(TableAppliance tab){
	tab.setdealerButton(tab.getdealerButton()+1);
	tab.setState(tab.collectSmallBlindState);
	//tab.Auto();
    }
    

}
