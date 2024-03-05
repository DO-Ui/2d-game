package com.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;	

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		config.setWindowedMode(1920, 1080);
		config.setForegroundFPS(165); // Set FPS to 165 (I personally have a 165Hz monitor, so this is what I'm setting it to)
		config.setTitle("2D Game");
		new Lwjgl3Application(new Game(), config);
	}
}
