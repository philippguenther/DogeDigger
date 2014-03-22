
import graphics.Graphic;
import graphics.GraphicAnimation;
import graphics.GraphicImage;
import graphics.GraphicPolygon;

import java.util.Arrays;

import util.Color4f;
import util.Vec2f;


public class GraphicFactory {
	
	public static Graphic ENTITY_STATIC = 			new GraphicImage("BoxGrey.png");
	
	public static Graphic ENTITY_BOX = 				new GraphicImage("BoxGrey.png");
	public static Graphic ENTITY_BOX_RED = 			new GraphicPolygon(GraphicFactory.box(), new Color4f(1f, 0f, 0f, 0.5f));
	public static Graphic ENTITY_BOX_GREEN =		new GraphicPolygon(GraphicFactory.box(), new Color4f(0f, 1f, 0f, 0.5f));
	public static Graphic ENTITY_BOX_BLUE =			new GraphicPolygon(GraphicFactory.box(), new Color4f(0f, 0f, 1f, 0.5f));
	public static Graphic ENTITY_BOX_YELLOW =		new GraphicPolygon(GraphicFactory.box(), new Color4f(1f, 1f, 0f, 0.5f));
	public static Graphic ENTITY_BOX_ACTIVE =		new GraphicPolygon(GraphicFactory.box(), new Color4f(1f, 1f, 1f, 0.3f));
	
	public static Graphic DOGE_BODY_DEAD =			new GraphicImage("DogeBodyWalking.png", new float[] {0f, 0f, 0.2f, 1f});
	public static Graphic DOGE_BODY_DIGGING_DOWN =	new GraphicAnimation(imagesWithEvenClipping("DogeBodyDiggingDown.png", 4), evenDelays(25, 4));
	public static Graphic DOGE_BODY_DIGGING_SIDE =	new GraphicAnimation(imagesWithEvenClipping("DogeBodyDiggingSide.png", 5), evenDelays(20, 5));
	public static Graphic DOGE_BODY_WAITING =		new GraphicImage("DogeBodyWalking.png", new float[] {0f, 0f, 0.2f, 1f});
	public static Graphic DOGE_BODY_WALKING =		new GraphicAnimation(imagesWithEvenClipping("DogeBodyWalking.png", 5), evenDelays(20, 5));
	
	public static Graphic DOGE_HEAD_DEAD =			new GraphicImage("DogeHeadWalking.png", new float[] {0f, 0f, 0.2f, 1f});
	public static Graphic DOGE_HEAD_DIGGING_DOWN =	new GraphicAnimation(imagesWithEvenClipping("DogeHeadDiggingDown.png", 4), evenDelays(25, 4));
	public static Graphic DOGE_HEAD_DIGGING_SIDE =	new GraphicAnimation(imagesWithEvenClipping("DogeHeadDiggingSide.png", 5), evenDelays(20, 5));
	public static Graphic DOGE_HEAD_WAITING =		new GraphicImage("DogeHeadWalking.png", new float[] {0f, 0f, 0.2f, 1f});
	public static Graphic DOGE_HEAD_WALKING =		new GraphicAnimation(imagesWithEvenClipping("DogeHeadWalking.png", 5), evenDelays(20, 5));
	
	
	private static GraphicImage[] imagesWithEvenClipping(String _filename, int c) {
		GraphicImage[] frames = new GraphicImage[c];
		float[] clipping = {0, 0, 1, 1};
		for (int i = 0; i < c; i++) {
			clipping[0] = i * (1f / c);
			clipping[2] = (i+1) * (1f / c);
			frames[i] = new GraphicImage(_filename, clipping);
		}
		return frames;
	}
	
	private static int[] evenDelays(int delay, int c) {
		int[] delays = new int[c];
		Arrays.fill(delays, delay);
		return delays;
	}
	
	private static Vec2f[] box() {
		return new Vec2f[] {
			new Vec2f(0.046875f, 0f),
			new Vec2f(1f - 0.046875f, 0f),
			new Vec2f(1f, 0.046875f),
			new Vec2f(1f, 1f - 0.046875f),
			new Vec2f(1f - 0.046875f, 1f),
			new Vec2f(0.046875f, 1f),
			new Vec2f(0f, 1f - 0.046875f),
			new Vec2f(0f, 0.046875f)
		};
	};
	
}
