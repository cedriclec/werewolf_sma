
package ui.view;
import java.util.ArrayList;

//Import des fichiers libgdx
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import sma.model.DFServices;
import sma.model.PlayerProfile;
import sma.model.Roles;
import sma.model.Status;
import ui.control.MapController;
import ui.model.ViewPlayer;
import ui.model.ViewPlayers;



public class ViewInterfaceGame implements Screen{
	private Stage stage;
	private ViewMap terrain;
	private MapController ctrlTerrain;
	private ViewPlayers viewPlayers;
	private Texture textureNight;
	private Texture textureEndgame;
    List<String> list;
    ScrollPane scrollPane;
	boolean hover;

	private int mapx; 
	private int mapy;
	private App game;
	private Skin skin;

	public ViewInterfaceGame (App game){
		this.ctrlTerrain = new MapController();
		hover = true;
		mapx = 0;
		mapy = 0;
		this.game = game;
	}


	public void show() {
		stage=new Stage(){
			@Override
			public boolean mouseMoved(int x, int y) {
				y=(int) (this.getHeight()-y);

				x = x+10;
				y = y+10;

				mapx = x;
				mapy = y;


				return true;
			}
		};

		Gdx.input.setInputProcessor(stage);
		skin = new Skin( Gdx.files.internal( "resources/visui/uiskin.json" ));
		viewPlayers=new ViewPlayers(((SpriteBatch)stage.getBatch()));

		terrain=new ViewMap((SpriteBatch) stage.getBatch(), ctrlTerrain);

		textureNight = new Texture(Gdx.files.internal("resources/sprites/night.png"));

		textureEndgame = new Texture(Gdx.files.internal("resources/sprites/endgame.png"));
		
		list = new List<String>(skin);
        scrollPane = new ScrollPane(list);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setBounds(0,0, 400,800);
        scrollPane.setPosition(970,300);
        stage.addActor(scrollPane);

	}


	public void dispose() {
		this.dispose();
		stage.dispose();
	}

	public void render(float arg0)
	{

		//System.gc();
		//Pour actualiser l'interface
		Gdx.gl.glClearColor(0.7f,0.7f,0.7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);       
		stage.getBatch().enableBlending();
		stage.getBatch().begin();

		terrain.update();		
		viewPlayers.drawPlayersDead();
		viewPlayers.drawPlayersSleep();


		
		
		if(this.game.getGameInformations()!=null){

			String[] strings = new String[this.game.getGameInformations().getActionLogs().size()];
			strings = this.game.getGameInformations().getActionLogs().toArray(strings);
	        list.setItems(strings);
			if (!this.game.getGameInformations().isEndGame())
	        scrollPane.scrollTo(0, 0, 0, 0);
			if (this.game.getGameInformations().isEndGame())
				stage.getBatch().draw(textureEndgame,0,0);
			else if (this.game.getGameInformations().getDayState().equals("NIGHT")) 
				stage.getBatch().draw(textureNight,0,0);
		}
		
		viewPlayers.drawPlayersWake();

		if(hover)
		{
			BitmapFont  font = new BitmapFont();
			font.setColor(Color.WHITE);
			font.setScale(1.5f);
			font.drawMultiLine(stage.getBatch(), viewPlayers.getLabel(mapx, mapy), mapx, mapy);
		}

		
		if(this.game.getGameInformations()!=null && this.game.getGameInformations().getProfiles()!=null)
				viewPlayers.updatePlayers(this.game.gameInformations.getProfiles());
		//stage.getBatch().draw(style.getBackground(), 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage.getBatch().end(); 
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		stage.getBatch().disableBlending();


	}


	public void hide() {
	}


	public void pause() {

	}


	public void resize(int arg0, int arg1) {

	}



	public void resume() {

	}
}