import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.lwjgl.opengl.GL11;


public class GraphicPolygon implements Graphic {
	private Color3f color;
	private Vec2 offset = new Vec2(0, 0);
	private Vec2[] vertices;
	
	public GraphicPolygon(Vec2[] _vertices, Color3f _color) {
		this.vertices = _vertices;
		this.color = _color;
	}
	
	public GraphicPolygon(Vec2[] _vertices, Vec2 _offset, Color3f _color) {
		this.vertices = _vertices;
		this.offset = _offset;
		this.color = _color;
	}
	
	public void tick(int delta) {
		
	}

	public void render() {
		GL11.glColor3f(this.color.x, this.color.y, this.color.z);
		GL11.glBegin(GL11.GL_LINE_LOOP);
			for (Vec2 v : this.vertices) {
				GL11.glVertex2f(v.x + this.offset.x, v.y + this.offset.y);
			}
		GL11.glEnd();
	}

}
