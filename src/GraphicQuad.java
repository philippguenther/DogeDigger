import org.jbox2d.common.Color3f;
import org.lwjgl.opengl.GL11;


public class GraphicQuad implements Graphic {
	private Color3f color;
	
	public GraphicQuad() {
		this.color = new Color3f(0.5f, 0.5f, 0.5f);
	}
	
	public GraphicQuad(Color3f _col) {
		this.color = _col;
	}
	
	
	public void tick(int delta) {
		// TODO Auto-generated method stub
		
	}

	public void render() {
		GL11.glColor3f(this.color.x, this.color.y, this.color.z);
		GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glVertex2f(0f, 0f);
			GL11.glVertex2f(1f, 0f);
			GL11.glVertex2f(1f, 1f);
			GL11.glVertex2f(0f, 1f);
		GL11.glEnd();
	}

}
