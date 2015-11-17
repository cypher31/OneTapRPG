package com.OTRPG.game;

import Game.gameScreen;

import com.badlogic.gdx.Game;

public class MyOneTapGame extends Game {

	@Override
	public void create() {
		setScreen(new gameScreen());
		
	}
	
}
