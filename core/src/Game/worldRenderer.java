package Game;

import Characters.Player;
import Environment.testBlocks;
import Stages.testWorld;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class worldRenderer {
	
	private testWorld world;
	private OrthographicCamera cam;
	
	ShapeRenderer debugRenderer = new ShapeRenderer();
	
	public worldRenderer(testWorld world){
		this.world = world;
		this.cam = new OrthographicCamera(10, 7);
		this.cam.position.set(5, 3.5f, 0);
		this.cam.update();
	}
	
	public void render(){
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