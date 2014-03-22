import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

enum State {
	LOADING,
	RUNNING
}

public class Game {
	public static Game game;

	public State state = State.RUNNING;

	private long lastFrame;
	private Level level;

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(Config.windowX, Config.windowY));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Config.windowBoxesX, Config.windowBoxesY, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glDisable(GL11.GL_DEPTH_TEST); //it's a 2D game

		this.level = new Level();
		//LevelFactory.random(this.level, Config.levelSeed);
		LevelFactory.randomFull(this.level, Config.levelSeed);
		
		this.lastFrame = getTime();
		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Config.keyQuit)) {
			
			if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
				this.level = new Level();
				LevelFactory.randomFull(this.level, Config.levelSeed);
			}
			
			int delta = this.getDelta();

			if (this.state == State.RUNNING) {
				this.level.tick(delta);
				
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
				this.level.render();
			}

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

	public static void main(String[] args) {
		new Game().start();
	}

}