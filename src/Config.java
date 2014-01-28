import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.lwjgl.input.Keyboard;


public class Config {
	private static int windowWidth = 480;
	private static int windowHeight = 640;
	
	private static float boxesX = 7;
	private static float boxesY = 9;
	
	private static Vec2 gravity = new Vec2(0f, 10f);
	
	private static Color3f sensorColor = new Color3f(1f, 1f, 1f);
	
	public static int keyLeft = Keyboard.KEY_LEFT;
	public static int keyRight = Keyboard.KEY_RIGHT;
	public static int keyJump = Keyboard.KEY_SPACE;
	public static int keyDig = Keyboard.KEY_DOWN;
	
	public static int keyReset = Keyboard.KEY_R;
	public static int keyQuit = Keyboard.KEY_Q;
	
	public static int getWindowWidth() {
		return Config.windowWidth;
	}
	
	public static int getWindowHeight() {
		return Config.windowHeight;
	}
	
	public static float getBoxesX() {
		return Config.boxesX;
	}
	
	public static float getBoxesY() {
		return Config.boxesY;
	}
	
	public static Color3f getSensorColor() {
		return Config.sensorColor;
	}
	
	public static Vec2 getGravity() {
		return Config.gravity;
	}
	
	public static boolean getDebug() {
		return true;
	}
}
