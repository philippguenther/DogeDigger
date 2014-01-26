import org.lwjgl.opengl.GL11;


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
		float d = 0.1f;
		Align align = this.pos.align(a.pos); 
		if (align==Align.NORTH || align==Align.NORTH_EAST || align==Align.NORTH_WEST)
			d = -d;
		if (align==Align.WEST || align==Align.NORTH_WEST || align==Align.SOUTH_WEST)
			d = -d;

		this.ani = new Animator(this, Animation.LINEAR, a.pos.copy(), d, d);
		a.ani = new Animator(a,Animation.LINEAR, this.pos.copy() , -d, -d);
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
		
		if (clicked) {
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
