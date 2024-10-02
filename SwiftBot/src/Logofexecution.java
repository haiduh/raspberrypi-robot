import java.util.Scanner;


public class Logofexecution {
		
		public static void Execute() {
	
	
		while (true) {
			System.out.println("'X' has been pressed.\n"
							   + "Program has been stopped. Would you like to view logs of execution? 1 for 'Yes', 2 for 'No'\n");
			
			try {
				Scanner UserInput = new Scanner(System.in); 
				System.out.println("Please enter input here:"); //takes the input from the user.
				int UserInputted = UserInput.nextInt(); 
				
				if (UserInputted == 1) {		
					
					System.out.println("\n"
							+"The program ran for: " + Main.executedTime + " seconds\n"
							+ "You have chosen: " + Main.swiftbotmode + "\n"
							+ "An object has been detected " + Main.objectdetected + " number of times\n");
					
					System.out.println("Logs displayed, shutting down......\n");
					break;
					
				}
				else if (UserInputted == 2 ) {
					System.out.println("\n" 
									  +"You have chosen 'No', shutting down the program....");
					break;
				}
				else {
					System.out.println("\nSelect only 1 or 2.\n");		//this is if the user has not selected the right options
				}
				
			}
			catch (Exception e){
				System.out.println("\nPlease try again. Select only 1 or 2.\n");  	//this is if the user has inputted a string.
			}
			
		}
		Main.API.stopMove();
		Main.API.disableUnderlights();
		Main.API.shutdown();
		System.exit(0);	
	}
	

}

