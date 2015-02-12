package getyeflask.game.screens;

import getyeflask.game.tween.SpriteAccessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Splash implements Screen {
	
	//batch is the background
	private SpriteBatch batch;
	private Sprite splash;
	//TweenManager updates the animation to the current values
	private TweenManager tweenManager;
	@Override
	public void show() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		//Registers accessor for the sprite class
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		//This is actually using something that would normally be found using
		//Gdx.files.internal("splash.png"). Internal begins in the assets folder
		Texture splashTexture = new Texture("assets/ui/splash2.png");
		splash = new Sprite(splashTexture);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//This part does the animations
		//Starts from alpha transparency 0 (invisible)
		Tween.set(splash, SpriteAccessor.ALPHA)
			.target(0)
			.start(tweenManager); //(starting value, value being animated).startFromThisTransparency(0).startWith(tweenManager)
		Tween.to(splash, SpriteAccessor.ALPHA, 2)
			.target(1)
			.setCallback(new TweenCallback(){
				@Override
				public void onEvent(int type, BaseTween<?> source) {
					//casts the application listener as a game
					((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
					
				}})
			.start(tweenManager);
	}
	@Override
	public void render(float delta) {
		tweenManager.update(delta);
		//gl is common for all the versions
		//Sets color
		Gdx.gl.glClearColor(0, 0, 0, 1);
		//Does color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		//splash draws itself on batch
		splash.draw(batch);
		
		batch.end();		
	}
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		batch.dispose();
		splash.getTexture().dispose();		
	}

}
