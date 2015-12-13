
public class InitParams {

	public int smallBlind;
	public int bigBlind;	
	public Limit lim;
	public int fixed;
	public int maxRaise;
	public int gameSize;
	public int initCredit;
	
	InitParams(int gameSize, int initCredit, Limit lim, int smallBlind, int bigBlind, int fixed,int maxRaise){
		this.smallBlind=smallBlind;
		this.bigBlind=bigBlind;
		this.lim=lim;
		this.fixed=fixed;
		this.maxRaise=maxRaise;
		this.gameSize=gameSize;
		this.initCredit=initCredit;
	}
}
