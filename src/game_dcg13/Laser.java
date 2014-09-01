package game_dcg13;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/*
 * a big part of the game.  there is a laser object that serves as mirror mans initial laser
 * there is also another laser object for each reflection off of a mirror
 */


public class Laser extends Line {
	private Pane rootPane;
	
	//constructor
	public Laser(int startX,int startY,int endX,int endY,boolean isDashed, Pane root){
		//send the starting and ending coordinates to the parent class 'Line'
		super(startX,startY,endX,endY);
        this.setStrokeWidth(5);
        this.setStroke(Color.BLACK);
        if (isDashed){
            this.getStrokeDashArray().addAll(2d);
            this.setStrokeWidth(1);
        }
        rootPane = root;
        root.getChildren().add(this);
	}
	public void remove(){
		rootPane.getChildren().remove(this);
	}
}
