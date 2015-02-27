package getyeflask.game.entities;

import getyeflask.game.screens.Play;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Enemy extends MoveableEntity {
	
	private float speed;
	private int health;
	private int aggroRange;
	private int attackRange;
	private int attackDamage;

	public Enemy(Sprite sprite, TextureAtlas movementAtlas, TiledMap map) {
		super(sprite, movementAtlas, map, 0);
		seek();
		}
	

	
	private void seek(){
		
		while(true){
			if(health <= 0) break;
			if(Player.getHealth() <= 0) break;
			
			float deltaX = this.getX() - Play.getPlayer().getX();
			float deltaY = this.getY() - Play.getPlayer().getY();
			//If within aggro range
			if(Math.abs(deltaX) < aggroRange && Math.abs(deltaY) < aggroRange) {
				if(deltaX < aggroRange && deltaX > 0) { //player is <n left
					tryDirection('w');
					if(deltaX < attackRange){
						stop('x');
						attack();
					}
				}
				if( -deltaX < aggroRange && deltaX < 0){ //player is <n right
					tryDirection('e');
					if(-deltaX < attackRange){
						stop('x');
						attack();
					}
				}
				if( deltaY < aggroRange && deltaY > 0) { //player is <n down 
					tryDirection('s');
					if(deltaY < attackRange){
						stop('y');
						attack();
					}
				}
				if( -deltaY < aggroRange && deltaY < 0){ //player is <n up
					tryDirection('n');
					if(-deltaY < attackRange){
						stop('y');
						attack();
					}
				}
			}
			//If outside aggro range, schedule new timer with delay 2 to move in a random direction
			else{
				float delay = 2;
				Timer.schedule(new Task() {
					@Override
					public void run() {

						boolean clear = false;
						while(!clear) {
							switch((int)(Math.random() * 4)){
							case 0:
								if(tryDirection('n')) clear = true;
								break;
							case 1:
								if(tryDirection('s')) clear = true;
								break;
							case 2:
								if(tryDirection('e')) clear = true;
								break;
							case 3:
								if(tryDirection('w')) clear = true;
								break;
							default:
								assert false;
							}
						}
					}
				}, delay);
			} //else ends
			
		}//while loop ends
	}
	
	private void attack(){
		long t1 = System.currentTimeMillis(); //t1 in ms
		float attackVelocity = -15; // pixels / sec, clockwise rotation
		float v = attackVelocity / attackRange; // v / r
		float r = attackRange;
		
		//Set initial position based on direction
		Vector2 attackPos = new Vector2();
		Vector2 initialPos = new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2 + r);
		
		
		while(deltaTime(t1) < 500){
			//Parametric eqn for a circle: r*cos(vt / r) , r*sin(vt / r) 
			//Switching cos to y and sin to x. It should work on the same principle as an x-axis transformation but simpler to deal with.
			//EFFECTIVE RANGE OF ATTACK: max value of attackPos.x or attackPos.y. Try to make it hit radius.
			attackPos.x = (float)(r * Math.sin(v * (deltaTime(t1)) / 1000) + initialPos.x); 
			attackPos.y = (float)(r * Math.cos(v * (deltaTime(t1)) / 1000) + initialPos.y);
			
			
			float x1 = Play.getPlayer().getX() , x2 = Play.getPlayer().getX() + Play.getPlayer().getWidth();
			float y1 =  Play.getPlayer().getY(), y2 = Play.getPlayer().getY() + Play.getPlayer().getHeight();
			
			if(attackPos.x >  x1 && attackPos.x < x2 && attackPos.y > y1 && attackPos.y < y2){
				Player.modifyHealth(-attackDamage);
			}
		}
		
		
	}
	
	private boolean tryDirection(char direction){
			switch(direction){
			case 'n':
				if(!this.collidesTop()){
					move('n');
					return true;
				}
				break;
			case 's':
				if(!this.collidesBottom()){
					move('s');
					return true;
				} 
				break;
			case 'e':
				if(!this.collidesRight()){
					move('e');
					return true;
				} 
				break;
			case 'w':
				if(!this.collidesLeft()){
					move('w');
					return true;
				}
				break;
			default:
				return false;
			}
			return false;
	}

	

	public void setStats(int speed, int health, int aggroRange, int attackRange, int attackDamage){
		this.speed = speed;
		this.health = health;
		this.aggroRange = aggroRange;
		this.attackRange = attackRange;
		this.attackDamage = attackDamage;
	}
	
	

}
