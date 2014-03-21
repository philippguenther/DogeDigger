package graphics;
import java.util.Arrays;


public class GraphicAnimation implements Graphic {
	private Graphic[] frames;
	private int[] delays;
	
	private int current = 0;
	private int delta = 0;
	
	private boolean disposable = false;
	
	public GraphicAnimation (Graphic[] _frames, int[] _delays) {
		this.frames = _frames;
		this.delays = Arrays.copyOf(_delays, _frames.length);
	}
	
	@Override
	public void flip() {
		for (Graphic g : this.frames) {
			g.flip();
		}
	}

	@Override
	public void unflip() {
		for (Graphic g : this.frames) {
			g.unflip();
		}
	}
	
	@Override
	public void reset() {
		this.current = 0;
		this.delta = 0;
		this.disposable = false;
	}
	
	@Override
	public boolean disposable() {
		return this.disposable;
	}

	@Override
	public void render(int delta) {
		if (this.current == this.frames.length - 1) {
			this.disposable = true;
		}
		
		this.delta += delta;
		if (this.delta > this.delays[this.current]) {
			this.current = (this.current + 1) % this.frames.length;
			this.delta = 0;
		}
		
		this.frames[this.current].render(delta);
	}
	
	@Override
	public void destroy() {
		for (Graphic g : this.frames)
			g.destroy();
		this.frames = null;
		this.delays = null;
	}
}
