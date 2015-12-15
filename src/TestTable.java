import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class TestTable {
	
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

	// Sprawdzenie, czy na początku nie ma żadnych graczy.
	@Test
	public void testnoPlayers() {
		assertEquals(tab.countPlayers(), (int) 0);
	}
	
	// Sprawdzenie, dodawania graczy 
	@Test
	public void testaddPlayers() throws gameHasStartedException, NoofPlayersException {
		tab.addPlayer(p0);
		tab.addPlayer(p1);
		tab.addPlayer(p2);
		tab.addPlayer(p3);
		assertEquals(tab.countPlayers(), (int) 4);
	}
	
	// Sprawdzenie limitu na liczbę graczy.
	@Test(expected = NoofPlayersException.class)
	public void testMaxPlayers() throws NoofPlayersException, gameHasStartedException {
		tab.addPlayer(p0);
		tab.addPlayer(p1);
		tab.addPlayer(p2);
		tab.addPlayer(p3);
		tab.addPlayer(p0);
		tab.addPlayer(p1);
		tab.addPlayer(p2);
		tab.addPlayer(p3);
		tab.addPlayer(p0);
		tab.addPlayer(p1);
		tab.addPlayer(p2);
	}
	
	// Sprawdzenie, czy gra uruchomi się ze zbyt mała liczba graczy.
		@Test(expected = NoofPlayersException.class)
		public void testgameStartsWithFewPlayers() throws NoofPlayersException, gameHasStartedException {
			tab.addPlayer(p0);
			tab.startGame();
		}
		
		// Sprawdzenie, czy generuje sie poprwanie nowa talia.
		@Test
		public void testnewGame() {
			tab.newDeck();
			assertEquals(tab.countDeck(), (int) 52);
		}
		
		// Sprawdzenie czy po rozdaniu ubywa odpowiednio kart ze stołu.
		@Test
		public void testcardsonTableafterdeal() throws sizeofSetException, RunOutOfPlayersException, gameHasStartedException, NoofPlayersException {
			tab.addPlayer(p0);
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.addPlayer(p0);
			tab.newDeck();
			tab.deal(6);
			assertEquals(tab.countDeck(), (int) 22);
		}
		
		// Sprawdzenie, czy gracze nie mają żadnych kart przed rozdaniem.
		@Test
		public void testPlayersscheat() throws sizeofSetException, gameHasStartedException, NoofPlayersException {
			int sum=0;
			tab.addPlayer(p0);
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.addPlayer(p0);
			for(int i=0;i<5;i++){
			sum+=tab.getSystemPlayer(i).countCards();
			}
			assertEquals(sum, (int) 0);
		}
		
		// Sprawdzenie, czy suma rozdanych kart graczy jest właściwa.
		@Test
		public void testcardsofAllPlayerss() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException {
			int sum=0;
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.addPlayer(p0);
			tab.newDeck();
			tab.deal(5);
			for(int i=0;i<4;i++){
				sum+=tab.getSystemPlayer(i).countCards();
				}
				assertEquals(sum, (int) 20);
		}
		
		// Sprawdzenie, czy karty nie giną.
		@Test
		public void teststack() throws sizeofSetException, RunOutOfPlayersException, gameHasStartedException, NoofPlayersException {
			int sum=0;
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.addPlayer(p0);
			tab.newDeck();
			tab.deal(5);
			tab.addCardOnTable(tab.takeCardFromDeck());
			tab.addCardOnTable(tab.takeCardFromDeck());
			tab.addCardOnTable(tab.takeCardFromDeck());
			for(int i=0;i<4;i++){
				sum+=tab.getSystemPlayer(i).countCards();
				}
			sum+=tab.countDeck();
			sum+=tab.getCardsOnTable().sizeof();
			assertEquals(sum, (int) 52);
		}
		
		// Sprawdzenie czy poszczególni gracze faktycznie otrzymują karty.
		@Test
		public void testcardsofPlayerss() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException {
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.newDeck();
			tab.deal(5);
			assertEquals(tab.getSystemPlayer(0).countCards(), (Integer) 5);
			assertEquals(tab.getSystemPlayer(1).countCards(), (Integer) 5);
			assertEquals(tab.getSystemPlayer(2).countCards(), (Integer) 5);
		}
		

		// Sprawdzenie czy karty są tasowane.
		@Test
		public void testcardShullfe() throws sizeofSetException {
			tab.newDeck();
			Card Before_0 = tab.getDeck().getCard(0);
			tab.shuffleDeck();
			Card After_0 = tab.getDeck().getCard(0);
			boolean b = ((Before_0.getRank() == After_0.getRank()) && (Before_0.getSuit() == After_0.getSuit()));
			assertFalse(b);
		}
		
		// Sprawdzenie czy karty są poprawnie generowane (bez powtórzeń)
		@Test
		public void testStackgeneration() throws sizeofSetException {
			tab.newDeck();
			List<Card> StackList = tab.getDeck().getCards();
			HashSet<Card> StackSet = new HashSet<Card>(StackList);
			assertTrue(StackList.size() == StackSet.size());
		}
		
		// Sprawdzenie czy po rozpoczeciu nowej gry talia ma odpowiedni rozmiar.
		@Test
		public void testsecondnewGame() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException {
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.newDeck();
			tab.deal(4);
			tab.setState(tab.NewTurnState);
			tab.Auto();
			assertEquals(tab.countDeck(), (int) 52);

		}
		
		//Sprawdzenie wykładania kart na stół
		@Test
		public void testputCardOnTable() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException {
			tab.newDeck();
			tab.addCardOnTable(tab.takeCardFromDeck());
			tab.addCardOnTable(tab.takeCardFromDeck());
			tab.addCardOnTable(tab.takeCardFromDeck());
				assertEquals(tab.countDeck(), (int) 49);
				assertEquals(tab.getCardsOnTable().sizeof(), (Integer) 3);
		}
		
		// Sprawdzenie czy karty nie są gubione po tasowaniu i rozdaniu.
		@Test
		public void teststackOK() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException {
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.addPlayer(p1);
			tab.addPlayer(p2);
			tab.addPlayer(p3);
			tab.newDeck();
			
			List<Card> TableBefore = tab.getDeck().getCards();
			tab.deal(5);
			tab.addCardOnTable(tab.takeCardFromDeck());
			tab.addCardOnTable(tab.takeCardFromDeck());
			tab.addCardOnTable(tab.takeCardFromDeck());
			List<Card> TableAfter = tab.getDeck().getCards();
			List<Card> PlayersAfter = new LinkedList<Card>();
			List<Card> StackAfter = TableAfter;
			for (Integer i = 0; i < tab.countPlayers(); i++) {
				PlayersAfter.addAll(tab.getSystemPlayer(i).getCards().getCards());
			}
			StackAfter.addAll(PlayersAfter);
			StackAfter.addAll(tab.getCardsOnTable().getCards());

			HashSet<Card> StackAfterSet = new HashSet<Card>(StackAfter);
			HashSet<Card> StackBeforeSet = new HashSet<Card>(TableBefore);
			// sprawdzenie zupełności talii
			boolean b = StackAfterSet.equals(StackBeforeSet);
			assertTrue(b);

		}
		
		// Sprawdzenie szukania następnego aktywnego gracza
				@Test
				public void nextActivePlayerInd() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					tab.addPlayer(p1);
					tab.addPlayer(p1);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.all_in);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.folded);
					tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.quit);
					assertEquals(tab.nextActivePlayer(4), (Integer) 2);

				}
				
				// Sprawdzenie szukania następnego niesfolodwanego gracza
				@Test
				public void nextNonFoldedPlayerInd() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					tab.addPlayer(p1);
					tab.addPlayer(p1);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.folded);
					tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.quit);
					assertEquals(tab.nextNonFoldedPlayer(4), (Integer) 2);

				}
				
				// Sprawdzenie szukania następnego niesquitowanego gracza
				@Test
				public void nextNonQuitPlayerInd() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					tab.addPlayer(p1);
					tab.addPlayer(p1);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.quit);
					assertEquals(tab.nextNonQuitPlayer(4), (Integer) 2);

				}
				
				// Sprawdzenie zliczania aktywnych gracza
				@Test
				public void nextActivePlayerCount() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					tab.addPlayer(p1);
					tab.addPlayer(p1);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.all_in);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.folded);
					tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.quit);
					assertEquals(tab.countActivePlayers(), (Integer) 2);

				}
				
				// Sprawdzenie zliczania niesfolodwanhych graczy
				@Test
				public void nextNonFoldedPlayerCount() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					tab.addPlayer(p1);
					tab.addPlayer(p1);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.folded);
					tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.quit);
					assertEquals(tab.countNonFoldedPlayers(), (Integer) 2);

				}
				
				// Sprawdzenie zliczania niesquitowanych graczy
				@Test
				public void nextNonQuitPlayerCount() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					tab.addPlayer(p1);
					tab.addPlayer(p1);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.quit);
					assertEquals(tab.countNonQuitPlayers(), (Integer) 2);

				}
				
				// Sprawdzenie inicjalizacji kredytów
				@Test
				public void initializeCredits() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					tab.initializeCredits();
					assertEquals(tab.getSystemPlayer(0).getPlayerCredits(), (Integer) 200);
					assertEquals(tab.getSystemPlayer(1).getPlayerCredits(), (Integer) 200);
					assertEquals(tab.getSystemPlayer(2).getPlayerCredits(), (Integer) 200);
				}
				
				//spr wyrzucania wyjątku RunOutOfPlayers dla nextnonquitplayer
				@Test(expected = RunOutOfPlayersException.class)
				public void runofnonquit() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					tab.addPlayer(p1);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.quit);
					tab.nextNonQuitPlayer(2);
				}
				
				//spr wyrzucania wyjątku RunOutOfActivePlayers dla nextactiveplayer
				@Test(expected = RunOutOfActivePlayersException.class)
				public void runofanctive() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					tab.addPlayer(p1);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.all_in);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.folded);
					tab.nextActivePlayer(2);
				}
				
				//spr wyrzucania wyjątku RunOutOfPlayers dla nextnonfoldedplayer
				@Test(expected = RunOutOfPlayersException.class)
				public void runofnonfolded() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					tab.addPlayer(p1);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.folded);
					tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.folded);
					tab.nextNonFoldedPlayer(2);
				}
				
				//test dla changeactiveplayersttausexcept
				//spr wyrzucania wyjątku RunOutOfPlayers dla nextnonfoldedplayer
				@Test
				public void activeplayersstatusexcept() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					tab.addPlayer(p1);
					tab.addPlayer(p1);
					tab.addPlayer(p1);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.all_in);
					tab.getSystemPlayer(2).setPlayerStatus(PlayerStatus.folded);
					tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.max_bet_bb);
					tab.getSystemPlayer(4).setPlayerStatus(PlayerStatus.max_bet_nbb);
					tab.getSystemPlayer(5).setPlayerStatus(PlayerStatus.under_max_bet);
					tab.ChangeActivePlayersStatusExcept(PlayerStatus.under_max_bet, 3);
					assertEquals(tab.getSystemPlayer(0).getPlayerStatus(),PlayerStatus.quit);
					assertEquals(tab.getSystemPlayer(1).getPlayerStatus(),PlayerStatus.all_in);
					assertEquals(tab.getSystemPlayer(2).getPlayerStatus(),PlayerStatus.folded);
					assertEquals(tab.getSystemPlayer(3).getPlayerStatus(),PlayerStatus.max_bet_bb);
					assertEquals(tab.getSystemPlayer(4).getPlayerStatus(),PlayerStatus.under_max_bet);
					assertEquals(tab.getSystemPlayer(5).getPlayerStatus(),PlayerStatus.under_max_bet);
				}
				
				//maxBetTest
				@Test
				public void maxBetCheck() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p3);
					
					tab.getSystemPlayer(2).setPlayerStatus(PlayerStatus.max_bet_bb);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.under_max_bet);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.under_max_bet);
					tab.getSystemPlayer(1).setPlayerBet(99);
					tab.getSystemPlayer(2).setPlayerBet(101);
					tab.getSystemPlayer(0).setPlayerBet(99);
					assertEquals(tab.getMaxBet(),101);
					
				}
				//fill
				@Test
				public void fillTest() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.max_bet_bb);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.under_max_bet);
					tab.getSystemPlayer(0).setPlayerCredits(100);
					tab.getSystemPlayer(1).setPlayerCredits(200);
					tab.getSystemPlayer(0).setPlayerBet(10);
					tab.getSystemPlayer(1).setPlayerBet(20);				
					tab.fill();
					assertEquals(tab.getgameInfo(0).ThisPlayerStatus,PlayerStatus.max_bet_bb);
					assertEquals(tab.getgameInfo(1).ThisPlayerStatus,PlayerStatus.under_max_bet);
					assertEquals(tab.getgameInfo(0).ThisPlayerCredits,100);
					assertEquals(tab.getgameInfo(1).ThisPlayerCredits,200);
					assertEquals(tab.getgameInfo(0).ThisPlayerBets,10);
					assertEquals(tab.getgameInfo(1).ThisPlayerBets,20);	
				}
				
				@Test
				public void initializePlayers() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p2);
					tab.addPlayer(p2);
					tab.addPlayer(p2);
					tab.addPlayer(p2);
					tab.addPlayer(p2);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.max_bet_bb);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(2).setPlayerStatus(PlayerStatus.all_in);
					tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.max_bet_nbb);
					tab.getSystemPlayer(4).setPlayerStatus(PlayerStatus.under_max_bet);
					tab.getSystemPlayer(5).setPlayerStatus(PlayerStatus.folded);
					tab.getSystemPlayer(6).setPlayerStatus(PlayerStatus.quit);
					tab.initializePlayers();
					assertEquals(tab.getgameInfo(0).ThisPlayerStatus,PlayerStatus.init);
					assertEquals(tab.getgameInfo(1).ThisPlayerStatus,PlayerStatus.quit);
					assertEquals(tab.getgameInfo(2).ThisPlayerStatus,PlayerStatus.init);
					assertEquals(tab.getgameInfo(3).ThisPlayerStatus,PlayerStatus.init);
					assertEquals(tab.getgameInfo(4).ThisPlayerStatus,PlayerStatus.init);
					assertEquals(tab.getgameInfo(5).ThisPlayerStatus,PlayerStatus.init);
					assertEquals(tab.getgameInfo(6).ThisPlayerStatus,PlayerStatus.quit);
				}
				@Test
				public void BetsAreEqualPlayers() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {
					tab.addPlayer(p1);
					tab.addPlayer(p2);
					tab.addPlayer(p2);
					tab.addPlayer(p2);
					tab.getSystemPlayer(0).setPlayerStatus(PlayerStatus.max_bet_bb);
					tab.getSystemPlayer(1).setPlayerStatus(PlayerStatus.quit);
					tab.getSystemPlayer(2).setPlayerStatus(PlayerStatus.under_max_bet);
					tab.getSystemPlayer(3).setPlayerStatus(PlayerStatus.max_bet_nbb);
					
					assertEquals(tab.BetsAreEqual(),false);
					tab.getSystemPlayer(2).setPlayerStatus(PlayerStatus.all_in);
					assertEquals(tab.BetsAreEqual(),true);
				}
				
				@Test
				public void getTabofPlayersCards() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {

					tab.addPlayer(p1);
					tab.addPlayer(p1);
					tab.newDeck();
					tab.deal(2);
					Cards cl=tab.getSystemPlayer(0).getCards();
					Card [] car=tab.getTabofPlayerCards(0);	
					assertEquals((int)cl.sizeof(),(int)car.length);
					for(int i=0;i<cl.sizeof();i++){
					assertEquals(cl.getCard(i),car[i]);
					}
				}
				
				@Test
				public void getTabofTableCards() throws sizeofSetException, gameHasStartedException, NoofPlayersException, RunOutOfPlayersException, RunOutOfActivePlayersException {

					tab.addPlayer(p1);
					tab.addPlayer(p1);
					tab.newDeck();
					tab.deal(10);
					tab.addCardOnTable(tab.takeCardFromDeck());
					tab.addCardOnTable(tab.takeCardFromDeck());
					tab.addCardOnTable(tab.takeCardFromDeck());
					tab.addCardOnTable(tab.takeCardFromDeck());
					tab.addCardOnTable(tab.takeCardFromDeck());
					Cards cl=tab.getCardsOnTable();
					Card [] car=tab.getTabofTableCards();
					assertEquals((int)cl.sizeof(),(int)car.length);
					for(int i=0;i<cl.sizeof();i++){
					assertEquals(cl.getCard(i),car[i]);
					}
				}
				
				

				
				

}

















