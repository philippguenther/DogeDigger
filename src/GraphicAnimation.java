import java.util.Arrays;


public class GraphicAnimation implements Graphic {
	private Graphic[] frames;
	private int[] delays;
	
	private int current = 0;
	private int delta = 0;
	
	public GraphicAnimation (Graphic[] _frames, int[] _delays) {
		this.frames = _frames;
		this.delays = Arrays.copyOf(_delays, _frames.length);
	}

	@Override
	public void render(int delta) {
		this.delta += delta;
		if (this.delta > this.delays[this.current]) {
			this.current = (this.current + 1) % this.frames.length;
			this.delta = 0;
		}
		
		this.frames[this.current].render(delta);
	}

}
