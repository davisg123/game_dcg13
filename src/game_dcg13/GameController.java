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
	private static Laser laserLine;				///the current line that represents the laser beam
	private static Level currentLevel;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mirror Man");

        Pane root = new Pane();

        currentLevel = new Level(root);
        handleDragOnPane(root);
        
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }
    
    private static void handleDragOnPane(Pane root){
    	root.onMouseDraggedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if (laserLine != null){
            		//remove the previous laser
            		laserLine.remove();
            	}
            	if (laserLine != null && laserLine.intersects(laserLine.getLayoutBounds())){
            		
            	}
            	//generate a laser from mirror man's static location to the click location
                laserLine = new Laser(150,270,(int)event.getX(),(int)event.getY(),true,root);
            }
        });
    }
    
    

}