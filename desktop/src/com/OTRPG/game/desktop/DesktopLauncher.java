package com.OTRPG.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.OTRPG.game.MyOneTapGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.height = 1080;
		cfg.width = 1920;
		new LwjglApplication(new MyOneTapGame(), cfg);
	}
}
