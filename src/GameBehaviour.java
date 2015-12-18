import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameBehaviour {

	public Table tab;
	public int gameSize = 4;
	public int initCredit = 200;
	public Limit lim = Limit.no_limit;
	public int smallBlind = 10;
	public int bigBlind = 20;
	public int fixed = 2;
	public int maxRaise = 20;

	Player p0 = new Bot("bot0");
	Player p1 = new Bot("bot1");
	Player p2 = new Bot("bot2");
	Player p3 = new Bot("bot3");

	@Before
	public void setUp() throws Exception {

		tab = new Table(gameSize, initCredit, lim, smallBlind, bigBlind, fixed, maxRaise);
	}

	@After
	public void tearDown() throws Exception {
		tab = null;
	}

	// Sprawdzenie, czy żetony nie giną podczas gry.
	@Test
	public void testCoinsDoNotDisappear() throws NoofPlayersException, gameHasStartedException {
		tab.addPlayer(p0);
		tab.addPlayer(p1);
		tab.addPlayer(p2);
		tab.addPlayer(p3);
		tab.startGame();
		int sumofCredits = 0;
		int sumofBets = 0;
		for (int i = 0; i < tab.countPlayers(); i++) {
			sumofCredits += tab.getSystemPlayer(i).getPlayerCredits();
		}
		for (int i = 0; i < tab.countPlayers(); i++) {
			sumofBets += tab.getSystemPlayer(i).getPlayerBet();
		}
		assertEquals(tab.getState(), tab.stopGameState);
		assertEquals(sumofBets, (int) 0);
		assertEquals(sumofCredits, gameSize * initCredit);
	}

	// FirstBid
	@Test
	public void testFirstBid() throws NoofPlayersException, gameHasStartedException, NotEnoughCreditsException {

		VAction VAct = new VAction();
		VAct.value = 10;

		tab.addPlayer(p0);
		tab.addPlayer(p1);
		tab.addPlayer(p2);
		tab.addPlayer(p3);
		
		tab.initializeCredits();

		tab.FirstBidState.setTab(tab);
		tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.under_max_bet);
		tab.getSystemPlayer(0).incrPlayerBet(smallBlind);
		tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.max_bet_bb);
		tab.getSystemPlayer(1).incrPlayerBet(bigBlind);
		tab.getSystemPlayer(2).setPlayerStatus(PlayerStatus.max_bet_nbb);
		tab.getSystemPlayer(2).incrPlayerBet(bigBlind);
		tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.under_max_bet);
		
		
		tab.FirstBidState.setPlayer(3);
		VAct.action = Action.raise;
		tab.FirstBidState.Decode(VAct);
		
		assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.under_max_bet);
		assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
		assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.under_max_bet);
		assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.max_bet_nbb);
		assertEquals((int) tab.getSystemPlayer(3).getPlayerBet(),(int) tab.getMaxBet());
		
		tab.FirstBidState.setPlayer(0);
		VAct.action = Action.call;
		tab.FirstBidState.Decode(VAct);
		
		assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.max_bet_nbb);
		assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
		assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.under_max_bet);
		assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.max_bet_nbb);
		assertEquals((int) tab.getSystemPlayer(0).getPlayerBet(),(int) tab.getMaxBet());
		
		tab.FirstBidState.setPlayer(2);
		VAct.action = Action.raise;
		tab.FirstBidState.Decode(VAct);
		
		assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.under_max_bet);
		assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
		assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.max_bet_nbb);
		assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
		
		tab.getSystemPlayer(0).setPlayerCredits(1);
		
		tab.FirstBidState.setPlayer(0);
		VAct.action = Action.all_in;
		tab.FirstBidState.Decode(VAct);
		
		assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.all_in);
		assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
		assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.max_bet_nbb);
		assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
		assertEquals((int) tab.getSystemPlayer(0).getPlayerCredits(),(int) 0);
		
		tab.FirstBidState.setPlayer(2);
		VAct.action = Action.fold;
		tab.FirstBidState.Decode(VAct);
		
		assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.all_in);
		assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
		assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.folded);
		assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
		
		tab.FirstBidState.setPlayer(0);
		VAct.action = Action.call;
		tab.FirstBidState.Decode(VAct);
		
		assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.quit);
		assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
		assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.folded);
		assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
		
		
		tab.FirstBidState.setPlayer(3);
		VAct.action = Action.quit;
		tab.FirstBidState.Decode(VAct);
		
		assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.quit);
		assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
		assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.folded);
		assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.quit);
	
	}
	
	// SecondBid
		@Test
		public void testSecondBid() throws NoofPlayersException, gameHasStartedException, NotEnoughCreditsException {

			VAction VAct = new VAction();
			VAct.value = 10;

			tab.addPlayer(p0);
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.initializeCredits();
			

			tab.SecondBidState.setTab(tab);
			tab.SecondBidState.setInitialBet(30);
			tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.getSystemPlayer(0).setPlayerBet(30);
			tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.getSystemPlayer(1).setPlayerBet(30);
			tab.getSystemPlayer(2).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.getSystemPlayer(2).setPlayerBet(30);
			tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.getSystemPlayer(3).setPlayerBet(30);
			
			tab.SecondBidState.setPlayer(3);
			VAct.action = Action.bet;
			tab.SecondBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals((int) tab.getSystemPlayer(3).getPlayerBet(),(int) tab.getMaxBet());
			
			tab.SecondBidState.setPlayer(0);
			VAct.action = Action.call;
			tab.SecondBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals((int) tab.getSystemPlayer(0).getPlayerBet(),(int) tab.getMaxBet());
			
			tab.SecondBidState.setPlayer(2);
			VAct.action = Action.raise;
			tab.SecondBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
			
			tab.getSystemPlayer(0).setPlayerCredits(1);
			
			tab.SecondBidState.setPlayer(0);
			VAct.action = Action.all_in;
			tab.SecondBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.all_in);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals((int) tab.getSystemPlayer(0).getPlayerCredits(),(int) 0);
			
			tab.SecondBidState.setPlayer(2);
			VAct.action = Action.fold;
			tab.SecondBidState.Decode(VAct);
		
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.all_in);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.folded);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
		
			
			tab.SecondBidState.setPlayer(3);
			VAct.action = Action.quit;
			tab.SecondBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.all_in);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.folded);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.quit);
			
			
			

		}
		//ThirdBid
		@Test
		public void testThirdBid() throws NoofPlayersException, gameHasStartedException, NotEnoughCreditsException {

			VAction VAct = new VAction();
			VAct.value = 10;

			tab.addPlayer(p0);
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.initializeCredits();
			

			tab.ThirdBidState.setTab(tab);
			tab.ThirdBidState.setInitialBet(30);
			tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.getSystemPlayer(0).setPlayerBet(30);
			tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.getSystemPlayer(1).setPlayerBet(30);
			tab.getSystemPlayer(2).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.getSystemPlayer(2).setPlayerBet(30);
			tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.getSystemPlayer(3).setPlayerBet(30);
			
			tab.ThirdBidState.setPlayer(3);
			VAct.action = Action.bet;
			tab.ThirdBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals((int) tab.getSystemPlayer(3).getPlayerBet(),(int) tab.getMaxBet());
			
			tab.ThirdBidState.setPlayer(0);
			VAct.action = Action.call;
			tab.ThirdBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals((int) tab.getSystemPlayer(0).getPlayerBet(),(int) tab.getMaxBet());
			
			tab.ThirdBidState.setPlayer(2);
			VAct.action = Action.raise;
			tab.ThirdBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
			
			tab.getSystemPlayer(0).setPlayerCredits(1);
			
			tab.ThirdBidState.setPlayer(0);
			VAct.action = Action.all_in;
			tab.ThirdBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.all_in);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals((int) tab.getSystemPlayer(0).getPlayerCredits(),(int) 0);
			
			tab.ThirdBidState.setPlayer(2);
			VAct.action = Action.fold;
			tab.ThirdBidState.Decode(VAct);
		
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.all_in);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.folded);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
		
			
			tab.ThirdBidState.setPlayer(3);
			VAct.action = Action.quit;
			tab.ThirdBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.all_in);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.folded);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.quit);
			
			
			
		}
		
		// LastBid
		@Test
		public void testLastBid() throws NoofPlayersException, gameHasStartedException, NotEnoughCreditsException {

			VAction VAct = new VAction();
			VAct.value = 10;

			tab.addPlayer(p0);
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.initializeCredits();
			

			tab.LastBidState.setTab(tab);
			tab.LastBidState.setInitialBet(30);
			tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.getSystemPlayer(0).setPlayerBet(30);
			tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.getSystemPlayer(1).setPlayerBet(30);
			tab.getSystemPlayer(2).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.getSystemPlayer(2).setPlayerBet(30);
			tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.max_bet_nbb);
			tab.getSystemPlayer(3).setPlayerBet(30);
			
			tab.LastBidState.setPlayer(3);
			VAct.action = Action.bet;
			tab.LastBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals((int) tab.getSystemPlayer(3).getPlayerBet(),(int) tab.getMaxBet());
			
			tab.LastBidState.setPlayer(0);
			VAct.action = Action.call;
			tab.LastBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals((int) tab.getSystemPlayer(0).getPlayerBet(),(int) tab.getMaxBet());
			
			tab.LastBidState.setPlayer(2);
			VAct.action = Action.raise;
			tab.LastBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
			
			tab.getSystemPlayer(0).setPlayerCredits(1);
			
			tab.LastBidState.setPlayer(0);
			VAct.action = Action.all_in;
			tab.LastBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.all_in);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.max_bet_nbb);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals((int) tab.getSystemPlayer(0).getPlayerCredits(),(int) 0);
			
			tab.LastBidState.setPlayer(2);
			VAct.action = Action.fold;
			tab.LastBidState.Decode(VAct);
		
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.all_in);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.folded);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.under_max_bet);
		
			
			tab.LastBidState.setPlayer(3);
			VAct.action = Action.quit;
			tab.LastBidState.Decode(VAct);
			
			assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.all_in);
			assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.under_max_bet);
			assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.folded);
			assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.quit);
			
			
			

			
			

		}

}
