import java.util.Random;

public class Bot extends Player {
	
	private int j=0;
	private int raiseConst=20;
	private double emergency=0.2;
	private int maxraise=5;
	private int RaiseCounter=0;
	private Integer RaiseProb=5;//(od 0 do 10)
	private Integer randomInt;
	private Random randomGenerator=new Random();
	boolean raise;
	private int maxMoves=10;
	private int moveCounter;

	public Bot(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public VAction makeAction(gameInfo gi){
		moveCounter+=1;
		this.PlayerCards=gi.PlayerCards;
		this.status=gi.ThisPlayerStatus;
		this.Credits=gi.ThisPlayerCredits;
		this.Bet=gi.ThisPlayerBets;
		this.hBet=gi.highestBet;
		this.bank=gi.bank;
		VAction va= new VAction();
		Integer randomInt;
		System.out.println("Make decision "+this.name);
		System.out.println("INFO: status: "+this.status+" highestBet: "+hBet+" Bank: "+this.bank+" credits: "+this.Credits+" bet: "+this.Bet);
		randomInt=RaiseProb+randomGenerator.nextInt(10);
		j=0;
		if(moveCounter<=maxMoves){
		if(randomInt>=10){
			raise=true;
		}else{
			raise=false;
		}
		
		if(this.status==PlayerStatus.under_max_bet){
			if((raise)&&(RaiseCounter<maxraise)&&(this.hBet-this.Bet+raiseConst<=this.Credits*emergency)){
				i=3;
				j=raiseConst;
				RaiseCounter+=1;
			}else if(this.hBet-this.Bet<=this.Credits){
				i=2;
			}else
			{
				i=4;
			}		
		}else{
			i=0;
		}
		}else{
			i=6;
		}
			
		switch(i){
		case 0:
		va.action=Action.check;
		break;
		case 1:
		va.action=Action.bet;
		break;
		case 2:
		va.action=Action.call;
		break;
		case 3:
		va.action=Action.raise;
		break;
		case 4:
		va.action=Action.fold;
		break;
		case 5:
		va.action=Action.all_in;
		break;
		case 6:
			va.action=Action.quit;	
		break;
					}	
		va.value=j;
		
		System.out.println("command:"+va.action);
		System.out.println("value: "+j);
		
		return va;
		}
	public void notify(String msg){
		System.out.println("Notka: "+msg+"\n");
		
	}

}
