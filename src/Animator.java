
import animations.*;

public class Animator {
	private Point dest;
	private Animation ani;
	private Box box;
	
	public Animator(Box _box, Animation _ani, Point _dest) {
		this.box = _box;
		this.ani = _ani;
		this.dest = _dest;
	}
	
	public void tick(int delta) {
		float dx = this.ani.getDx(delta);
		float dy = this.ani.getDy(delta);
		
		if (!this.box.pos.xEquals(dest.x))
			this.box.pos.x += dx;
		if (!this.box.pos.yEquals(dest.y))
			this.box.pos.y += dy;
	}
	
	public boolean disposable() {
		return this.box.pos.equals(dest);
	}
}