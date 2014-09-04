package game_dcg13;

import javafx.scene.Parent;
import javafx.scene.text.Text;


public class Splash extends Parent {
	public Splash(){
	      Text welcomeText = new Text(75, 25, "Welcome to Mirror Man");
	      Text instructionText = new Text (82, 200, "Press X to Start Game");
	      getChildren().add(welcomeText);
	      getChildren().add(instructionText);
	}
}
