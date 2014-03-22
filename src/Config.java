import org.lwjgl.input.Keyboard;


public class Config {
	public final static int windowX = 448;
	public final static int windowY = 576;

	public final static int gameBoxesX = 7;
	public final static int gameBoxesY = 9;
	
	public final static int levelMaxX = Config.gameBoxesX;
	public final static int levelMaxY = 64;
	public final static long levelSeed = 1l;
	
	public final static int keyUp = Keyboard.KEY_W;
	public final static int keyRight = Keyboard.KEY_D;
	public final static int keyDown = Keyboard.KEY_S;
	public final static int keyLeft = Keyboard.KEY_A;
	public final static int keyDig = Keyboard.KEY_SPACE;

	public final static int keyReset = Keyboard.KEY_R;
	public final static int keyQuit = Keyboard.KEY_Q;

	public final static int dogeDelayMove = 0;
	public final static int dogeDelayDig = 300;
	
	public final static int boxDecay = 1000;
	public final static int boxMove = 100;
	
	public final static float levelStandardGravity = 1f;
}