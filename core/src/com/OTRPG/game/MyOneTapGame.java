package com.OTRPG.game;

import javafx.scene.transform.Scale;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyOneTapGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Sprite imgSprite;
	//float to set the test image width
	float imgWidth;
	//float to set the test image height
	float imgHeight;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("OTRPlayer_Test.jpg");
		//These two variables grab the screen size and sets the image to 10% of the screen size
		imgWidth = Gdx.graphics.getWidth() * .05f;
		imgHeight = Gdx.graphics.getHeight() * .05f;
		//This controls what the sprite is and the actual size of the sprite viewport ... not the size of the image itself
		imgSprite = new Sprite(img, img.getWidth(), img.getHeight());
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//Draws and sets the sprite position and scale -- image height and width are switched here because I want this image to be in portrait
		batch.draw(imgSprite, Gdx.graphics.getWidth() / 2 - imgHeight / 2, Gdx.graphics.getHeight() / 2 - imgWidth / 2, 
				imgHeight, imgWidth);
		batch.end();
	}
}
