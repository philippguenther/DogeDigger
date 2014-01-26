import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;


enum State {
	INPUT,
	ANIMATING
}

public class SwapBoxes {
	public static SwapBoxes game = new SwapBoxes();
	
	private long lastFrame;
	private Level currentLevel;
	public State state = State.INPUT;

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		try {
			Mouse.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Config.getWindowWidth() / Config.getBoxSize(), Config.getWindowHeight() / Config.getBoxSize(), 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		this.currentLevel = LevelFactory.getRandomLevel();
		
		while(!Display.isCloseRequested()) {
			int delta = this.getDelta();
			
			input();
			this.currentLevel.tick(delta);
			
			// Clear the screen and depth buffer
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			this.currentLevel.render();
			
			Display.update();
			Display.sync(60);
		}
		Mouse.destroy();
		Display.destroy();
	}
	
	private void input() {
		if (this.state == State.INPUT)
			this.currentLevel.input();
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
		SwapBoxes.game.start();
	}

}
