package Game;

import Characters.Player;
import Environment.testBlocks;
import Stages.testWorld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class worldRenderer {
	
	private static final float CAMERA_WIDTH = 16f;
	private static final float CAMERA_HEIGHT = 9f;
	
	private testWorld world;
	private OrthographicCamera cam;
	
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	private Texture playerTexture;
	private Texture blockTexture;
	
	private SpriteBatch spriteBatch;
	private boolean debug = false;
	private int width;
	private int height;
	private float ppuX;
	private float ppuY;
	public void setSize (int w, int h){
		this.width = w;
		this.height = h;
		ppuX = (float)width / CAMERA_WIDTH;
		ppuY = (float)height / CAMERA_HEIGHT;
	}
	
	public worldRenderer(testWorld world, boolean debug){
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);;
		this.cam.position.set(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();
		loadTextures();
	}
	
	private void loadTextures() {
		playerTexture = new Texture("OTRPlayer_Test.jpg");
		blockTexture = new Texture("badlogic.jpg");
	}

	public void render(){
		spriteBatch.begin();
			drawBlocks();
			drawPlayer();
		spriteBatch.end();
		if(debug)
			drawDebug();
		
		
		}
		
		private void drawPlayer() {
		Player player = world.getPlayer();
		spriteBatch.draw(playerTexture, player.getPosition().x * ppuX, player.getPosition().y * ppuY,
				Player.SIZE * ppuX, Player.SIZE * ppuY);
	}

		private void drawBlocks() {
			for (testBlocks block : world.getBlocks()){
				spriteBatch.draw(blockTexture, block.getPosition().x * ppuX, block.getPosition().y * ppuY
						, testBlocks.SIZE * ppuX, testBlocks.SIZE * ppuY);
			}
	}


	private void drawDebug() {
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.Line);
		for(testBlocks block : world.getBlocks()){
			Rectangle rect = block.getBounds();
			float x1 = block.getPosition().x + rect.x;
			float y1 = block.getPosition().y + rect.y;
			debugRenderer.setColor(new Color(1, 0, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);	
			}
			
			Player player = world.getPlayer();
			Rectangle rect = player.getBounds();
			float x1 = player.getPosition().x + rect.x;
			float y1 = player.getPosition().y + rect.y;
			debugRenderer.setColor(new Color(0, 1, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
			debugRenderer.end();
	}

}
