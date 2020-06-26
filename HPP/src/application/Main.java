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
			
			GridElement[][] grid = new GridElement[SIZE][SIZE];
			for(int i=0;i<SIZE;i++) {
				for(int j=0;j<SIZE;j++) {
					grid[i][j]=new GridElement(i,j);  //create each of the GridElement objects
					root.add(grid[i][j].getGraphic(), i, j);  //add graphic to gridpane
				}
			}
			
			//create the actual data array

			for(int i=0;i<SIZE;i++) {
				for(int j=0;j<SIZE;j++) {
					byte value = (byte)rand.nextInt(13);
					if(i==0 || j==0 || i==SIZE-1 ||j ==SIZE-1) {
						value=0;
					}
					//setup random occupation

					data[i][j]=value;
					grid[i][j].setOccupation(getBits(value));
				}
			}
			
			setupCollision();

			for(int i=50;i<150;i++) {
				for(int j=50;j<150;j++) {
					data[i][j]=15;
					grid[i][j].setOccupation(getBits(data[i][j]));
				}
			}

			handler=new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					for(int i=0;i<1;i++) {
						collision();
						propagation(grid);
					}
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
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				if(i==0 || j==0 || i==SIZE-1 ||j ==SIZE-1) {
					data[i][j]=data[i][j];
				} else {
					data[i][j] = lookup[(int)data[i][j]];
				}
				
			}
		}
	}
	
	/**
	 * Propagation
	 * Loops though each cell and examines the von Neumann neighborhood.
	 * Based on the surrounding cells, computes each cells new state into a temp array.
	 * After completing all calculations, the temp array is copied into the data array.
	 * @param grid The GridElement matrix for updating the graphics for each grid cell
	 */
	public void propagation(GridElement[][] grid) {
		//0000 abcd
		
		byte upMask = 0x01;
		byte downMask = 0x08;
		byte leftMask = 0x02;
		byte rightMask = 0x04;
		byte upByte=0, downByte=0, leftByte=0, rightByte=0;
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {

				if(i!=SIZE-1)
					upByte = (byte) ((data[i+1][j]&upMask)<<3);
				if(i!=0)
					downByte = (byte) ((data[i-1][j]&downMask)>>3);
				if(j!=0)
					leftByte = (byte) ((data[i][j-1]&leftMask)<<1);
				if(j!=SIZE-1)
					rightByte = (byte) ((data[i][j+1]&rightMask)>>1);
				
				byte value = (byte) (upByte | downByte | leftByte | rightByte);
				tempdata[i][j]=value;
				
				grid[i][j].setOccupation(getBits(tempdata[i][j]));
			}
		}
		for(int i=0;i<SIZE;i++) {
			for(int j=0;j<SIZE;j++) {
				data[i][j]=tempdata[i][j];
			}
		}
		
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

		lookup[0]=(byte)0;
		lookup[1]=(byte)8;  //0001 -> 1000
		lookup[2]=(byte)4;  //0010 -> 0100
		lookup[3]=(byte)12;  //0011 -> 1100
		lookup[4]=(byte)2;   //0100 -> 0010
		lookup[5]=(byte)10;   //0101 -> 1010
		lookup[6]=(byte)9;   //0110 -> 1001
		lookup[7]=(byte)14;   //0111 -> 1110
		lookup[8]=(byte)1;    //1000 -> 0001
		lookup[9]=(byte)6;    //1001 ->0110
		lookup[10]=(byte)5;   //1010 -> 0101
		lookup[11]=(byte)13;  //1011 -> 1101
		lookup[12]=(byte)3;
		lookup[13]=(byte)11;
		lookup[14]=(byte)7;
		lookup[15]=(byte)15; //1111 -> 1111
	}
}
