
public class Animator {
	private Point dest;
	
	private float dx;
	private float dy;
	
	private Animation ani;
	
	private Box box;
	
	public Animator(Box _box, Animation _ani, Point _dest, float _dx, float _dy) {
		this.box = _box;
		this.ani = _ani;
		this.dest = _dest;
		this.dx = _dx;
		this.dy = _dy;
	}
	
	public void tick(int delta) {
		if (!this.box.pos.xEquals(dest.x)) {
			switch (this.ani) {
				case LINEAR:
					this.box.pos.x += dx;
					break;
			}
		}
		if (!this.box.pos.yEquals(dest.y)) {
			switch (this.ani) {
				case LINEAR:
					this.box.pos.y += dy;
					break;
			}
		}
	}
	
	public boolean disposable() {
		return this.box.pos.equals(dest);
	}
}

enum Animation {
	LINEAR
}