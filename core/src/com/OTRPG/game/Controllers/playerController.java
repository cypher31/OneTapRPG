package com.OTRPG.game.Controllers;



import java.util.HashMap;
import java.util.Map;

import Characters.Player;
import Characters.Player.State;
import Game.gameScreen;
import Stages.testWorld;

import com.badlogic.gdx.math.Vector2;

public class playerController {
	
	enum Keys{
		JUMP, TURN;
	}
	
	private testWorld world;
	private Player player;
	
	static Map<Keys, Boolean> keys = new HashMap<playerController.Keys, Boolean>();
	static {
		keys.put(Keys.JUMP, false);
		keys.put(Keys.TURN, false);
	};

	public playerController(testWorld world){
		this.world = world;
		this.player = world.getPlayer();
	}
	
	public static void buttonPressed(){
		keys.get(keys.put(Keys.JUMP, true));
	}
	
	public static void buttonReleased(){
		keys.get(keys.put(Keys.JUMP, false));
	}
	
	public static void buttonHeld(){
		keys.get(keys.put(Keys.TURN, true));
	}
	
	public static void buttonHeldRelease(){
		keys.get(keys.put(Keys.TURN, false));
	}
	
	public void update(float delta){
		processInput();
		player.update(delta);
	}

	private void processInput() {
		if(keys.get(Keys.JUMP)){
			player.setState(State.JUMPING);
			//player.getVelocity().y = Player.JUMP_VELOCITY;
			//player.getVelocity().x = Player.SPEED;
			gameScreen.elapsedTime = (System.nanoTime() - gameScreen.beginTime) / 1000000000.0f;
			System.out.println(gameScreen.elapsedTime);
		}
		
		if(keys.get(Keys.TURN) && gameScreen.elapsedTime > 1f && player.getVelocity().x > 0f){
			player.setState(State.TURNING);
			player.getVelocity().x = -(Player.SPEED);
			System.out.println(player.getVelocity().x);
		}
		
		if(keys.get(Keys.TURN) && gameScreen.elapsedTime > 1f && player.getVelocity().x < 0f){
			player.setState(State.TURNING);
			player.getVelocity().x = (Player.SPEED);
			System.out.println(player.getVelocity().x);
		}
	}
	
}
