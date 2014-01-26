
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
	
	public Point minus(Point p) {
		return new Point(this.x - p.x, this.y - y);
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
	
	public boolean isWest(Point p) {
		if (!this.xEquals(p.x) && this.x < p.x) return true;
		else return false;
	}
	
	public boolean isEast(Point p) {
		if (!this.xEquals(p.x) && this.x > p.x) return true;
		else return false;
	}
	
	public boolean isNorth(Point p) {
		if (!this.yEquals(p.y) && this.y < p.y) return true;
		else return false;
	}
	
	public boolean isSouth(Point p) {
		if (!this.yEquals(p.y) && this.y > p.y) return true;
		else return false;
	}
	
	public Align align(Point p) {
		if (this.isNorth(p)) {
			if (this.isEast(p))
				return Align.NORTH_EAST;
			else if (this.isWest(p))
				return Align.NORTH_WEST;
			else
				return Align.NORTH;
		} else if (this.isSouth(p)) {
			if (this.isEast(p))
				return Align.SOUTH_EAST;
			else if (this.isWest(p))
				return Align.SOUTH_WEST;
			else
				return Align.SOUTH;
		} else
			if (this.isEast(p))
				return Align.EAST;
			else if (this.isWest(p))
				return Align.WEST;
			else
				return Align.CENTER;
	}
	
	public float distance(Point p) {
		return (float) Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2) );
	}
}

enum Align {
	CENTER,
	NORTH,
	NORTH_WEST,
	WEST,
	SOUTH_WEST,
	SOUTH,
	SOUTH_EAST,
	EAST,
	NORTH_EAST
}