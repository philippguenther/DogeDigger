
public class Color {
	private float r;
	private float g;
	private float b;
	private float a;
	
	public Color(float _r, float _g, float _b, float _a) {
		this.r = cap(_r);
		this.g = cap(_g);
		this.b = cap(_b);
		this.a = cap(_a);
	}
	
	public Color(float _r, float _g, float _b) {
		this(_r, _g, _b, 1f);
	}

	public boolean equals(Color c) {
		if (Math.abs(this.r - c.getR()) <= Config.getColorTolerance())
			if (Math.abs(this.g - c.getG()) <= Config.getColorTolerance())
				if (Math.abs(this.b - c.getB()) <= Config.getColorTolerance())
					return true;
		return false;
	}
	
	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}

	public float getA() {
		return a;
	}

	private float cap(float _i) {
		if (_i <= 0f)
			return 0f;
		else if (_i >= 1f)
			return 1f;
		else
			return _i;
	}
	
	public static Color red() {
		return new Color(0.8f, 0f, 0f);
	}
	public static Color brightRed() {
		return new Color(1f, 0.5f, 0.5f);
	}
	public static Color green() {
		return new Color(0f, 0.8f, 0f);
	}
	public static Color brightGreen() {
		return new Color(0.5f, 1f, 0.5f);
	}
	public static Color blue() {
		return new Color(0f, 0f, 0.8f);
	}
	public static Color brightBlue() {
		return new Color(0.5f, 0.5f, 1f);
	}
}
