
# SwiftBot - The Smart Roaming Robot 🤖

SwiftBot is a smart roaming robot that detects objects and interacts with its surroundings based on different behavioral modes. This robot project uses Raspberry Pi for controlling movements and object detection via sensors, making it capable of curious exploration or scaredy avoidance. I designed this for a university project. The SwiftBot was provided to me by the University along with the API for the program for communication between Eclipse and Raspberry Pi.

## Features ✨
- **Curious Mode**: The robot approaches objects, maintaining a safe distance. Moves closer if too far or further if too close.
- **Scaredy Mode**: The robot avoids objects, moving away once they come too close.
- **Rotational Movement**: If no object is detected, the robot rotates almost at 120° and continues its search.
- **Visual Feedback**: LED lights change color based on object proximity (green for found, red for danger, blinking for avoidance).
- **Logs**: Tracks the number of objects detected and the robot’s reactions to them.
- **User Interaction**: Control the robot’s behavior by choosing between Curious, Scaredy, or Any mode. Press 'X' to stop.

## How it Works 🚀
1. When powered on, the robot prompts you to select between:
   - **Curious**: Moves towards objects but adjusts distance if too close.
   - **Scaredy**: Avoids objects and moves away when proximity is too close.
   - **Any**: A mixed mode that combines both behaviors.
   
2. Based on the mode:
   - **Curious Mode**: If an object is detected, the robot moves closer or further away to maintain a specific distance. If no object is found after a set time, it rotates 120° and resumes exploration. After interacting with an object, the robot pauses for 2 seconds to log the object before                           moving on.
   - **Scaredy Mode**: The robot wanders with blue lights. If it detects an object within a specific range, its lights turn red, and it moves away.
   
3. The robot logs every object it detects and displays it at the end of the program.

## Lights and Indicators 🌈
- **Green**: Object detected (Curious mode).
- **Red**: Danger (Scaredy mode, too close to an object).
- **Blinking**: Moving away (Curious mode).

## Eclipse Demo 🎥


