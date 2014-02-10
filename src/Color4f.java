
public class Color4f {
	public float r;
	public float g;
	public float b;
	public float a;
	
	public Color4f (float _r, float _g, float _b, float _a) {
		this.r = _r;
		this.g = _g;
		this.b = _b;
		this.a = _a;
	}
	
	public Color4f (float _r, float _g, float _b) {
		this(_r, _g, _b, 1f);
	}
}
