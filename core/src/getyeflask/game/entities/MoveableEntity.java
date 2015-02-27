package getyeflask.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MoveableEntity extends Sprite {
	
	private Vector2 velocity = new Vector2(0, 0);
	private float speed;
	private TiledMap map;
	private TiledMapTileLayer collisionLayer;
	protected float animationTime = 0;
	private Animation northStill, eastStill, westStill, southStill,
					  north, east, west, south;
	
	public MoveableEntity(Sprite sprite, TextureAtlas movementAtlas, TiledMap map, float speed){
		super(sprite);
		
		north = new Animation(1 / 8f, movementAtlas.findRegions("walk_north"));
		east = new Animation(1 / 8f, movementAtlas.findRegions("walk_east"));
		west = new Animation(1 / 8f, movementAtlas.findRegions("walk_west"));
		south = new Animation(1 / 8f, movementAtlas.findRegions("walk_south"));
		southStill = new Animation(1 / 2f, movementAtlas.findRegions("south_still"));
		
		north.setPlayMode(Animation.PlayMode.LOOP);
		east.setPlayMode(Animation.PlayMode.LOOP);
		west.setPlayMode(Animation.PlayMode.LOOP);
		south.setPlayMode(Animation.PlayMode.LOOP);
		southStill.setPlayMode(Animation.PlayMode.LOOP);
		
		this.map = map;
		this.speed = speed;
		setSize(getWidth() * 2.2f, getHeight() * (float)(2.2 * (8.0/10)));
	}
		
	
	@Override
	public void draw(Batch batch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(batch);
	}
	
	public void update(float delta){
		
		//applies gravity
		//GRAVvelocity.y -= gravity * delta;
		
		/*GRAVclamp velocity
		if(velocity.y > speed)
			velocity.y = speed;
		else if(velocity.y < -speed)
			velocity.y = -speed;*/
		
		float oldX = getX(); boolean xCollision = false;
		float oldY = getY(); boolean yCollision = false;
		boolean breakIt = false;
		
		for(int i = 0; i < map.getLayers().getCount(); i++) {
		
		collisionLayer = (TiledMapTileLayer) map.getLayers().get(i);
			
		//move on x
		setX(getX() + velocity.x * delta);
		if(velocity.x < 0){ //went left
			xCollision = collidesLeft();
			
		} else if(velocity.x > 0){ //went right
			xCollision = collidesRight();
		}
		//collision reaction on X
		if(xCollision){
			setX(oldX);
			setY(oldY);
			velocity.x = 0;
			breakIt = true;
			}
		
		//move on y
		setY(getY() + velocity.y * delta);
		if(velocity.y < 0){ //went down
			yCollision = collidesBottom();
			
		} else if(velocity.y > 0){ // went up
			yCollision = collidesTop();
		}
		
		//collision reaction on y
		if(yCollision){
			setX(oldX);
			setY(oldY);
			velocity.y = 0;
			breakIt = true;
			}
		if(breakIt) break;
		}

		
		animationTime += delta;
		setRegion(getAnimationState());
	}
	
	private TextureRegion getAnimationState() {
		if(velocity.y > 0) return north.getKeyFrame(animationTime);
		else if(velocity.y < 0) return south.getKeyFrame(animationTime);
		else if(velocity.x > 0) return east.getKeyFrame(animationTime);
		else if(velocity.x < 0) return west.getKeyFrame(animationTime);
		return southStill.getKeyFrame(animationTime);
	}
	
	public long deltaTime(long t1){
		return System.currentTimeMillis() - t1;
	}
	
	
	public boolean collidesRight() {
		
		for(float step = 0; step < getHeight()/ 4; step += collisionLayer.getTileHeight() / 2){
			if(isCellBlocked(getX() + getWidth(), getY()  + step))
				return true;
		}
		return false;
	}

	public boolean collidesLeft() {
		for(float step = 0; step < getHeight()/ 4; step += collisionLayer.getTileHeight() / 2){
			if(isCellBlocked(getX() , getY()  + step))
				return true;
		}
		return false;
	}
	
	public boolean collidesTop() {
		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2){
			if(isCellBlocked(getX() + step, getY()  + getHeight() / 4))
				return true;
		}
		return false;
	}
	public boolean collidesBottom() {
		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2){
			if(isCellBlocked(getX() + step, getY() ))
				return true;
		}
		return false;
	}
	
	private boolean isCellBlocked(float x, float y){
		Cell cell = collisionLayer.getCell((int)(x / collisionLayer.getTileWidth()), (int)(y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
	}
	
	public void move(char direction){
		switch(direction){
		case 'n':
			velocity.y = speed;
			break;
		case 'e':
			velocity.x = speed;
			break;
		case 's':
			velocity.y = -speed;
			break;
		case 'w':
			velocity.x = -speed;
			break;
		}
	}
	
	public void stop(char direction){
		switch(direction){
		case 'x':
			velocity.x = 0;
			break;
		case 'y':
			velocity.y = 0;
			break;
		}
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public TiledMapTileLayer getCollisionLayer(int i){
		return (TiledMapTileLayer)map.getLayers().get(i);
	}

}
