import org.jbox2d.common.Color3f;
import org.lwjgl.opengl.GL11;


public class GraphicQuad implements Graphic {
	private Color3f color;
	private float width;
	private float height;
	
	public GraphicQuad() {
		this.width = 1f;
		this.height = 1f;
		this.color = new Color3f(0.5f, 0.5f, 0.5f);
	}
	
	public GraphicQuad(Color3f _col) {
		this.width = 1f;
		this.height = 1f;
		this.color = _col;
	}
	
	public GraphicQuad(float _w, float _h, Color3f _col) {
		this.width = _w;
		this.height = _h;
		this.color = _col;
	}
	
	
	public void tick(int delta) {
		
	}

	public void render() {
		float x = this.width / 2;
		float y = this.height / 2;
		GL11.glColor3f(this.color.x, this.color.y, this.color.z);
		GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glVertex2f(-x, -y);
			GL11.glVertex2f(x, -y);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(-x, y);
		GL11.glEnd();
	}

}
