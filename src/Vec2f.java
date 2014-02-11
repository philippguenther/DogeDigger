
public class Vec2f {
	public float x;
	public float y;
	
	public Vec2f (float _x, float _y) {
		this.x = _x;
		this.y = _y;
	}
	
	public void add (Vec2f a) {
		this.x += a.x;
		this.y += a.y;
	}
	
	public void sub (Vec2f a) {
		this.x -= a.x;
		this.y -= a.y;
	}
	
	public float len () {
		return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	
	public float dist (Vec2f a) {
		return (float) Math.sqrt(Math.pow(this.x - a.x, 2) + Math.pow(this.y - a.y, 2));
	}
	
	public Vec2f clone ()  {
		return new Vec2f(this.x, this.y);
	}
	
	
	// STATIC
	public static Vec2f nil () {
		return new Vec2f(0f, 0f);
	}
	
	public static Vec2f add (Vec2f a, Vec2f b) {
		return new Vec2f(a.x + b.x, a.y + b.y);
	}
	
	public static Vec2f sub (Vec2f a, Vec2f b) {
		return new Vec2f(a.x - b.x, a.y - b.y);
	}
	
	public static float dist (Vec2f a, Vec2f b) {
		return (float) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}
}