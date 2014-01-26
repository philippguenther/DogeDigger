package animations;

public class Linear implements Animation {
	private float d;
	
	public Linear(float _d) {
		this.d = _d;
	}

	@Override
	public float getDx(int delta) {
		return this.d;
	}

	@Override
	public float getDy(int delta) {
		return this.d;
	}
	
}