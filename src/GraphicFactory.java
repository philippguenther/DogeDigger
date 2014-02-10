import java.util.Arrays;


public class GraphicFactory {
	private final static Vec2f[] box = {
		new Vec2f(0f, 0f),
		new Vec2f(1f, 0f),
		new Vec2f(1f, 1f),
		new Vec2f(0f, 1f)
	};
	
	public static Graphic newBoxGraphic () {
		return new GraphicPolygon(GraphicFactory.box, new Color4f(1f, 1f, 0f));
	}
	
	public static Graphic newDogeGraphic () {
		return new GraphicAnimation(imagesWithEvenClipping("BoxGlow.png", 7), evenDelays(75, 7));
	}
	
			
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
	
}
