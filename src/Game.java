import graphics.Graphic;
import graphics.GraphicPolygon;
import graphics.GraphicString;
import gui.Button;
import gui.Screen;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import util.Color4f;
import util.Vec2f;

enum GameState {
	MENU,
	LEVEL_LOAD,
	LEVEL_PLAY
}

public class Game {
	public static Game game;

	public GameState state = GameState.MENU;

	private long lastFrame = this.getTime() - 1;
	private int lastFps = 60;
	private Level level;

	public void start() throws LWJGLException {
		
		// Window setup
		Display.setDisplayMode(new DisplayMode(Config.windowX, Config.windowY));
		Display.create();
		Display.setVSyncEnabled(true);
		
		// OpenGL setup
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Config.windowBoxesX, Config.windowBoxesY, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glDisable(GL11.GL_DEPTH_TEST); //it's a 2D game
		
		// loading bitmap font
		GraphicString.init();
		
		// setup menu
		Vec2f[] v = new Vec2f[] {
			new Vec2f(0f, 0f),
			new Vec2f(5f, 0f),
			new Vec2f(5f, 0.7f),
			new Vec2f(0f, 0.7f)
		};
		Graphic[] startGraphics = new Graphic[] {
			new GraphicPolygon(v, new Color4f(0.2f, 0.2f, 0.8f)),
			new GraphicString("Start", 0.5f, new Vec2f(0.1f, 0.1f))
		};
		Graphic[] activeGraphics = new Graphic[] {
			new GraphicPolygon(v, new Color4f(0.5f, 0.5f, 0.5f))
		};
		Button start = new Button("start", new Vec2f(1f, 1f), startGraphics, activeGraphics);
		Graphic[] settingsGraphics = new Graphic[] {
				new GraphicPolygon(v, new Color4f(0.2f, 0.2f, 0.8f)),
				new GraphicString("Settings", 0.5f, new Vec2f(0.1f, 0.1f))
			};
		Button settings = new Button("settings", new Vec2f(1f, 2f), settingsGraphics, activeGraphics);
		Button[] buttons = new Button[] {
				start,
				settings
		};
		Screen menu = new Screen(buttons);
		
		// setup level
		this.level = new Level();
		LevelFactory.randomFull(this.level, Config.levelSeed);
		
		GraphicString graphicFps = new GraphicString("0", 0.5f, new Vec2f(0.1f, 0.1f));
		
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Config.keyQuit)) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			int delta = this.getDelta();
			
			int fps = 1000 / delta;
			if (this.lastFps - fps > 1)
				graphicFps.setString(Integer.toString(fps));
			this.lastFps = fps;
			
			switch (this.state) {
			case MENU:
				String desc = menu.poll();
				if (desc == "start")
					this.state = GameState.LEVEL_PLAY;
				menu.tick(delta);
				menu.render();
				break;
				
			case LEVEL_LOAD:
				break;
				
			case LEVEL_PLAY:
				if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
					this.level = new Level();
					LevelFactory.randomFull(this.level, Config.levelSeed);
				}
				
				this.level.tick(delta);
				this.level.render();
			}
			
			graphicFps.render();

			Display.update();
			Display.sync(60);
		}
		Mouse.destroy();
		Display.destroy();
	}

	public Level getLevel() {
		return this.level;
	}

	private long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	private int getDelta() {
		long time = getTime();
		int delta = (int) (time - this.lastFrame);
		this.lastFrame = time;
		return delta;
	}

	public static void main(String[] args) throws LWJGLException {
		new Game().start();
	}

}