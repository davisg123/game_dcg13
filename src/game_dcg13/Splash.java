package game_dcg13;

import javafx.scene.Parent;
import javafx.scene.text.Text;


public class Splash extends Parent {
	public Splash(boolean isEndOfGame){
		if (isEndOfGame){
			Text endText = new Text(85, 25, "You Win! (Hard Huh?)");
			getChildren().add(endText);
		}
		else{
			Text welcomeText = new Text(75, 25, "Welcome to Mirror Man");
			Text promptText = new Text (70, 200, "Press Enter to Start Game");
			getChildren().add(welcomeText);
			getChildren().add(promptText);
		}
	}
}
