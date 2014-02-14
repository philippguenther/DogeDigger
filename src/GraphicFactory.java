import java.util.Arrays;


public class GraphicFactory {
	
	//STATIC--------------------------------------------------
	public static Graphic newStatic() {
		return new GraphicPolygon(GraphicFactory.box, new Color4f(1f, 1f, 1f));
	}
	
	//BOX-----------------------------------------------------
	public static Graphic newBox () {
		return new GraphicImage("BoxGrey.png");
	}
	
	public static Graphic newBoxRed() {
		return new GraphicPolygon(GraphicFactory.box, new Color4f(1f, 0f, 0f, 0.5f));
	}
	
	public static Graphic newBoxGreen() {
		return new GraphicPolygon(GraphicFactory.box, new Color4f(0f, 1f, 0f, 0.5f));
	}
	
	public static Graphic newBoxBlue() {
		return new GraphicPolygon(GraphicFactory.box, new Color4f(0f, 0f, 1f, 0.5f));
	}
	
	public static Graphic newBoxYellow() {
		return new GraphicPolygon(GraphicFactory.box, new Color4f(1f, 1f, 0f, 0.5f));
	}
	
	public static Graphic newBoxActive() {
		return new GraphicPolygon(GraphicFactory.box, new Color4f(1f, 1f, 1f, 0.3f));
	}
	
	
	//DOGE--------------------------------------------------
	public static Graphic newDogeWalking() {
		return new GraphicAnimation(imagesWithEvenClipping("DogeWalking.png", 5), evenDelays(25, 5));
	}
	
	public static Graphic newDogeDigging() {
		return new GraphicAnimation(imagesWithEvenClipping("DogeDigDown.png", 4), evenDelays(40, 4));
	}
	
	public static Graphic newDogeSitting() {
		return new GraphicImage("Doge.png");
	}
	
	public static Graphic newDogeDead() {
		return new GraphicImage("Doge.png");
	}
	
	public static Graphic newDogeIntense() {
		return new GraphicAnimation(imagesWithEvenClipping("DogeIntense.png", 4), evenDelays(1, 4));
	}
	
	
	//HELPER-----------------------------------------------
			
	private static GraphicImage[] imagesWithEvenClipping (String _filename, int c) {
		GraphicImage[] frames = new GraphicImage[c];
		float[] clipping = {0, 0, 1, 1};
		for (int i = 0; i < c; i++) {
			clipping[0] = i * (1f / c);
			clipping[2] = (i+1) * (1f / c);
			frames[i] = new GraphicImage(_filename, clipping);
		}
		return frames;
	}
	
	private static int[] evenDelays (int delay, int c) {
		int[] delays = new int[c];
		Arrays.fill(delays, delay);
		return delays;
	}
	
	private final static Vec2f[] box = {
		new Vec2f(0.046875f, 0f),
		new Vec2f(1f - 0.046875f, 0f),
		new Vec2f(1f, 0.046875f),
		new Vec2f(1f, 1f - 0.046875f),
		new Vec2f(1f - 0.046875f, 1f),
		new Vec2f(0.046875f, 1f),
		new Vec2f(0f, 1f - 0.046875f),
		new Vec2f(0f, 0.046875f)
	};
	
}
