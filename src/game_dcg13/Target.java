package game_dcg13;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Target extends Line{
	public Target(int startX, int startY, int endX, int endY, Level level, Color color){
		super(startX,startY,endX,endY);
		setStroke(color);
		setStrokeWidth(15);
		level.getChildrenFromLevel().add(this);
	}
}
