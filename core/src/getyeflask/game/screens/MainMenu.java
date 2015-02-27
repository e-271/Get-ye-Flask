package getyeflask.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen {
	
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton exitButton, playButton;
	private BitmapFont font;
	//private Label heading;
	
	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		atlas = new TextureAtlas("assets/ui/button.pack"); //TODO insert button pack
		skin = new Skin(atlas);
		//Table = A group that sizes and positions children using table constraints.
		//Skin argument allows addString()
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//false because we inverted coords, otherwise it would be flipped
		font = new BitmapFont(Gdx.files.internal("assets/ui/bitmap fonts/ORC_A_STD.fnt"), false); //TODO insert bitmap .fnt file
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.down = skin.getDrawable("button.down"); //TODO creat button.down, button.up
		textButtonStyle.up = skin.getDrawable("button.up");
		textButtonStyle.pressedOffsetX = 1; //moves text 1 pixel right upon button press
		textButtonStyle.pressedOffsetY = -1; //moves text 1 pixel "down" (actually up)
		textButtonStyle.font = font;
		
		exitButton = new TextButton("EXIT", textButtonStyle);
		exitButton.addListener(
				new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y){
						Gdx.app.exit();
					}
				});
		exitButton.pad(15); //n-pixel pad around text
		
		playButton = new TextButton("PLAY", textButtonStyle);
		playButton.addListener(
				new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y){
						((Game) Gdx.app.getApplicationListener()).setScreen(new Play());
					}
				});
		playButton.pad(15);
		
		//LabelStyle headingStyle = new LabelStyle(font, Color.WHITE); 
		//heading = new Label(GetYeFlask.NAME, headingStyle);
		//table.add(heading);
		//table.getCell(heading).spaceBottom(100); 
		
		table.row(); //add a row
		table.add(playButton);
		table.row();
		table.getCell(playButton).spaceBottom(30); //TODO space however
		table.add(exitButton);
		//table.debug(); //enable degbug lines
		
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1); //TODO determine clear color
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true); //bool is keepAspectRatio
		table.invalidateHierarchy(); //recalculates table layout
		table.setSize(width, height);
		
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		font.dispose();
	}

}
