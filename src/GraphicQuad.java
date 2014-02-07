import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.lwjgl.opengl.GL11;


public class GraphicQuad implements Graphic {
	private Color3f color;
	private Vec2 offset = new Vec2(0, 0);
	private float width;
	private float height;
	
	public GraphicQuad(float w, float h, Color3f _col) {
		this.width = w;
		this.height = h;
		this.color = _col;
	}
	
	public GraphicQuad(float w, float h, Color3f _col, Vec2 _o) {
		this.width = w;
		this.height = h;
		this.color = _col;
		this.offset = _o;
	}
	
	
	public void tick(int delta) {
		
	}

	public void render() {
		float x = this.width / 2;
		float y = this.height / 2;
		GL11.glColor3f(this.color.x, this.color.y, this.color.z);
		GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glVertex2f(-x + this.offset.x, -y + this.offset.y);
			GL11.glVertex2f(x + this.offset.x, -y + this.offset.y);
			GL11.glVertex2f(x + this.offset.x, y + this.offset.y);
			GL11.glVertex2f(-x + this.offset.x, y + this.offset.y);
		GL11.glEnd();
	}

}
