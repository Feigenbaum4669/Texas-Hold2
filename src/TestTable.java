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

}

















