import org.lwjgl.opengl.GL11;


public class Box {
	public float x;
	public float y;
	private Color color;
	
	public Box(int _x, int _y, Color _color) {
		this.x = _x;
		this.y = _y;
		this.color = _color;
	}
	
	public void render(int _delta) {
		GL11.glColor4f(this.color.getR(), this.color.getG(), this.color.getB(), this.color.getA());
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(this.x, this.y);
			GL11.glVertex2f(this.x + 1f, this.y);
			GL11.glVertex2f(this.x + 1f, this.y + 1f);
			GL11.glVertex2f(this.x, this.y + 1f);
		GL11.glEnd();
	}
}
