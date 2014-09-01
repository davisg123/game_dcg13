package game_dcg13;
 
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

 
public class GameController extends Application {
	private static Level currentLevel;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mirror Man");

        Pane root = new Pane();

        currentLevel = new Level(root);
        
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }
    

}