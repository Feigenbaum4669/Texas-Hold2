import java.util.Random;

public class Bot extends Player {
	private int raiseConst = 20;
	private double emergency = 0.2;
	private int maxraise = 5;
	private int RaiseCounter = 0;
	private Integer RaiseProb = 5;// (od 0 do 10)
	private Random randomGenerator = new Random();
	boolean raise;
	private int maxMoves = 10;
	private int moveCounter;
	
	public Bot(String name) {
		super(name);
	}
	
	public VAction makeAction(GameInfo gi) {
		moveCounter += 1;
		this.PlayerCards = gi.playerCards;
		this.status = gi.playerStatus;
		this.Credits = gi.playerCredits;
		this.bet = gi.playerBets;
		this.hBet = gi.highestBet;
		this.bank = gi.pot;
		VAction va = new VAction();
		Integer randomInt;
		System.out.println("Make decision " + this.name);
		System.out.println("INFO: status: " + this.status
				+ " highestBet: " + hBet + " Bank: " + this.bank
				+ " credits: " + this.Credits + " bet: "
				+ this.bet);
		randomInt = RaiseProb + randomGenerator.nextInt(10);

		if (moveCounter > maxMoves) {
			va.action = Action.quit;
			return va;
		}

		if (randomInt >= 10) {
			raise = true;
		} else {
			raise = false;
		}

		if (this.status != PlayerStatus.under_max_bet) {
			va.action = Action.check;
			return va;
		}

		if ((raise) && (RaiseCounter < maxraise)
				&& (this.hBet - this.bet
						+ raiseConst <= this.Credits
								* emergency)) {
			va.action = Action.raise;
			va.value = raiseConst;
			++RaiseCounter;
		} else if (this.hBet - this.bet <= this.Credits) {
			va.action = Action.call;
		} else {
			va.action = Action.fold;
		}

		// where are Action.bet(1) and Action.all_in(5)? 

		System.out.println("command:" + va.action);
		System.out.println("value: " + va.value);

		return va;
	}
	public void notify(String msg) {
		System.out.println("Notka: " + msg + "\n");
	}

	@Override
	public void readAndSetName() {
		// TODO Auto-generated method stub
	}

}
