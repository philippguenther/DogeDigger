
public class Config {
	private static int windowWidth = 800;
	private static int windowHeight = 600;
	
	private static float boxSize = 100f;
	
	private static float colorTolerance = 0.2f;
	
	public static int getWindowWidth() {
		return Config.windowWidth;
	}
	
	public static int getWindowHeight() {
		return Config.windowHeight;
	}
	
	public static float getBoxSize() {
		return Config.boxSize;
	}
	
	public static float getColorTolerance() {
		return Config.colorTolerance;
	}
}
