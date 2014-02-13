import java.util.Arrays;


public class GraphicFactory {
	
	//STATIC--------------------------------------------------
	public static Graphic newStatic() {
		return new GraphicPolygon(GraphicFactory.box, new Color4f(1f, 1f, 1f));
	}
	
	//BOX-----------------------------------------------------
	public static Graphic newBox () {
		return new GraphicImage("Box_greyscale.png");
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
	public static Graphic newDoge() {
		return new GraphicImage("[intensifies].png", new float[]{0, 0, 0.25f, 1});
	}
	
	public static Graphic newDogeDigging() {
		return new GraphicImage("[intensifies].png", new float[]{0, 0, 0.25f, 1});
	}
	
	public static Graphic newDogeSitting() {
		return new GraphicImage("[intensifies].png", new float[]{0, 0, 0.25f, 1});
	}
	
	public static Graphic newDogeDead() {
		return new GraphicImage("[intensifies].png", new float[]{0, 0, 0.25f, 1});
	}
	
	public static Graphic newDogeIntense() {
		return new GraphicAnimation(imagesWithEvenClipping("[intensifies].png", 4), evenDelays(1, 4));
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
