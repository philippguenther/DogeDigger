import org.lwjgl.opengl.GL11;
import animations.*;


public class Box {
	public Point pos;
	private Color color;
	
	public Animator ani;
	
	public boolean clicked;
	
	public Box(Point _pos, Color _color) {
		this.pos = _pos;
		this.color = _color;
	}
	
	public void swap(Box a) {
		float d = 0.16667f;
		if (this.pos.isEast(a.pos) || this.pos.isSouth(a.pos))
			d = -d;
		this.ani = new Animator(this, new Linear(d), a.pos.copy());
		a.ani = new Animator(a, new Linear(-d), this.pos.copy());
	}
	
	public boolean isNear(Box a) {
		if (this.pos.distance(a.pos) < 1.0001f)
			return true;
		else
			return false;
	}
	
	public void tick(int delta) {
		if (this.ani != null) {
			if (this.ani.disposable()) {
				this.ani = null;
			} else {
				this.ani.tick(delta);
			}
		}
	}
	
	public void render() {
		GL11.glColor4f(this.color.getR(), this.color.getG(), this.color.getB(), this.color.getA());
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(this.pos.x, this.pos.y);
			GL11.glVertex2f(this.pos.x + 1f, this.pos.y);
			GL11.glVertex2f(this.pos.x + 1f, this.pos.y + 1f);
			GL11.glVertex2f(this.pos.x, this.pos.y + 1f);
		GL11.glEnd();
		
		if (this.clicked) {
			GL11.glColor4f(0f, 0f, 0f, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(this.pos.x, this.pos.y);
				GL11.glVertex2f(this.pos.x + 1f, this.pos.y);
				GL11.glVertex2f(this.pos.x + 1f, this.pos.y + 1f);
				GL11.glVertex2f(this.pos.x, this.pos.y + 1f);
			GL11.glEnd();
		}
	}
}
