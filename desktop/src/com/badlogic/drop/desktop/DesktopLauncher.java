package com.badlogic.drop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.drop.Drop;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	      cfg.title = "Drop";
	      cfg.width = 800;
	      cfg.height = 480;
	      new LwjglApplication(new Drop(), cfg);
	}
}
