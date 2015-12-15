import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FirstBidTest {

	public Table tab;
	public int gameSize=4;
	public int initCredit=200;
	public Limit lim=Limit.no_limit;
	public int smallBlind=10;
	public int bigBlind=20;
	public int fixed=2;
	public int maxRaise=20;
	
	Player p0=new Player("p0");
	Player p1=new Player("p1");
	Player p2=new Player("p2");
	Player p3=new Player("p3");
	
	@Before
	public void setUp() throws Exception {
		
		tab=new Table(gameSize,initCredit,lim,smallBlind,bigBlind,fixed,maxRaise);
	}

	@After
	public void tearDown() throws Exception {
		tab=null;
	}
	/* Sprawdzenie 
		@Test
		public void Test() throws gameHasStartedException, NoofPlayersException {
			tab.addPlayer(p0);
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.startGame();
			
			assertEquals(tab.getState(), tab.stopGameState);
		}
*/
}
