
public class AnimationParable implements Animation {
	private Point target;
	private Point dest;
	private float d;
	private Point start;
	
	public AnimationParable(Point _target, Point _dest, float _d) {
		this.target = _target;
		this.dest = _dest;
		this.d = _d;
		this.start = _target.copy();
	}
	
	public void tick(int delta) {
		if (!this.target.equals(dest)) {
			this.target.x += d;
			this.target.y = y();
		}
	}
	
	public boolean disposable() {
		return this.target.equals(dest);
	}
	
	private float y() {
		return 2f * (float) (Math.pow(this.target.x - (this.start.x + (this.dest.x - this.start.x) / 2), 2)) + this.start.x + 0.5f;
	}
}
