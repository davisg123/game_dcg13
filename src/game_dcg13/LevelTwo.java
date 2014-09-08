// This entire file is part of my masterpiece.
// Davis Gossage


package game_dcg13;

import javafx.scene.paint.Color;

public class LevelTwo extends Level {

	public LevelTwo(Main gameController) {
		super(gameController);
	}

	@Override
	public void setupLevel() {
		myMirrors.add(new Mirror(40,200,65,225,this,Color.BLUE));
        new Target(200,90,200,70,this,Color.BLUE);
	}

	@Override
	public void solve() {
		myMirrors.get(0).setStartX(233);
		myMirrors.get(0).setStartY(38);
		myMirrors.get(0).setEndX(258);
		myMirrors.get(0).setEndY(63);
	}

}
