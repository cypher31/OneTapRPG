package Game;

import Characters.Player;
import Stages.tiledTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class worldRenderer implements InputProcessor {
	
	private static final float UNIT_SCALE = 1 / 32f;
	private static final float RUNNING_FRAME_DURATION = 0.175f;

	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private tiledTest level;
	private Player player;

	private Batch spriteBatch;

	/* Textures for Player */
	private TextureRegion playerIdleLeft;
	private TextureRegion playerIdleRight;

	private TextureRegion playerJumpLeft;
	private TextureRegion playerJumpRight;

	private TextureRegion playerWalkLeft;
	private TextureRegion playerWalkRight;

	private TextureRegion playerFallLeft;
	private TextureRegion playerFallRight;

	//private Array<Enemy> enemyList;

	private TextureRegion playerFrame;
	private TextureRegion enemyFrame;

	/* Animations for Player */
	private Animation walkLeftAnimation;
	private Animation walkRightAnimation;

	private boolean jumpingPressed;
	private long jumpPressedTime;

	/* for debug rendering */
	ShapeRenderer debugRenderer = new ShapeRenderer();

	private Array<Rectangle> tiles;
	private Array<Rectangle> enemyTiles;

	private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
		@Override
		protected Rectangle newObject() {
			return new Rectangle();
		}
	};

	public worldRenderer() {

		level = new tiledTest("Maps/tiledTest1.tmx");
		player = new Player();
		//enemyList = new Array<Enemy>();

		loadPlayerTextures();

		tiles = new Array<Rectangle>();
		enemyTiles = new Array<Rectangle>();

		Gdx.input.setInputProcessor(this);

		player.setPosition(new Vector2(16, 32));
		player.setWidth(UNIT_SCALE * playerIdleRight.getRegionWidth());
		player.setHeight(UNIT_SCALE * playerIdleRight.getRegionHeight());

	//	loadEnemies();

		renderer = new OrthogonalTiledMapRenderer(level.getMap(), UNIT_SCALE);
		spriteBatch = renderer.getBatch();
		debugRenderer = new ShapeRenderer();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 30, 20);
		camera.update();

	}

	public void render(float delta) {

		//Makes it so when I move or resize the screen my game doesnt freak out
		delta = Math.min(delta, 0.1f);
				
		debugRenderer.setProjectionMatrix(camera.combined);

		renderer.setView(camera);

		camera.position.x = player.getPosition().x;
		camera.position.y = player.getPosition().y;
		camera.update();

		renderer.render();

		spriteBatch.begin();

		drawPlayer();
	//	drawEnemies();

		spriteBatch.end();

		drawDebug();

		updatePlayer(delta);

		// player.update(delta);

	}

	public void updatePlayer(float delta) {
		if (delta == 0)
			return;

		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			player.getVelocity().x = -Player.MAX_VELOCITY;
			if (player.isGrounded()) {
				player.setState(Player.State.Walking);
			}
			player.setFacingRight(true);
		}

		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			player.getVelocity().x = Player.MAX_VELOCITY;
			if (player.isGrounded()) {
				player.setState(Player.State.Walking);
			}
			player.setFacingRight(false);
		}

		if (player.getState() != Player.State.Falling) {
			if (player.getVelocity().y < 0) {
				player.setState(Player.State.Falling);
				player.setGrounded(false);
			}
		}

		player.getAcceleration().y = Player.GRAVITY;
		player.getAcceleration().scl(delta);
		player.getVelocity().add(player.getAcceleration().x,
				player.getAcceleration().y);

		// clamp the velocity to the maximum, x-axis only
		if (Math.abs(player.getVelocity().x) > Player.MAX_VELOCITY) {
			player.getVelocity().x = Math.signum(player.getVelocity().x)
					* Player.MAX_VELOCITY;
		}

		// clamp the velocity to 0 if it's < 1, and set the state to standing
		if (Math.abs(player.getVelocity().x) < 1) {
			player.getVelocity().x = 0;
			if (player.isGrounded()) {
				player.setState(Player.State.Standing);
			}
		}

		player.getVelocity().scl(delta);

		Rectangle playerRect = rectPool.obtain();
		playerRect.set(player.getPosition().x,
				player.getPosition().y + player.getHeight() * .1f,
				player.getWidth(), player.getHeight());

		int startX, startY, endX, endY;
		if (player.getVelocity().x > 0) {
			startX = endX = (int) (player.getPosition().x + player.getWidth() + player
					.getVelocity().x);
		} else {
			startX = endX = (int) (player.getPosition().x + player
					.getVelocity().x);
		}

		startY = (int) (player.getPosition().y);
		endY = (int) (player.getPosition().y + player.getHeight());
		getTiles(startX, startY, endX, endY, tiles);

		playerRect.x += player.getVelocity().x;

		for (Rectangle tile : tiles) {
			if (playerRect.overlaps(tile)) {
				player.getVelocity().x = 0;
				break;
			}
		}

		playerRect.set(player.getPosition().x, player.getPosition().y,
				player.getWidth(), player.getHeight());

		if (player.getVelocity().y > 0) {
			startY = endY = (int) (player.getPosition().y + player.getHeight() + player
					.getVelocity().y);
		} else {
			startY = endY = (int) (player.getPosition().y + player
					.getVelocity().y);
		}

		startX = (int) (player.getPosition().x);
		endX = (int) (player.getPosition().x + player.getWidth());
		getTiles(startX, startY, endX, endY, tiles);

		playerRect.y += player.getVelocity().y;

		for (Rectangle tile : tiles) {
			if (playerRect.overlaps(tile)) {
				if (player.getVelocity().y > 0) {
					player.getVelocity().y = tile.y - player.getHeight();
				} else {
					player.getPosition().y = tile.y + tile.height;
					player.setGrounded(true);
				}
				player.getVelocity().y = 0;

				break;
			}
		}

		startX = (int) (player.getPosition().x - player.getWidth());
		endX = (int) (player.getPosition().x + player.getWidth() * 2);

		startY = (int) (player.getPosition().y - player.getHeight());
		endY = (int) (player.getPosition().y + player.getHeight() * 2);

		//getEnemyTiles(startX, startY, endX, endY, enemyTiles);

		for (Rectangle tile : enemyTiles) {
			if (playerRect.overlaps(tile)) {
				player.setPosition(new Vector2(15, 17));
			}
		}

		rectPool.free(playerRect);

		// unscale the velocity by the inverse delta time and set
		// the latest position
		player.getPosition().add(player.getVelocity());

		player.getVelocity().scl(1 / delta);

		player.getVelocity().x *= Player.DAMPING;

		player.setStateTime(player.getStateTime() + delta);

		player.getPosition().y += player.getVelocity().cpy().scl(delta).y;

	}

	public void loadPlayerTextures() {

		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("atlas.pack"));
		TextureAtlas atlas2 = new TextureAtlas(
				Gdx.files.internal("atlas2.pack"));

		/* Standing */
		playerIdleLeft = atlas.findRegion("0");

		playerIdleRight = new TextureRegion(playerIdleLeft);
		playerIdleRight.flip(true, false);

		// Just loading texture to get width and height for bounding box
		playerWalkRight = atlas.findRegion("0");
		playerWalkLeft = new TextureRegion(playerWalkRight);
		playerWalkLeft.flip(true, false);

		TextureRegion[] walkLeftFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			walkLeftFrames[i] = atlas.findRegion(((i) + ""));

		}

		walkLeftAnimation = new Animation(RUNNING_FRAME_DURATION,
				walkLeftFrames);

		TextureRegion[] walkRightFrames = new TextureRegion[3];
		for (int i = 0; i < 3; i++) {
			walkRightFrames[i] = new TextureRegion(walkLeftFrames[i]);
			walkRightFrames[i].flip(true, false);
		}

		walkRightAnimation = new Animation(RUNNING_FRAME_DURATION,
				walkRightFrames);

		playerJumpRight = atlas.findRegion("1");
		playerJumpLeft = new TextureRegion(playerJumpRight);
		playerJumpLeft.flip(true, false);

		playerFallRight = atlas2.findRegion("0");
		playerFallLeft = new TextureRegion(playerFallRight);
		playerFallLeft.flip(true, false);

