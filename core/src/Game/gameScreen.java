package Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class gameScreen implements Screen{

		private worldRenderer renderer;
		
		public gameScreen(){
			renderer = new worldRenderer();
		}
	 
		@Override
		public void render(float delta) {
			
			Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			renderer.render(delta);
		}
	 
		@Override
		public void resize(int width, int height) {
			
		}
	 
		@Override
		public void show() {
			
		}
	 
		@Override
		public void hide() {
			
		}
	 
		@Override
		public void pause() {
			
		}
	 
		@Override
		public void resume() {
			
		}
	 
		@Override
		public void dispose() {
			
		}
}