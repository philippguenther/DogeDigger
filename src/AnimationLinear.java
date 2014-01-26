
public class AnimationLinear implements Animation {
	private Point target;
	private Point dest;
	private float d;
	
	public AnimationLinear(Point _target, Point _dest, float _d) {
		this.target = _target;
		this.dest = _dest;
		this.d = _d;
	}
	
	public void tick(int delta) {
		if (!this.target.xEquals(dest.x))
			this.target.x += this.d;
		if (!this.target.yEquals(dest.y))
			this.target.y += this.d;
	}
	
	public boolean disposable() {
		return this.target.equals(dest);
	}
}