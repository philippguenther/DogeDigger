import java.util.Arrays;


public class GraphicFactory {
	public final static Vec2f[] box = {
		new Vec2f(0f, 0f),
		new Vec2f(1f, 0f),
		new Vec2f(1f, 1f),
		new Vec2f(0f, 1f)
	};
	
	public static Graphic newBoxGraphic () {
		return new GraphicImage("Box_greyscale.png");
	}
	
	public static Graphic newBoxColorGraphic(Type type) {
		Color4f color = new Color4f(0f, 0f, 0f, 0.5f);
		switch(type) {
		case RED:
			color.r = 1f;
			break;
		case GREEN:
			color.g = 1f;
			break;
		default:
			color.b = 1f;
		}
		return new GraphicPolygon(GraphicFactory.box, color);
	}
	
	public static Graphic newBoxStaticGraphic () {
		return new GraphicLineLoop(GraphicFactory.box, new Color4f(1f, 1f, 1f));
	}
	
	public static Graphic newDogeGraphic () {
		return new GraphicAnimation(imagesWithEvenClipping("[intensifies].png", 4), evenDelays(1, 4));
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