//		enemyFrame = new TextureRegion(new Texture("enemy0.png"));
//		enemyFrame.flip(true, false);
	}

//	public void loadEnemies() {
//		Enemy en1 = new Enemy(new Vector2(18, 17));
//
//		en1.setWidth(player.getWidth());
//
//		en1.setHeight(player.getHeight());
//
//		enemyList.add(en1);
//	}

	private void getTiles(int startX, int startY, int endX, int endY,
			Array<Rectangle> tiles) {
		TiledMapTileLayer layer = (TiledMapTileLayer) level.getMap()
				.getLayers().get(0);
		rectPool.freeAll(tiles);
		tiles.clear();
		for (int y = startY; y <= endY; y++) {
			for (int x = startX; x <= endX; x++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null) {
					Rectangle rect = rectPool.obtain();
					rect.set(x, y, 1, 1);
					tiles.add(rect);
				}
			}
		}
	}

//	private void getEnemyTiles(int startX, int startY, int endX, int endY,
//			Array<Rectangle> tiles) {
//		rectPool.freeAll(tiles);
//		tiles.clear();
//		for (Enemy enemy : enemyList) {
//			if (startX < enemy.getPosition().x && endX > enemy.getPosition().x) {
//				Rectangle rect = rectPool.obtain();
//				rect.set(enemy.getPosition().x, enemy.getPosition().y,
//						enemy.getWidth(), enemy.getHeight());
//				tiles.add(rect);
//			}
//		}
//	}

	public void drawPlayer() {

		playerFrame = player.isFacingRight() ? playerIdleRight : playerIdleLeft;

		if (player.getState() == Player.State.Walking) {
			playerFrame = player.isFacingRight() ? walkRightAnimation
					.getKeyFrame(player.getStateTime(), true)
					: walkLeftAnimation
							.getKeyFrame(player.getStateTime(), true);
		} else if (player.getState() == Player.State.Jumping) {
			playerFrame = player.isFacingRight() ? playerJumpLeft
					: playerJumpRight;
		} else if (player.getState() == Player.State.Falling) {
			playerFrame = player.isFacingRight() ? playerFallLeft
					: playerFallRight;
		}

		spriteBatch.draw(playerFrame, player.getPosition().x,
				player.getPosition().y, player.getWidth(), player.getHeight());
	}

