package getyeflask.game.entities;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class Player extends MoveableEntity implements InputProcessor {
		
    //GRAV gravity = 60 * 1.8f; /*pixels/sec*/'
	//GRAVprivate boolean canJump = true;
	private static float speed = 60 * 2;
	private static int health;
	private static int attackDamage;
	
	
	public Player(Sprite sprite, TextureAtlas movementAtlas, TiledMap map){
		super(sprite, movementAtlas, map, speed);
	}
	
	public static void modifyHealth(int amount){
		health += amount;
	}
	
	public static int getHealth(){
		return health;
	}
	
	public static void setSpeed(int speed){
		Player.speed = speed;
	}
	
	public static float getSpeed(){
		return speed;
	}
	
	public static void setDamage(int attackDamage){
		Player.attackDamage = attackDamage;
	}
	
	public static int getDamage(){
		return attackDamage;
	}
	
		
	//INPUT HANDLERS

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.W:
			move('n');
			animationTime = 0;
			break;
		case Keys.A:
			move('w');
			animationTime = 0;
			break;
		case Keys.D:
			move('e');
			animationTime = 0;
			break;
		case Keys.S:
			move('s');
			animationTime = 0;
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.W:
		case Keys.S:
			animationTime = 0;
			stop('y');
			break;
		case Keys.A:
		case Keys.D:
			animationTime = 0;
			stop('x');
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return true;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
