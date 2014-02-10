
public class GraphicFactory {
	
	private static Vec2f[] box = {
		new Vec2f(0f, 0f),
		new Vec2f(1f, 0f),
		new Vec2f(1f, 1f),
		new Vec2f(0f, 1f)
	};
	
	public static Graphic BOX =		new GraphicImage("Box.png");
	public static Graphic DOGE =	new GraphicPolygon(GraphicFactory.box, new Color4f(1f, 1f, 0f));
}
