
public class TableContext implements TableState {
	
	private TableState TState;
	
	public void setState(TableState state ){
		this.TState=state;
	}
	
	public TableState getState(){
		return this.TState;
	}

	@Override
	public void Check() {
		this.TState.Check();
		
	}

	@Override
	public void Bet() {
		this.TState.Bet();
		
	}

	@Override
	public void Raise() {
		this.TState.Raise();
		
	}

	@Override
	public void Call() {
		this.TState.Call();
		
	}

	@Override
	public void Fold() {
		this.TState.Fold();
		
	}

	@Override
	public void AllIn() {
		this.TState.AllIn();
		
	}

}
