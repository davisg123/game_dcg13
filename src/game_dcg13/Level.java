package game_dcg13;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Level {
	private Pane rootPane;
	
	public Level(Pane root){
		rootPane = root;
		//setup game boundary
        generateLine(30,30,270,30,false,root);
        generateLine(30,30,30,270,false,root);        
        generateLine(270,30,270,270,false,root);
        generateLine(125,270,175,270,true,root);
        generateLine(30,270,125,270,false,root);
        generateLine(175,270,270,270,false,root);
	}
	
    private static Line generateLine(int startX, int startY, int endX, int endY, Boolean isDashed, Pane root){
        Line aLine = new Line(startX,startY,endX,endY);
        aLine.setStrokeWidth(5);
        aLine.setStroke(Color.BLACK);
        if (isDashed){
            aLine.getStrokeDashArray().addAll(2d);
            aLine.setStrokeWidth(1);
        }
        root.getChildren().add(aLine);
        return aLine;
    }
}
