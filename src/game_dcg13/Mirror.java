package game_dcg13;


import java.awt.Point;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Mirror extends Line{
	//create deltas representing the distance the the start and end point (each end) of the mirror moves
	final Point startDragDelta = new Point();
	final Point endDragDelta = new Point();
	
	public Mirror(int startX, int startY, int endX, int endY, Level level,Color color){
		//coordinates consists of a startX,startY,endX, and endY
		//send the coordinates to create a super 'Line'
		super(startX,startY,endX,endY);
        this.setStrokeWidth(5);
        this.setStroke(color);
	    level.getChildrenFromLevel().add(this);
	    
	    //reference to ourself for event handler
	    Mirror myMirror = this;
	    
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
		  @Override 
		  public void handle(MouseEvent mouseEvent) {
			//disable laser generation
			level.setLevelShouldHandleMouseEvents(false);
		    // record a delta distance for the drag and drop operation on this mirror.
		    startDragDelta.x = (int) (myMirror.getStartX() - mouseEvent.getSceneX());
		    startDragDelta.y = (int) (myMirror.getStartY() - mouseEvent.getSceneY());
		    endDragDelta.x = (int) (myMirror.getEndX() - mouseEvent.getSceneX());
		    endDragDelta.y = (int) (myMirror.getEndY() - mouseEvent.getSceneY());
		  }
		});
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
		  @Override 
		  public void handle(MouseEvent mouseEvent) {
			//update the mirror position based on the drag deltas
		    myMirror.setStartX(mouseEvent.getSceneX() + startDragDelta.x);
		    myMirror.setStartY(mouseEvent.getSceneY() + startDragDelta.y);
		    myMirror.setEndX(mouseEvent.getSceneX() + endDragDelta.x);
		    myMirror.setEndY(mouseEvent.getSceneY() + endDragDelta.y);
		  }
		});
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
		  @Override 
		  public void handle(MouseEvent mouseEvent) {
			level.setLevelShouldHandleMouseEvents(true);
		  }
		});
	    
	}
}