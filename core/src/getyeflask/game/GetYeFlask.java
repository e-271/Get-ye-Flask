package getyeflask.game;

import getyeflask.game.screens.Splash;

import com.badlogic.gdx.Game;

public class GetYeFlask extends Game {
	
	public static final String NAME = "Get ye Flask", VERSION = "0.0.0.1";
	
	@Override
	public void create () {
		setScreen(new Splash());
		
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
