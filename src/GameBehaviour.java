import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameBehaviour {

	public Table tab;
	public int gameSize=4;
	public int initCredit=200;
	public Limit lim=Limit.no_limit;
	public int smallBlind=10;
	public int bigBlind=20;
	public int fixed=2;
	public int maxRaise=20;
	
	Player p0=new Bot("bot0");
	Player p1=new Bot("bot1");
	Player p2=new Bot("bot2");
	Player p3=new Bot("bot3");
	
	@Before
	public void setUp() throws Exception {
		
		tab=new Table(gameSize,initCredit,lim,smallBlind,bigBlind,fixed,maxRaise);
	}

	@After
	public void tearDown() throws Exception {
		tab=null;
	}
	
	// Sprawdzenie, czy żetony nie giną podczas gry.
		@Test
		public void testCoinsDoNotDisappear() throws NoofPlayersException, gameHasStartedException {
			tab.addPlayer(p0);
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.startGame();
			int sumofCredits=0;
			int sumofBets=0;
			for(int i=0;i<tab.countPlayers();i++){
				sumofCredits+=tab.getSystemPlayer(i).getPlayerCredits();
			}
			for(int i=0;i<tab.countPlayers();i++){
				sumofBets+=tab.getSystemPlayer(i).getPlayerBet();
			}
			assertEquals(tab.getState(),tab.stopGameState);
			assertEquals(sumofBets,(int) 0);
			assertEquals(sumofCredits, gameSize*initCredit);
		}
		
		// FirstBid
				@Test
				public void testFirstBid() throws NoofPlayersException, gameHasStartedException {
					tab.addPlayer(p0);
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					//tab.startGame();
					int sumofCredits=0;
					int sumofBets=0;
					for(int i=0;i<tab.countPlayers();i++){
						sumofCredits+=tab.getSystemPlayer(i).getPlayerCredits();
					}
					for(int i=0;i<tab.countPlayers();i++){
						sumofBets+=tab.getSystemPlayer(i).getPlayerBet();
					}
					assertEquals(tab.getState(),tab.stopGameState);
					
				}

}
