package Characters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	
	public enum State {
		RUNNING, JUMPING, FALLING, DYING, ATTACKING
	}
	
	static final float SPEED = 2f;
	static final float JUMP_VELOCITY = 1f;
	public static final float SIZE = 0.5f;
	
	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.RUNNING;
	boolean facingLeft = true;
	
	public Player(Vector2 position){
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
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
