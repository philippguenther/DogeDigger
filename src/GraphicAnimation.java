import java.util.Arrays;


public class GraphicAnimation implements Graphic {
	private Graphic[] frames;
	private int[] delays;
	
	private int current = 0;
	private int delay = 0;
	
	public GraphicAnimation (Graphic[] _frames, int[] _delays) {
		this.frames = _frames;
		this.delays = Arrays.copyOf(_delays, _frames.length);
	}

	@Override
	public void render(int delta) {
		this.delay += delta;
		if (this.delay > this.delays[this.current]) {
			this.current = (this.current + 1) % this.frames.length;
			this.delay = 0;
		}
		
		this.frames[this.current].render(delta);
	}

}
