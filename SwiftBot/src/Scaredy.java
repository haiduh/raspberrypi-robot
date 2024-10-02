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