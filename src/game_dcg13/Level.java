package game_dcg13;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Level {
	private static Laser laserLine;				///the current line that represents the laser beam
	private static Pane rootPane;
	
	public Level(Pane root){
		rootPane = root;
		//setup game boundary
        generateLine(30,30,270,30,false,root);
        generateLine(30,30,30,270,false,root);        
        generateLine(270,30,270,270,false,root);
        generateLine(125,270,175,270,true,root);
        generateLine(30,270,125,270,false,root);
        generateLine(175,270,270,270,false,root);
        new Mirror(50,75,75,75,root,Color.BLACK);
        //detect drag event
        handleDragOnPane(rootPane);
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
    
    private static void handleDragOnPane(Pane root){
    	root.onMouseDraggedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	handleMouseEvent(event);
            }
        });
    	root.onMouseReleasedProperty().set(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			handleMouseEvent(event);
    		}
    	});
    }
    
    private static void handleMouseEvent(MouseEvent event){
    	if (laserLine != null){
    		//remove the previous laser
    		laserLine.remove();
    	}
    	if (event.getEventType() == MouseEvent.MOUSE_DRAGGED){
	    	if (laserLine != null && laserLine.intersects(laserLine.getLayoutBounds())){
	    	}
	        //generate a laser from mirror man's static location to the click location
        	//only generate if the click is on the game area (above mirror man)
        	if ((int)event.getY() < 260){
        		//coords consists of a startX,startY,endX, and endY
        		int[] coords = createExtendedLine(150,265,(int)event.getX(),(int)event.getY());
                laserLine = new Laser(coords,0,true,rootPane);
        	}
    	}
    }
    
	private static int[] createExtendedLine(int startX,int startY,int endX,int endY){
		//extend this partial line to extend across the entire game area
		//get the slope of the line
		float m = (float)(startY-endY)/(startX-endX);
		float b = startY - m*startX;
		//we want y to be 0 (top of screen)
		int extendedY = 0;
		int extendedX;
		if (m == Float.POSITIVE_INFINITY){
			//slope is infinity, so x stays the same
			extendedX = startX;
		}
		else{
			extendedX = (int)((extendedY-b)/m);
		}
		int[] coords = {startX,startY,extendedX,extendedY};
		//return the new coordinates for the extended line
		return coords;
	}
}
