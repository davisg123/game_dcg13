package game_dcg13;


import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Level extends Parent{
	private static Laser laserLine;				///the current line that represents the laser beam
	private static boolean levelShouldHandleMouseEvents = true;		///indicates whether we should generating a laser on mouse event
	private Main myGameController;
	private int myLevelNum;
	private ArrayList<Mirror> myMirrors = new ArrayList<Mirror>();
	
	
	public Level(int levelNum, Main gameController){
		myLevelNum = levelNum;
		myGameController = gameController;
		setupBoundary();
		setupLevel(levelNum);
	}
	
	public void setupBoundary(){
		//these should be in a property list to avoid repeated code
		//setup game boundary
        generateLine(30,30,270,30,false);
        generateLine(30,30,30,270,false);        
        generateLine(270,30,270,270,false);
        generateLine(125,270,175,270,true);
        generateLine(30,270,125,270,false);
        generateLine(175,270,270,270,false);
	}
	
	public void setupLevel(int levelNum){
		//these should be in a property list to avoid repeated code
		myMirrors.clear();
		switch(levelNum){
		case 0:
	        myMirrors.add(new Mirror(50,100,75,75,this,Color.GREEN));
	        new Target(200,90,200,70,this,Color.GREEN);
	        break;
		case 1:
			myMirrors.add(new Mirror(40,200,65,225,this,Color.BLUE));
	        new Target(200,90,200,70,this,Color.BLUE);
	        break;
		case 2:
			myMirrors.add(new Mirror(150,60,175,35,this,Color.LIGHTGREEN));
			myMirrors.add(new Mirror(150,100,175,125,this,Color.BLUE));
			new Target(70,255,90,255,this,Color.AQUA);
			break;
		case 3:
			myGameController.gameComplete();
			break;
		}
	}
	
    private Line generateLine(int startX, int startY, int endX, int endY, Boolean isDashed){
        Line aLine = new Line(startX,startY,endX,endY);
        aLine.setStrokeWidth(5);
        aLine.setStroke(Color.BLACK);
        if (isDashed){
            aLine.getStrokeDashArray().addAll(2d);
            aLine.setStrokeWidth(1);
        }
        getChildren().add(aLine);
        return aLine;
    }
    
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
    
    public int getLevelNum(){
    	return myLevelNum;
    }
    
    public void solve(){
    	//these should be in a property list to avoid repeated code
		switch(myLevelNum){
		case 0:
			myMirrors.get(0).setStartX(87);
			myMirrors.get(0).setStartY(68);
			myMirrors.get(0).setEndX(111);
			myMirrors.get(0).setEndY(43);
	        break;
		case 1:
			myMirrors.get(0).setStartX(233);
			myMirrors.get(0).setStartY(38);
			myMirrors.get(0).setEndX(258);
			myMirrors.get(0).setEndY(63);
	        break;
		case 2:
			myMirrors.get(0).setStartX(67);
			myMirrors.get(0).setStartY(183);
			myMirrors.get(0).setEndX(92);
			myMirrors.get(0).setEndY(158);
			//2nd mirror
			myMirrors.get(1).setStartX(140);
			myMirrors.get(1).setStartY(156);
			myMirrors.get(1).setEndX(165);
			myMirrors.get(1).setEndY(181);
			break;
		}
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
        		//coords consists of a startX,startY,endX, and endY
        		int[] coords = LineOps.extendLine(150,265,(int)event.getX(),(int)event.getY());
                laserLine = new Laser(coords,0,true,this,null,null);
        	}
    	}
    }
}
