import java.util.Scanner; 			//these are all the imports needed for the code
import swiftbot.SwiftBotAPI;

public class Main {
	static SwiftBotAPI swiftBot;
	public static String swiftbotmode; //this is used later to display the correct mode in the logs.
	public static long executedTime;
	public static int objectdetected;
	public static long start = System.nanoTime();
	public static int botSpeed;
	public static SwiftBotAPI API = new SwiftBotAPI();
	public static double bufferzone = 15;
	public static double distance;

	public static void botSpeed() {		//So that the user can input the bot speed required
		while (true) {
				try {
					System.out.println("\n"
				
									+"                   Please choose the correct speed according to your surface.\n"
									+"               (Select '20' for wood and select anything between '40 - 100' for carpet)\n");	
		
				Scanner input = new Scanner(System.in); 	
				System.out.println("Please enter speed here:");
				botSpeed = input.nextInt(); 
				if (botSpeed > 0 && botSpeed < 100) {
					
					System.out.println("\n"
							  + "                        The speed you have input is: \n" 
							  + "                                    " 
							  + botSpeed);
				break;
				}
				else {
					System.out.println("\nYou input a speed outside the range, please try anything between '0-100'\n");
				}
			}
			catch (Exception e) {
		        System.out.println("\nInvalid input. Please enter an integer.\n"); //If the user somehow enters a string.    
		    }
		}
	}
	
	public static void main(String[] args) {
		
		int selectedmode; 
		
		//User interface prompt.
		System.out.println("\n"
				+ "                 Welcome to the Detect Object program!\n"
				+ "====================================================================\n"
				+ "\n"
				+ "                   Please select your mode of choice:\n"
				+ "                       1 - Curious Swiftbot\n"
				+ "                       2 - Scaredy Swiftbot\n"
				+ "                       3 - Any Swiftbot\n" 
				+ "\n"
				+ "====================================================================");
		
		//Mode Selection
		while (true) {
			try { //this is to make sure that a integer is entered rather than a string 

				Scanner select = new Scanner(System.in); 	//scanner inputs is necessary to interact with the user
				System.out.println("Please enter input here:");
				selectedmode = select.nextInt(); 
				
				//this a new feature, allow the user to adjust the speed according to the surface that the robot is on.
				
				if (selectedmode == 3) { 	//Any mode generates a random number between 1 and 2 and then selects the corresponding mode.
					System.out.println("You have selected 'Any Mode'. A random mode will be selected.");
					int min = 1;
					int max = 2;
					int rand_int = (int)Math.floor(Math.random() * (max - min + 1) + min);
					selectedmode = rand_int;
					botSpeed();
				}
				
				if (selectedmode == 1) {
					botSpeed();
					System.out.println("\n"
					+"Curious Swiftbot mode has been selected."); //If curious mode has been selected.
					swiftbotmode = "Curious Swiftbot";
					
					Curious func = new Curious ();
				    func.CuriousMode();
				    break;
					
				}
				//if scaredy mode has been selected.
				else if (selectedmode == 2) {
					botSpeed();
					System.out.println("\n"
					+"Scaredy Swiftbot mode has been selected.");
					swiftbotmode = "Scaredy Swiftbot";
					
					Scaredy func = new Scaredy();
					func.ScaredyMode();
					break;
				}
				//if the user has input the correct option, this error message has been followed.
				else {
					System.out.println("You have not chosen the correct options. Please try again.");
				}
					
			}
			//if the user has accidentally entered a string
			catch (Exception e) {
				  System.out.println("Please use the numbers and retry. \n");
			}
			
		}
		
	}
		public static void LOE() {
			long end = System.nanoTime();
			executedTime =	-(start - end ) / 1000000000;
			Logofexecution.Execute();
		}
}