
public class Config {
	private static int windowWidth = 480;
	private static int windowHeight = 640;
	
	private static float boxesX = 7;
	private static float boxesY = 9;
	
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
	
	public static boolean getDebug() {
		return true;
	}
}
