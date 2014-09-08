package game_dcg13;

import javafx.scene.paint.Color;

public class LevelThree extends Level {

	public LevelThree(Main gameController) {
		super(gameController);
	}

	@Override
	public void setupLevel() {
		myMirrors.add(new Mirror(150,60,175,35,this,Color.LIGHTGREEN));
		myMirrors.add(new Mirror(150,100,175,125,this,Color.BLUE));
		new Target(70,255,90,255,this,Color.AQUA);
	}

	@Override
	public void solve() {
		myMirrors.get(0).setStartX(67);
		myMirrors.get(0).setStartY(183);
		myMirrors.get(0).setEndX(92);
		myMirrors.get(0).setEndY(158);
		//2nd mirror
		myMirrors.get(1).setStartX(140);
		myMirrors.get(1).setStartY(156);
		myMirrors.get(1).setEndX(165);
		myMirrors.get(1).setEndY(181);
	}

}
