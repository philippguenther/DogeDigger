
public class Vec2f {
	public float x;
	public float y;
	
	public Vec2f (float _x, float _y) {
		this.x = _x;
		this.y = _y;
	}
	
	public Vec2f clone ()  {
		return new Vec2f(this.x, this.y);
	}
}