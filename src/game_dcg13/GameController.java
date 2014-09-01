package game_dcg13;
 
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

 
public class GameController extends Application {
	private static Line laserLine;				///the current line that represents the laser beam
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mirror Man");


        Pane root = new Pane();
        buildView(root);
        handleDragOnPane(root);
        
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }
    
    private static void handleDragOnPane(Pane root){
    	root.onMouseDraggedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if (laserLine != null){
            		root.getChildren().remove(laserLine);
            	}
            	if (laserLine.intersects(laserLine.getLayoutBounds())){
            		
            	}
                laserLine = generateLine(150,270,(int)event.getX(),(int)event.getY(),true,root);
            }
        });
    }
    
    private static void buildView(Pane root){
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