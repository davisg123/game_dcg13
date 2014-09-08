package game_dcg13;


import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

public abstract class Level extends Parent{
	private static Laser laserLine;				///the current line that represents the laser beam
	private static boolean levelShouldHandleMouseEvents = true;		///indicates whether we should generating a laser on mouse event
	private Main myGameController;									///reference to the game controller for sending level complete message
	protected ArrayList<Mirror> myMirrors = new ArrayList<Mirror>();	///list of all of the mirrors that a level contains
	
	
	public Level(Main gameController){
		myGameController = gameController;
		setupBoundary();
		setupLevel();
	}
	
	public void setupBoundary(){
		//these should be in a property list to avoid repeated code
		//setup game boundary, generate line takes starting and ending coordinates
        addNode(LineOps.generateLine(30,30,270,30,false));
        addNode(LineOps.generateLine(30,30,30,270,false));        
        addNode(LineOps.generateLine(270,30,270,270,false));
        addNode(LineOps.generateLine(125,270,175,270,true));
        addNode(LineOps.generateLine(30,270,125,270,false));
        addNode(LineOps.generateLine(175,270,270,270,false));
	}
	
	public abstract void setupLevel();
    public abstract void solve();
    
    public void setLevelShouldHandleMouseEvents(boolean shouldHandle){
    	levelShouldHandleMouseEvents = shouldHandle;
    }
    
    public void addNode(Node n){
    	getChildren().add(n);
    }
    
    public void checkWinConditions(Target target, Laser laser){
    	if (target.getStroke().equals(laser.getStroke())){
    		myGameController.levelComplete();
    	}
    }
    
    public ObservableList<Node> getChildrenFromLevel(){
    	return getChildren();
    }
    
    public void handleMouseEvent(MouseEvent event){
    	if (laserLine != null){
    		//remove the previous laser
    		laserLine.remove();
    	}
    	if (event.getEventType() == MouseEvent.MOUSE_DRAGGED && levelShouldHandleMouseEvents){
	        //generate a laser from mirror man's static location to the click location
        	//only generate if the click is on the game area (above mirror man)
        	if ((int)event.getY() < 260){
        		//coordinates consists of a startX,startY,endX, and endY
        		int[] coords = LineOps.extendLine(150,265,(int)event.getX(),(int)event.getY());
                laserLine = new Laser(coords,0,true,this,null,null);
        	}
    	}
    }
}
