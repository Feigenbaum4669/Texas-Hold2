
public class FirstDeal extends TableState  {

	
	public void Auto(Table tab){
		try{
		tab.deal(2);
		}catch(sizeofSetException ex){
			System.err.println("sizeofSetException: " + ex.getMessage());
		}
		tab.setState(tab.FirstBidState);
		//tab.Auto();
	    }

}
