import graphics.Graphic;
import graphics.GraphicPolygon;
import graphics.GraphicString;
import gui.Button;
import gui.Screen;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
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
		
		// setup window
		Display.setDisplayMode(new DisplayMode(Config.windowX, Config.windowY));
		Display.create();
		Display.setVSyncEnabled(true);
		
		// setup keyboard
		Keyboard.create();
		
		// setup OpenGL
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
			new GraphicString("Game", 0.5f, new Vec2f(0.1f, 0.1f))
		};
		Graphic[] activeGraphics = new Graphic[] {
			new GraphicPolygon(v, new Color4f(0.5f, 0.5f, 0.5f))
		};
		Button game = new Button("game", new Vec2f(1f, 1f), startGraphics, activeGraphics);
		Graphic[] restartGraphics = new Graphic[] {
				new GraphicPolygon(v, new Color4f(0.2f, 0.2f, 0.8f)),
				new GraphicString("Restart", 0.5f, new Vec2f(0.1f, 0.1f))
			};
		Button restart = new Button("restart", new Vec2f(1f, 2f), restartGraphics, activeGraphics);
		Graphic[] settingsGraphics = new Graphic[] {
				new GraphicPolygon(v, new Color4f(0.2f, 0.2f, 0.8f)),
				new GraphicString("Settings", 0.5f, new Vec2f(0.1f, 0.1f))
			};
		Button settings = new Button("setting", new Vec2f(1f, 3f), settingsGraphics, activeGraphics);
		Graphic[] quitGraphics = new Graphic[] {
				new GraphicPolygon(v, new Color4f(0.2f, 0.2f, 0.8f)),
				new GraphicString("Quit", 0.5f, new Vec2f(0.1f, 0.1f))
			};
		Button quit = new Button("quit", new Vec2f(1f, 4f), quitGraphics, activeGraphics);
		Button[] buttons = new Button[] {
				game,
				restart,
				settings,
				quit
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
				String desc = menu.poll(delta);
				if (desc == "game")
					this.state = GameState.LEVEL_PLAY;
				else if (desc == "restart")
					this.state = GameState.LEVEL_LOAD;
				else if (desc == "quit")
					System.exit(0);
				menu.tick(delta);
				menu.render();
				graphicFps.render();
				Display.update();
				Display.sync(30);
				break;
				
			case LEVEL_LOAD:
				this.level = new Level();
				LevelFactory.randomFull(this.level, Config.levelSeed);
				this.state = GameState.LEVEL_PLAY;
				break;
				
			case LEVEL_PLAY:
				if (Keyboard.isKeyDown(Keyboard.KEY_M))
					this.state = GameState.MENU;
				this.level.tick(delta);
				this.level.render();
				
				graphicFps.render();
				Display.update();
				Display.sync(60);
			}
		}
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