package game_dcg13;


import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Mirror extends Line{
	public Mirror(int startX, int startY, int endX, int endY, Level level,Color color){
		//coords consists of a startX,startY,endX, and endY
		//send the coordinates to create a super 'Line'
		super(startX,startY,endX,endY);
        this.setStrokeWidth(5);
        this.setStroke(color);
	    level.getChildrenFromLevel().add(this);
	}
}
