
public abstract class TableAppliance {
	
	protected TableState TState;
	
	protected startGame startGameState;
	protected CollectBigBlind collectBigBlindState;
	protected CollectSmallBlind collectSmallBlindState;
	protected FirstBid FirstBidState;
	protected FirstDeal FirstDealState;
	protected Flop FlopState;
	protected LastBid LastBidState;
	protected River RiverState;
	protected SecondBid SecondBidState;
	protected SumUp SumUpState;
	protected ThirdBid ThirdBidState;
	protected Turn TurnState;
	protected NewTurn NewTurnState;
	
	TableAppliance(){
		startGameState=new startGame();
		 collectBigBlindState=new CollectBigBlind();
		 collectSmallBlindState=new CollectSmallBlind();
		 FirstBidState=new FirstBid();
		 FirstDealState=new FirstDeal();
		FlopState=new Flop();
		 LastBidState=new LastBid();
		RiverState=new River();
		SecondBidState=new SecondBid();
		 SumUpState=new SumUp();
		 ThirdBidState=new ThirdBid();
		 TurnState=new Turn();
	}
	
	protected abstract void initializeCredits();
	protected abstract void setCredits(Integer p,Integer value);
	protected abstract int getCredits(Integer p);
	protected abstract void setdealerButton(Integer i);
	public abstract int getdealerButton();
	protected abstract void setBank(Integer b);
	public abstract int getBank();
	public abstract int getSmallBlind();
	public abstract int getBigBlind();
	protected abstract void newDeck();
	protected abstract void deal(Integer sizeofSet)throws sizeofSetException;
	protected abstract void addCardOnTable(Card c);
	protected abstract Card takeCardFromDeck();
	
	
	
	
	protected void setState(TableState state ){
		Auto();
		
	}
	
	public TableState getState(){
		return TState;
	}

	public void Check() {
		TState.Check(this);
		
	}

	
	public void Bet() {
		TState.Bet(this);
		
	}

	
	public void Raise() {
		TState.Raise(this);
		
	}


	public void Call() {
		TState.Call(this);
		
	}

	
	public void Fold() {
	    TState.Fold(this);
		
	}

	
	public void AllIn() {
	   TState.AllIn(this);
		
	}
	
	protected void Auto(){
		TState.Auto(this);
	}

	

}
