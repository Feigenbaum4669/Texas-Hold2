import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;



public class ServerSidePlayer extends Player {	
	public static String getInput(String prompt) {
	    BufferedReader in = new BufferedReader(
	            new InputStreamReader(System.in));

	    System.out.print(prompt);
	    System.out.flush();

	    try {
	        return in.readLine();
	    } catch (IOException e) {
	        return "Error: " + e.getMessage();
	    }

	}
	
	Console console=System.console();
	
	public VAction makeAction(GameInfo gi){
		setGameInfo(gi);
		
		VAction va= new VAction();
		System.out.println("Make decision "+this.name);
		System.out.println("INFO: status: "+this.status+" highestBet: "+hBet+" Bank: "+this.bank+" credits: "+this.Credits+" bet: "+this.bet);
		System.out.println("command:");
		int i=Integer.parseInt(getInput(""));
		System.out.println("value:");
		int j=Integer.parseInt(getInput(""));
		
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
		return va;
		}
	public void notify(String msg){
		log("Notka (" + this.name + "): "+msg);
	}
	
	@Override
	public void readAndSetName() {
		notify("Podaj swoje imię: ");
		setName(getInput("imię: "));
	}
	
	public ServerSidePlayer() {
		super();
	}
	
	public ServerSidePlayer(String name) {
		super();
		setName(name);
	}
	
}
