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
	private Pane rootPane;
	
	//constructor
	public Laser(int[] coords,boolean isDashed, Pane root){
		//coords consists of a startX,startY,endX, and endY
		//send the coordinates to create a super 'Line'
		super(coords[0],coords[1],coords[2],coords[3]);
        this.setStrokeWidth(5);
        this.setStroke(Color.BLACK);
        if (isDashed){
            this.getStrokeDashArray().addAll(2d);
            this.setStrokeWidth(1);
        }
        rootPane = root;
        root.getChildren().add(this);
        checkCollisions();
	}
	

	public void checkCollisions(){
		Point closestIntersectingPoint = new Point(Integer.MAX_VALUE,Integer.MAX_VALUE);
	    for(Node n : rootPane.getChildren()){
	        if (n != this && n.getBoundsInParent().intersects(this.getBoundsInParent())){
	        	//if we can determine the point of intersection, see if it's the closest point
	        	Point intersectPoint = determinePointOfIntersection(n);
	        	if (intersectPoint != null){
	        		closestIntersectingPoint = closestPointToSelf(closestIntersectingPoint,intersectPoint);
	        	}
	        }
	    }
	    this.setEndX(closestIntersectingPoint.x);
	    this.setEndY(closestIntersectingPoint.y);
	}
	
	public Point closestPointToSelf(Point a, Point b){
		Point myStartPoint = new Point(this.startXProperty().getValue().intValue(), this.startYProperty().getValue().intValue());
		double distanceofA = Math.sqrt((a.x - myStartPoint.x)^2 + (a.y - myStartPoint.y)^2);
		double distanceofB = Math.sqrt((b.x - myStartPoint.x)^2 + (b.y - myStartPoint.y)^2);
		if (distanceofA < distanceofB){
			return a;
		}
		else{
			return b;
		}
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
	
	public void remove(){
		rootPane.getChildren().remove(this);
	}
	

}