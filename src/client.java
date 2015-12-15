
public class client {

	public static void main(String[] args) {
		int gameSize=4;
		int initCredit=200;
		Limit lim=Limit.no_limit;
		int smallBlind=10;
		int bigBlind=20;
		int fixed=2;
		int maxRaise=20;
		
		Player p0=new Bot("bot0");
		Player p1=new Bot("bot1");
		Player p2=new Bot("bot2");
		Player p3=new Bot("bot3");
		
		
		Table tab=new Table(gameSize,initCredit,lim,smallBlind,bigBlind,fixed,maxRaise);
		try{
		tab.addPlayer(p0);
		tab.addPlayer(p1);
		tab.addPlayer(p2);
		tab.addPlayer(p3);}
		catch(gameHasStartedException ex){
			System.out.println(ex.getMessage());
		}catch(NoofPlayersException ex2){
			System.out.println(ex2.getMessage());
		};
		try{
		tab.startGame();
		}catch(NoofPlayersException ex2){
			System.out.println(ex2.getMessage());
		};
		
	}

}
