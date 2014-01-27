import org.jbox2d.common.Vec2;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

enum State {
	LOADING,
	RUNNING
}

public class DogeDriller {
	public static DogeDriller game = new DogeDriller();
	
	public State state = State.RUNNING;
	
	private long lastFrame;
	private Level currentLevel;

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(Config.getWindowWidth(), Config.getWindowHeight()));
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
		GL11.glOrtho(0, Config.getBoxesX(), Config.getBoxesY() + 1, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		this.currentLevel = LevelFactory.getRandomLevel();
		// render grid
		if (Config.getDebug()) {
			for (int x = 0; x < Config.getBoxesX(); x++) {
				for (int y = 0; y < Config.getBoxesY(); y++) {
					this.currentLevel.addEntity(new EntityBox(new Vec2(x, y), null, new GraphicQuad()));
				}
			}
		}
		
		while(!Display.isCloseRequested()) {
			int delta = this.getDelta();
			
			this.currentLevel.input();
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
		DogeDriller.game.start();
	}

}
