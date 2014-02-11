import org.lwjgl.input.Keyboard;


public class Config {
	public static int windowWidth = 480;
	public static int windowHeight = 640;

	public static int boxesX = 7;
	public static int boxesY = 9;
	
	public static int levelMaxX = boxesX;
	public static int levelMaxY = 64;
	
	public static int keyUp = Keyboard.KEY_UP;
	public static int keyRight = Keyboard.KEY_RIGHT;
	public static int keyDown = Keyboard.KEY_DOWN;
	public static int keyLeft = Keyboard.KEY_LEFT;
	public static int keyDig = Keyboard.KEY_D;

	public static int keyReset = Keyboard.KEY_R;
	public static int keyQuit = Keyboard.KEY_Q;

	public static int delayMove = 200;
	public static int delayDig = 300;
}