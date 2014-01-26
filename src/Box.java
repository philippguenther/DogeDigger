import org.lwjgl.opengl.GL11;


public class Box {
	public float x;
	public float y;
	private Color color;
	public boolean clicked;
	
	public Box(int _x, int _y, Color _color) {
		this.x = _x;
		this.y = _y;
		this.color = _color;
	}
	
	public void swapPos(Box a) {
		SwapBoxes.game.state = State.ANIMATING;
		float ax = a.x;
		float ay = a.y;
		a.x = this.x;
		a.y = this.y;
		this.x = ax;
		this.y = ay;
		SwapBoxes.game.state = State.INPUT;
	}
	
	public boolean areSwapable(Box a) {
		if (Math.abs(this.x - a.x) <= 1.1f && Math.abs(this.y - a.y) <= 1.1f)
			return true;
		else
			return false;
	}
	
	public void render(int _delta) {
		GL11.glColor4f(this.color.getR(), this.color.getG(), this.color.getB(), this.color.getA());
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(this.x, this.y);
			GL11.glVertex2f(this.x + 1f, this.y);
			GL11.glVertex2f(this.x + 1f, this.y + 1f);
			GL11.glVertex2f(this.x, this.y + 1f);
		GL11.glEnd();
		
		if (clicked) {
			GL11.glColor4f(0f, 0f, 0f, 0.5f);
			GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex2f(this.x, this.y);
				GL11.glVertex2f(this.x + 1f, this.y);
				GL11.glVertex2f(this.x + 1f, this.y + 1f);
				GL11.glVertex2f(this.x, this.y + 1f);
			GL11.glEnd();
		}
	}
}
