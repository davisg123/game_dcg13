package game_dcg13;

import javafx.scene.paint.Color;

public class LevelOne extends Level {

	public LevelOne(Main gameController) {
		super(gameController);
	}

	@Override
	public void setupLevel() {
        myMirrors.add(new Mirror(50,100,75,75,this,Color.GREEN));
        new Target(200,90,200,70,this,Color.GREEN);
	}

	@Override
	public void solve() {
		myMirrors.get(0).setStartX(87);
		myMirrors.get(0).setStartY(68);
		myMirrors.get(0).setEndX(111);
		myMirrors.get(0).setEndY(43);
	}

}
