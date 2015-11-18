package Game;

import sun.security.x509.DeltaCRLIndicatorExtension;
import Stages.testWorld;

import com.OTRPG.game.Controllers.playerController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class gameScreen implements Screen, InputProcessor{
	
	private testWorld world;
	private worldRenderer renderer;
	private playerController pController;
	
	private float timePressed = .25f;
	public static long beginTime;
	public static float elapsedTime;
	
	private int width, height;

	@Override
	public void show() {
		// TODO Auto-generated method stub
		world = new testWorld();
		renderer = new worldRenderer(world, true);
		pController = new playerController(world);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		pController.update(delta);
		renderer.render();
	}

	//This was necessary to get sprites to show up. Not sure why
	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
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
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.SPACE){
			playerController.buttonPressed();
			beginTime = System.nanoTime();
			playerController.buttonHeld();
	}
		return true;
}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.SPACE)
			playerController.buttonReleased();
			playerController.buttonHeldRelease();
			beginTime = 0;
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
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
