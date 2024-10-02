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


	
	

