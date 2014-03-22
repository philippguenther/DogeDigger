package util;

public class Vec2i {
	public int x;
	public int y;
	
	public Vec2i(int _x, int _y) {
		this.x = _x;
		this.y = _y;
	}
	
	public boolean equals(Vec2i a) {
		if (this.x == a.x && this.y == a.y)
			return true;
		else
			return false;
	}
	
	public void add(Vec2i a) {
		this.x += a.x;
		this.y += a.y;
	}
	
	public void sub(Vec2i a) {
		this.x -= a.x;
		this.y -= a.y;
	}
	
	public float len() {
		return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	
	public float dist(Vec2i a) {
		return (float) Math.sqrt(Math.pow(this.x - a.x, 2) + Math.pow(this.y - a.y, 2));
	}
	
	public Vec2i clone()  {
		return new Vec2i(this.x, this.y);
	}
	
	
	// STATIC
	public static Vec2i nil() {
		return new Vec2i(0, 0);
	}
	
	public static Vec2i add(Vec2i a, Vec2i b) {
		return new Vec2i(a.x + b.x, a.y + b.y);
	}
	
	public static Vec2i sub(Vec2i a, Vec2i b) {
		return new Vec2i(a.x - b.x, a.y - b.y);
	}
	
	public static float dist(Vec2i a, Vec2i b) {
		return (float) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}
}