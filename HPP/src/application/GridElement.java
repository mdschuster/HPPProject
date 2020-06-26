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

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * GridElement class
 * Contains all the information about each grid
 * location, including color, location and occupation
 * @author schuster
 *
 */
public class GridElement {
	Rectangle r;
	int size =1;
	int x;
	int y;
	int occupation;
	
	/**
	 * GridElement constructor
	 * 
	 * @param x x position of the grid element
	 * @param y y position of the grid element
	 */
	public GridElement(int x, int y) {
		this.x=x;
		this.y=y;
		
		this.r = new Rectangle(x,y);
		this.r.setWidth(size);
		this.r.setHeight(size);
		this.r.setStrokeWidth(0.0);
		setOccupation(0);
		this.r.setStroke(Color.BLACK);
		
	}
	
	/**
	 * getGraphic
	 * returns the graphic for the grid as a Node
	 * @return Grid element graphic
	 */
	public Node getGraphic() {
		return r;
	}
	
	/**
	 * setOccupation
	 * Sets the occupation of the GridElement and changes
	 * the graphic color based on the current occupation
	 * @param value Grid occupation to change to
	 */
	public void setOccupation(int value) {
		this.occupation=value;
		switch(this.occupation) {
		case(0):
			this.r.setFill(Color.YELLOW);
			break;
		case(1):
			this.r.setFill(Color.GREENYELLOW);
			break;
		case(2):
			this.r.setFill(Color.LIGHTGREEN);
			break;
		case(3):
			this.r.setFill(Color.LIGHTSEAGREEN);
			break;
		case(4):
			this.r.setFill(Color.BLUE);
			break;
		default:
			this.r.setFill(Color.PINK);
			break;
		}
	}
}