![eclipse demo](https://github.com/user-attachments/assets/8798750d-0672-4895-9363-80b65022b1ec)


## Code Breakdown 🧑‍💻
This project is split into four key classes, each handling a different aspect of the SwiftBot’s behavior:

### 1. `MainClass` - Core functionality & decision logic
This class powers up the robot and manages the user’s choice between modes.

```java
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
```

### 2. `CuriousClass` - Handles Curious Mode behavior
This class manages object detection in curious mode, controlling movement to maintain the desired distance from objects.

```java
import java.io.IOException;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class Curious {
	int x = 1;
	
	
	public void CuriousMode() {
		System.out.println("\n"
						+"=====================================================================\n" 
						+ "\n"
						+ "                     Commencing mode of choice.....\n"
						+ "\n"
						+"=====================================================================\n");
		
		// X Button press swiftbot.
		Main.API.BUTTON_X.addListener(new GpioPinListenerDigital()
	{
	public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
		if (event.getState().isLow()) { 		        //this indicates when the 'x' button on the swiftbot is pressed.
			x = 0; 								        //makes the while loop stop so that the log of execution class is executed.
			Main.API.BUTTON_X.removeAllListeners(); 	//to stop the user from accidentally pressing 'x' again.
			Main.API.stopMove(); 					   	//stops the wheels on the robot from turning.
			Main.API.disableUnderlights();				//turns off all the underlights
			}
		}
	});
	
		//This is the curious swiftbot flowchart.
		while(x == 1) {
            Main.distance = Main.API.useUltrasound(); 							//scans for nearby objects
           
            if (Main.distance > Main.bufferzone) {				  			
            	try {
            		Main.API.fillUnderlights(0 , 90 , 0);		  				//the underlights are green here.		
    			} 
            	catch (IOException e1) {
    				e1.printStackTrace();
    			}																//moves until Main.bufferzone has been reached
            		System.out.println("\nToo far, moving closer....\n");
            		Main.API.startMove(Main.botSpeed, Main.botSpeed);			//changed it to user input
            		Main.distance = Main.API.useUltrasound();
            }
            if (Main.distance> 1200.0 && Main.distance< 1300.0) { 				//The return value received by the bot is 1214 when no object is detected.
        		long startTime = System.currentTimeMillis();
        		long timeTaken = 0;
            		try {
    					Main.API.fillUnderlights(0 , 0 , 90); 					//underlights = blue
    				} 
            		catch (IOException e1) {
    					e1.printStackTrace();
    				}
            		while (timeTaken < 5000) { 									// 5000 milliseconds = 5 seconds
            		    timeTaken = System.currentTimeMillis() - startTime; 	//to add a pause for 5 seconds.
            		    if (x == 0) {break;}
            		}
            		if (x == 0) {Main.LOE(); return;}  					   //these are shown multiple times in the code because the code keep running even when the 'X' is pressed.
							            								   //force executes the log of execution class.
            		
            		System.out.println("The time that has passed is " + timeTaken / 1000 + " seconds\n");
    		    	if (Main.distance> 1200.0 & Main.distance< 1300.0) {
    		            try {
    						System.out.println("Object not found, commencing rotation......");
    						Main.API.move(-Main.botSpeed, -Main.botSpeed, 1500);
    						Main.API.move( Main.botSpeed , -Main.botSpeed , 2000);
    					} 
    					catch (IllegalArgumentException | InterruptedException e) {
    						e.printStackTrace();
    					}
    		            if (x == 0) {Main.LOE(); return;}
    				}
                }
            	else if (Main.distance < Main.bufferzone) {
	            		try {
	                		Main.API.fillUnderlights(0 , 90 , 0);
	        			} 
	                	catch (IOException e1) {
	        				e1.printStackTrace();								//moves away until the Main.bufferzone has been reached				
	        			}
	            	
	            		System.out.println("\nToo close , moving away....\n");
	            		Main.API.startMove(-Main.botSpeed, -Main.botSpeed);
	            		Main.distance = Main.API.useUltrasound();
	            }
	           
	            else if (Main.distance== Main.bufferzone) {
		    			Main.API.stopMove();
		    			System.out.println("Object found, waiting for 2 seconds....\n");  
		    			try {
							Main.API.fillUnderlights(0 , 90 , 0);
						} 
		        		catch (IOException e1) {									//commences the algorithm for when the swiftbot is within the bufferzone			
							e1.printStackTrace();	
						}
		    			
		    			Main.API.disableUnderlights();
		    			
		    			try {														//this blinks the underlights of the swiftbot
		    				
						Main.API.fillUnderlights(0 , 90 , 0);
						
						} 
		        		catch (IOException e1) {
							e1.printStackTrace();
						}
						Main.objectdetected += 1;			//calculates the number of times an object has been detected so that it can be displayed in the log of execution.
						
						long startTime = System.currentTimeMillis();
		        		long timeTaken = 0;

	        		while (timeTaken < 2000) { 												// 2000 milliseconds = 2 seconds
	        		    timeTaken = System.currentTimeMillis() - startTime;					// adds a pause for 2 seconds.
	        		  
	        		    if (x == 0) {break;}
	        		    
	        		}
        		
        		      if (x == 0) {Main.LOE(); return;}
        		
					Main.distance = Main.API.useUltrasound();
					if (Main.distance == Main.bufferzone) { 		//this means the object has not been moved for 2 seconds.
						try {
							System.out.println("Moving");
							Main.API.move(45 , -45 , 1500);
						} 
						catch (IllegalArgumentException | InterruptedException e) {
							e.printStackTrace();
						
							}
						} 
					}
            if (x == 0) {
    			Main.LOE();				
    			return;
    			}
    		}
		}
	}
}
```

### 3. `ScaredyClass` - Handles Scaredy Mode behavior
In this class, the robot reacts to detected objects by moving away when they get too close.

```java
import java.io.IOException;

import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;


public class Scaredy {
		int x = 1;
		static volatile boolean y = true;

	public static void stop() {
				Main.LOE();
				return;
			}
		
			public void ScaredyMode() throws InterruptedException {
				System.out.println("\n"
						+ "====================================================================\n" 
						+ "\n"
						+ "                     Commencing mode of choice.....\n"
						+ "\n"
						+ "====================================================================\n");
		
				// X Button press swiftbot.
				Main.API.BUTTON_X.addListener(new GpioPinListenerDigital()
			{
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				if (event.getState().isLow()) { 		//this indicates when the 'x' button on the swiftbot is pressed.
					x = 0; 	
					y = false;										//makes the while loop stop so that the log of execution class is executed.
					Main.API.BUTTON_X.removeAllListeners(); 	//to stop the user from accidentally pressing 'x' again.
					Main.API.stopMove(); 					//stops the wheels on the robot from turning.
					Main.API.disableUnderlights();			//kills the underlights for the bot.
					}
				}
			});
			while (x == 1) {
				Main.distance = Main.API.useUltrasound(); 			//scans for nearby objects
		        System.out.println("The object that is closest to the bot is + " + Main.distance + "cm away.");
				if (Main.distance > 100) {
					try {
						Main.API.fillUnderlights(0 , 0 , 90); //Sets the underlights to blue when it is wandering.
					} 
		    		catch (IOException e1) {
						e1.printStackTrace();
					}
					Main.API.startMove(Main.botSpeed, Main.botSpeed);     //moves the robot at the speeds set by the user.
					Main.distance = Main.API.useUltrasound();
				}
				 if (Main.distance < 100) {
					 
					 Main.API.stopMove();
					 System.out.println("Object detected, moving away...\n");
					 
					 try {
							Main.API.fillUnderlights(90 , 0 , 0);
						} 
		     		catch (IOException e1) {
							e1.printStackTrace();
						}
					 try {
		 					Main.API.disableUnderlights();
		 					Thread.sleep(500);
					 }
					 catch (InterruptedException e) {
		 		                e.printStackTrace();
		 		            }		
		 			
		 			try {
		 				
		 					Main.API.fillUnderlights(90 , 0 , 0);
						
						} 
		     		catch (IOException e1) {
							e1.printStackTrace();
						}
			 	
		 			Main.objectdetected += 1;
		 			Main.API.move(-Main.botSpeed, -Main.botSpeed, 1000);
		 			Main.distance = Main.API.useUltrasound();
		 			Main.API.stopMove();
		 			System.out.println("Turning....\n");
		 			Main.API.move(Main.botSpeed, -Main.botSpeed, 2000);
		 			Main.distance = Main.API.useUltrasound();
		 			Main.API.stopMove();
		 			Main.API.move(Main.botSpeed, Main.botSpeed, 3000);
		 			Main.distance = Main.API.useUltrasound();
		 			
				 if (x == 0) {stop();}
				 
				 if (Main.distance> 1200.0 && Main.distance< 1300.0) { //The return value received by the bot is 1214 when no object is detected.
			 		long launchTime = System.currentTimeMillis();
			 		long timeConsumed = 0;
				 		try {
								Main.API.fillUnderlights(0 , 0 , 90); //underlights = blue
							} 
				 		catch (IOException e1) {
								e1.printStackTrace();
							}
				 		while (timeConsumed < 5000) { // 5000 milliseconds = 5 seconds
				 		    timeConsumed = System.currentTimeMillis() - launchTime;
				 		}
				 		System.out.println("The time that has passed is " + timeConsumed / 1000 + " seconds");
					    	if (Main.distance> 1200.0 & Main.distance< 1300.0) {
					            try {
									System.out.println("Moving");
									Main.API.move(-Main.botSpeed, -Main.botSpeed, 1500);
		    						Main.API.move( Main.botSpeed , -Main.botSpeed , 2000);
								} 
								catch (IllegalArgumentException | InterruptedException e) {
									e.printStackTrace();
								}
							}
						    	if (x == 0) {stop();}	
				 			}
				 			if (x == 0) {stop();}
					}		
           }
   	}
  }
```

### 4. `LogExecutionClass` - Logs object interactions
Tracks the number of objects detected, mode-specific interactions,the time of execution for this use and display the outputs of this information to the user.

```java
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
```

## Future Enhancements 🚧
- Integration with AI for smarter object detection.
- Voice commands to switch between modes.
- Enhanced logging with timestamps and environmental data.

 

## 📞 Contact Me
If you have any questions or would like to discuss this project further, feel free to reach out:

- **Email:** rayaanhaider04@hotmail.com

I’d love to hear your feedback or collaborate on future projects!
