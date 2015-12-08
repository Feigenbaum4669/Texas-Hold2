
public class NewDeal extends TableState {
	
    public void Auto(Table tab){
	tab.setdealerButton(tab.getdealerButton()+1);
	tab.setState(tab.collectSmallBlindState);
	//tab.Auto();
    }
    

}
