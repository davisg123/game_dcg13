package game_dcg13;

import java.awt.Point;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineOps {
	protected static int[] extendLine(int startX,int startY,int endX,int endY){
		//extend this partial line to extend across the entire game area
		//get the slope of the line
		float m = (float)(startY-endY)/(startX-endX);
		float b = startY - m*startX;
		//we want y to be at the top of the screen
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
	
	public static Point[] createPointsFromLine(Line l){
		int startPointX = l.startXProperty().getValue().intValue();
		int startPointY = l.startYProperty().getValue().intValue();
		int endPointX = l.endXProperty().getValue().intValue();
		int endPointY = l.endYProperty().getValue().intValue();
		Point[] points = {new Point(startPointX,startPointY), new Point(endPointX,endPointY)};
		return points;
	}
	
	public static boolean isLeft(Line theLine, Point c){
		Point a = new Point();
		a.x = theLine.startXProperty().getValue().intValue();
		a.y = theLine.startYProperty().getValue().intValue();
		Point b = new Point();
		b.x = theLine.endXProperty().getValue().intValue();
		b.y = theLine.endYProperty().getValue().intValue();
	    return ((b.x - a.x)*(c.y - a.y) - (b.y - a.y)*(c.x - a.x)) > 0;
	}
	

	/**
	 * Calculate the cross product of the two points.
	 */
	public static float crossProduct(Point pt1, Point pt2) {
		return pt1.x * pt2.y - pt1.y * pt2.x;
	}

	/**
	 * Subtract the second point from the first.
	 */ 
	public static Point subtractPoints(Point pt1, Point pt2) {
		Point result = new Point();
		result.x = pt1.x - pt2.x;
		result.y = pt1.y - pt2.y;

		return result;
	}
	
	public static Point addPoints(Point pt1, Point pt2) {
		Point result = new Point();
		result.x = pt1.x + pt2.x;
		result.y = pt1.y + pt2.y;
		return result;
	}
	
	public static double slopeOfLine(Line aLine){
		//get the slope (rise / run)
		double rise = aLine.endYProperty().getValue() - aLine.startYProperty().getValue();
		double run = aLine.endXProperty().getValue() - aLine.startXProperty().getValue();
		return rise/run;
	}
	
	
    protected static Line generateLine(int startX, int startY, int endX, int endY){
    	return internalLineGeneration(startX,startY,endX,endY,false);
    }
    
    protected static Line generateDashedLine(int startX, int startY, int endX, int endY){
    	return internalLineGeneration(startX,startY,endX,endY,true);
    }
    
    private static Line internalLineGeneration(int startX, int startY, int endX, int endY, Boolean isDashed){
        Line aLine = new Line(startX,startY,endX,endY);
        aLine.setStrokeWidth(5);
        aLine.setStroke(Color.BLACK);
        if (isDashed){
            aLine.getStrokeDashArray().addAll(2d);
            aLine.setStrokeWidth(1);
        }
        return aLine;
    }
	
}