//	public void drawEnemies() {
//		for (Enemy enemy : enemyList) {
//			spriteBatch.draw(enemyFrame, enemy.getPosition().x,
//					enemy.getPosition().y, enemy.getWidth(), enemy.getHeight());
//			
//			debugRenderer.begin(ShapeType.Line);
//			
//			debugRenderer.rect(enemy.getPosition().x, enemy.getPosition().y,
//					enemy.getWidth(), enemy.getHeight());
//		}
//	}

	public void drawDebug() {
		debugRenderer.setAutoShapeType(true);
		
		debugRenderer.begin();
		
		debugRenderer.setColor(new Color(0, 1, 0, 1));

		debugRenderer.rect(player.getPosition().x, player.getPosition().y,
				player.getWidth(), player.getHeight());

		debugRenderer.end();

	}

	@Override
	public boolean keyDown(int keycode) {

		if (keycode == Keys.SPACE && player.isGrounded()
				&& player.getState() != Player.State.Falling) {
			if (!player.getState().equals(Player.State.Jumping)) {
				jumpingPressed = true;
				player.setGrounded(false);
				jumpPressedTime = System.currentTimeMillis();
				player.setState(Player.State.Jumping);
				player.getVelocity().y = Player.MAX_JUMP_SPEED;
			} else {
				if ((jumpingPressed && ((System.currentTimeMillis() - jumpPressedTime) >= Player.LONG_JUMP_PRESS))) {
					jumpingPressed = false;
				} else {
					if (jumpingPressed) {
						player.getVelocity().y = Player.MAX_JUMP_SPEED;
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.SPACE && player.getState() == Player.State.Jumping) {
			player.getAcceleration().y = Player.GRAVITY;
			player.getAcceleration().scl(Gdx.graphics.getDeltaTime());
			player.getVelocity().add(player.getAcceleration().x,
					player.getAcceleration().y);
			player.setState(Player.State.Falling);
			jumpingPressed = false;
		}
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
