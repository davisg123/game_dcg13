package game_dcg13;

import java.awt.Point;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/*
 * a big part of the game.  there is a laser object that serves as mirror mans initial laser
 * there is also another laser object for each reflection off of a mirror
 */


public class Laser extends Line {
	private Level level;
	private int myReflectionNumber;		//the reflection number of this laser	
	private Laser childLaser;
	private Mirror myCollidingMirror;
	//constructor
	public Laser(int[] coords,int reflectionNumber,boolean isDashed, Level l, Mirror collisionMirror){
		//coords consists of a startX,startY,endX, and endY
		//send the coordinates to create a super 'Line'
		super(coords[0],coords[1],coords[2],coords[3]);
        this.setStrokeWidth(5);
        this.setStroke(Color.BLACK);
        if (isDashed){
            this.getStrokeDashArray().addAll(2d);
            this.setStrokeWidth(1);
        }
        myCollidingMirror = collisionMirror;
        myReflectionNumber = reflectionNumber;
        if (myReflectionNumber < 5){
        	//to avoid an infinite amount of bounces off mirrors, we limit number of reflections to 5
	        level = l;
	        level.addNode(this);
	        checkForCollisionsAndTrimLine();
        }
	}
	
	public boolean isValidReflection(){
		//check if this reflection is eligible, if it isn't the object won't be shown on screen
		return myReflectionNumber < 5;
	}

	public void checkForCollisionsAndTrimLine(){
		Point closestIntersectingPoint = new Point(Integer.MAX_VALUE,Integer.MAX_VALUE);
		Node closestIntersectedNode = null;
	    for(Node n : level.getChildrenFromLevel()){
	        if (n != this && n.getBoundsInParent().intersects(this.getBoundsInParent()) && n != myCollidingMirror){
	        	//if we can determine the point of intersection, see if it's the closest point
	        	Point intersectPoint = determinePointOfIntersection(n);
	        	if (intersectPoint != null){
	        		if (distanceFromSelf(intersectPoint) < distanceFromSelf(closestIntersectingPoint) && !(n instanceof Laser)){
	        			//we have a closer intersecting point
	        			closestIntersectingPoint = intersectPoint;
	        			closestIntersectedNode = n;
	        		}
	        	}
	        }
	    }
	    //update this laser to be cutoff at the closest obstruction
	    this.setEndX(closestIntersectingPoint.x);
	    this.setEndY(closestIntersectingPoint.y);
	    //if the intersected node is a mirror, we need to create a reflection
    	if (closestIntersectedNode instanceof Mirror){
    		//cast node as a mirror
    		createReflection(closestIntersectingPoint, (Mirror)closestIntersectedNode);
    	}
	}
	
	public double distanceFromSelf(Point a){
		Point myStartPoint = new Point(this.startXProperty().getValue().intValue(), this.startYProperty().getValue().intValue());
		double distance = Math.sqrt(Math.pow(a.x - myStartPoint.x,2) + Math.pow(a.y - myStartPoint.y,2));
		return distance;
	}
	
	public Point determinePointOfIntersection(Node n){
		/*
		 * logic for this comes from http://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
		 */
		if (n instanceof Line){
			boolean linesDoIntersect;
			//4 points from 2 lines
			//call these points p and p2 for the 1st line
			//and q and q2 for the 2nd line
			Point[] pPoints = createPointsFromLine((Line)n);
			Point p = pPoints[0];
			Point p2 = pPoints[1];
			Point[] qPoints = createPointsFromLine(this);
			Point q = qPoints[0];
			Point q2 = qPoints[1];
			
			Point r = subtractPoints(p2, p);
			Point s = subtractPoints(q2, q);

			float uNumerator = crossProduct(subtractPoints(q, p), r);
			float denominator = crossProduct(r, s);

			float u = uNumerator / denominator;
			float t = crossProduct(subtractPoints(q, p), s) / denominator;

			linesDoIntersect = (t >= 0) && (t <= 1) && (u >= 0) && (u <= 1) && denominator != 0;
			if (linesDoIntersect){
				//calculate the point of intersection by figuring out how long along one of the lines...
				//we have to go to find the intersection
				Point tr = new Point((int)(t*r.x),(int)(t*r.y));
				return addPoints(p,tr);
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	public Point[] createPointsFromLine(Line l){
		int startPointX = l.startXProperty().getValue().intValue();
		int startPointY = l.startYProperty().getValue().intValue();
		int endPointX = l.endXProperty().getValue().intValue();
		int endPointY = l.endYProperty().getValue().intValue();
		Point[] points = {new Point(startPointX,startPointY), new Point(endPointX,endPointY)};
		return points;
	}

	/**
	 * Calculate the cross product of the two points.
	 */
	public float crossProduct(Point pt1, Point pt2) {
		return pt1.x * pt2.y - pt1.y * pt2.x;
	}

	/**
	 * Subtract the second point from the first.
	 */ 
	public Point subtractPoints(Point pt1, Point pt2) {
		Point result = new Point();
		result.x = pt1.x - pt2.x;
		result.y = pt1.y - pt2.y;

		return result;
	}
	
	public Point addPoints(Point pt1, Point pt2) {
		Point result = new Point();
		result.x = pt1.x + pt2.x;
		result.y = pt1.y + pt2.y;
		return result;
	}
	
	public double slopeOfLine(Line aLine){
		//get the slope (rise / run)
		double rise = aLine.endYProperty().getValue() - aLine.startYProperty().getValue();
		double run = aLine.endXProperty().getValue() - aLine.startXProperty().getValue();
		return rise/run;
	}
	
	public void createReflection(Point collisionPoint, Mirror collisionMirror){
		//reflected slope is given by c = (a*b^2-a+2*b)/(-b^2+1+2*b*a)
		double mySlope = slopeOfLine(this);
		double mirrorSlope = slopeOfLine(collisionMirror);
		double reflectingSlope = (mySlope*Math.pow(mirrorSlope,2)-mySlope+2*mirrorSlope)/(-Math.pow(mirrorSlope,2)+1+2*mySlope*mirrorSlope);
		//now we make a new laser, using the calculated slope
		//with a start point at the collision point
		//we make up another point at an arbitrary distance along our line
		//this point is either (x+300, y+m*300) or (x-300, y-m*300) depending on...
		//which result puts the reflected line on the same side of the mirror as our original line
		//make sense?  good...
		//begin smelly code
		Point myStart = new Point();
		myStart.x = this.startXProperty().getValue().intValue();
		myStart.y = this.startYProperty().getValue().intValue();
		Point end = new Point();
		end.x = collisionPoint.x+300;
		end.y = collisionPoint.y+(int)(reflectingSlope*300);
		if(LineOps.isLeft(collisionMirror,end) != LineOps.isLeft(collisionMirror,myStart)){
			end.x = collisionPoint.x-300;
			end.y = collisionPoint.y-(int)(reflectingSlope*300);
		};

		//end smelly code
		

		int[] coords = {collisionPoint.x,collisionPoint.y,end.x,end.y};
		childLaser = new Laser(coords,myReflectionNumber+1,true,level,collisionMirror);
	}
	
	public void remove(){
	    level.getChildrenFromLevel().remove(this);
	    
	    //make sure the child laser exists and is valid before removing it
		if (childLaser != null && childLaser.isValidReflection()){
			childLaser.remove();
		}
	}
	

}