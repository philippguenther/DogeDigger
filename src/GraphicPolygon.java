import org.lwjgl.opengl.GL11;


public class GraphicPolygon implements Graphic {
	private Color4f color;
	private Vec2f offset = new Vec2f(0f, 0f);
	private Vec2f[] vertices;

	public GraphicPolygon(Vec2f[] _vertices, Color4f _color) {
		this.vertices = _vertices;
		this.color = _color;
	}

	public GraphicPolygon(Vec2f[] _vertices, Color4f _color, Vec2f _offset) {
		this.vertices = _vertices;
		this.offset = _offset;
		this.color = _color;
	}

	public void tick(int delta) {

	}

	public void render(int delta) {
		GL11.glColor4f(this.color.r, this.color.g, this.color.b, this.color.a);
		GL11.glTranslatef(this.offset.x, this.offset.y, 0f);
		GL11.glBegin(GL11.GL_LINE_LOOP);
			for (Vec2f v : this.vertices) {
				GL11.glVertex2f(v.x + this.offset.x, v.y + this.offset.y);
			}
		GL11.glEnd();
	}

}