package Environment;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class testBlocks {
	
	static final float SIZE = 1f;
	
	Vector2 position = new Vector2();
	Rectangle bounds = new Rectangle();
	
	public testBlocks(Vector2 pos){
		this.position = pos;
		this.bounds.width = SIZE;
		this.bounds.height = SIZE;
	}

	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return bounds;
	}

	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}

}