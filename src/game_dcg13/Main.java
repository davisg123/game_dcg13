package game_dcg13;
 
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

 
public class Main extends Application {
	private static Level currentLevel;
	private static Stage myStage;
	private static Pane rootPane;
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage s) {
    	myStage = s;
        myStage.setTitle("Mirror Man");

        rootPane = new Pane();

        Scene myScene = new Scene(rootPane,300,300);
        Splash mySplash = new Splash(false);
        rootPane.getChildren().add(mySplash);
        myStage.setScene(myScene);
        myStage.show();
        // Check for game start press
        myScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER){
                	//get rid of splash
                	rootPane.getChildren().remove(mySplash);
                	//load first level
                	loadLevel(0);
                }
                else if (ke.getCode() == KeyCode.S){
                	//solve the level
                	if (currentLevel != null){
                		currentLevel.solve();
                	}
                }
            }
        });
    }
    
    public void levelComplete(){
    	rootPane.getChildren().remove(currentLevel);
    	loadLevel(currentLevel.getLevelNum()+1);
    }
    
    public void gameComplete(){
    	rootPane.getChildren().remove(currentLevel);
    	Splash gameOverSplash = new Splash(true);
    	rootPane.getChildren().add(gameOverSplash);
    }
    
    public void loadLevel(int levelNum){
    	//prevent duplicate level loading
    	if (currentLevel == null || currentLevel.getLevelNum() != levelNum){
        	String title = String.format("Mirror Man Level %d", levelNum+1);
        	myStage.setTitle(title);
        	currentLevel = new Level(levelNum,this);
        	rootPane.getChildren().add(currentLevel);
        	handleDragOnSelf();
    	}
    }
    
    
    private void handleDragOnSelf(){
    	rootPane.onMouseDraggedProperty().set(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	currentLevel.handleMouseEvent(event);
            }
        });
    	rootPane.onMouseReleasedProperty().set(new EventHandler<MouseEvent>() {
    		@Override
    		public void handle(MouseEvent event) {
    			currentLevel.handleMouseEvent(event);
    		}
    	});
    }
    

}