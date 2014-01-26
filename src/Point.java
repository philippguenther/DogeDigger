
public class Point {
	public float x;
	public float y;
	
	public Point(float _x, float _y) {
		this.x = _x;
		this.y = _y;
	}
	
	public Point copy() {
		return new Point(this.x, this.y);
	}
	
	public boolean equals(Point p) {
		if (this.xEquals(p.x) && this.yEquals(p.y)) return true;
		else return false;
	}
	
	public boolean xEquals(float _x) {
		if (Math.abs(this.x - _x) < 0.0001f) return true;
		else return false;
	}
	
	public boolean yEquals(float _y) {
		if (Math.abs(this.y - _y) < 0.0001f) return true;
		else return false;
	}
	
	public Align align(Point p) {
		if (this.x > p.x) {
			if (this.y > p.y) {
				return Align.SOUTH_WEST;
			} else if (this.yEquals(p.y)) {
				return Align.WEST;
			} else {
				return Align.NORTH_WEST;
			}
		} else if (this.xEquals(p.x)) {
			if (this.y > p.y) {
				return Align.NORTH;
			} else {
				return Align.SOUTH;
			}
		} else {
			if (this.y > p.y) {
				return Align.SOUTH_EAST;
			} else if (this.yEquals(p.y)) {
				return Align.EAST;
			} else {
				return Align.NORTH_EAST;
			}
		}
	}
	
	public float distance(Point p) {
		return (float) Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2) );
	}
}

enum Align {
	NORTH,
	NORTH_WEST,
	WEST,
	SOUTH_WEST,
	SOUTH,
	SOUTH_EAST,
	EAST,
	NORTH_EAST
}