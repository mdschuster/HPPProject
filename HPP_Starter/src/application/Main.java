/*
Copyright 2020 Micah Schuster

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package application;
	
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

/**
 * Main class to run the HPP simulation
 * This is a JavaFX application
 * @author schuster
 *
 */
public class Main extends Application {
	
	//Size of the square window
	public static final int SIZE=384;
	//random number generator
	public static final Random rand=new Random();
	//timeline for running the simulation
	Timeline tl;
	//Handler for running the simulation over time
	EventHandler<ActionEvent> handler;
	//lookup table for collisions
	byte[] lookup = new byte[16];
	//Occupation lookup table
	int[] bits = {0,1,1,2,1,2,2,3,1,2,2,3,2,3,3,4};
	//Underlying data grid
	byte[][] data = new byte[SIZE][SIZE];
	//Temp data grid for computing collisions
	byte[][] tempdata = new byte[SIZE][SIZE];
	//playing state variable
	static boolean playing=false;
	
	/**
	 * Start function for JavaFX
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane root = new GridPane();
			
			//Setup the grid and data here
			
			handler=new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					//More will go here
				}
				
			};
			
			tl=new Timeline(new KeyFrame(Duration.millis(100),handler));
			
			tl.setCycleCount(Timeline.INDEFINITE);
			root.setOnKeyPressed(e->{
				if(playing==true) {
					tl.pause();
					playing=false;
				} else {
					tl.play();
					playing=true;
				}
			});

			Scene scene = new Scene(root,SIZE,SIZE);
			primaryStage.setScene(scene);
			primaryStage.show();
			root.requestFocus();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main method that starts the JavaFX program
	 * @param args Command line args, not used
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * getBits
	 * Converts a cells byte value to the number of particles in that cell
	 * Uses a lookup table
	 * @param value Cell byte value
	 * @return Number of particles in that cell
	 */
	public int getBits(byte value) {
		return bits[value];
		

	}
	
	/**
	 * Collision
	 * Performs the intra-cell collision using a lookup table
	 */
	public void collision() {
		//collision code goes here
	}
	
	/**
	 * Propagation
	 * Loops though each cell and examines the von Neumann neighborhood.
	 * Based on the surrounding cells, computes each cells new state into a temp array.
	 * After completing all calculations, the temp array is copied into the data array.
	 * @param grid The GridElement matrix for updating the graphics for each grid cell
	 */
	public void propagation(GridElement[][] grid) {
		//all the propagation code goes here
	}
	
	/**
	 * setupCollision
	 * Sets up the lookup table for the collisions
	 */
	public void setupCollision() {
		//setup collision lookup array    
		//---- abcd
		//0000 0000
		//---- 8421

		//The creation of the collision lookup will go here
	}
}
