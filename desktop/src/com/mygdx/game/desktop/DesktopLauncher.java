package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Skeleton;
import com.mygdx.game.mainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Skeleton.WIDTH;
		config.height = Skeleton.HEIGHT;
		config.resizable = false;
		new LwjglApplication(new Skeleton(), config);
	}
}