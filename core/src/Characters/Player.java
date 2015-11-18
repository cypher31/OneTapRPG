package Characters;

import Characters.Player.State;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
	
	public enum State {
		RUNNING, JUMPING, FALLING, DYING, ATTACKING, TURNING
	}
	
	public static final float SPEED = 2f;
	public static final float JUMP_VELOCITY = 1f;
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

	public void setState(State newState) {
		this.state = newState;
	}

	public void update(float delta) {
		position.add(velocity.cpy().scl(delta));
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public static float getSPEED() {
		return SPEED;
	}

}
