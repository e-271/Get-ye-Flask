package getyeflask.game.screens;

import getyeflask.game.entities.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Play implements Screen {
	

	
	private static TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private static Player player;
	private TextureAtlas playerAtlas;
	private int[] background = new int[] {0}, character = new int[] {3}, foreground = new int[] { 1, 2 };

	
	@Override
	public void show() {
		map = new TmxMapLoader().load("assets/maps/GuildRoom1.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		
		playerAtlas = new TextureAtlas("assets/entities/atlas.pack");
		player = new Player(new Sprite(new Texture("assets/entities/player.png")), playerAtlas, map);
		player.setPosition((4) * player.getCollisionLayer(0).getTileWidth(), 
							(player.getCollisionLayer(0).getHeight() - 5)* player.getCollisionLayer(0).getTileHeight());
		Gdx.input.setInputProcessor(player);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1); //TODO determine clear color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		camera.update();
		
		renderer.setView(camera);
		renderer.render(background);
		renderer.render(foreground);
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		renderer.getBatch().end(); 
		renderer.render(character);

	}
	
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 1.5f;
		camera.viewportHeight = height / 1.5f;
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
		map.dispose();
		renderer.dispose();
		playerAtlas.dispose();
	}

	public static TiledMap getMap(){
		return map;
	}
	
	public static Player getPlayer(){
		return player;
	}
}
