package graphics;
import java.util.Arrays;


public class GraphicAnimation implements Graphic {
	private Graphic[] frames;
	private int[] delays;
	
	private int current = 0;
	private int delta = 0;
	
	private boolean disposable = false;
	
	private GraphicAnimation (Graphic[] _frames, int[] _delays, int _current, int _delta) {
		this.frames = _frames;
		this.delays = Arrays.copyOf(_delays, _frames.length);
		this.current = _current;
		this.delta = _delta;
	}
	
	public GraphicAnimation (Graphic[] _frames, int[] _delays) {
		this.frames = _frames;
		this.delays = Arrays.copyOf(_delays, _frames.length);
	}
	
	@Override
	public void flipX() {
		for (Graphic g : this.frames) {
			g.flipX();
		}
	}

	@Override
	public void unflipX() {
		for (Graphic g : this.frames) {
			g.unflipX();
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
	public void tick(int delta) {
		this.delta += delta;
		if (this.delta > this.delays[this.current]) {
			this.current = (this.current + 1) % this.frames.length;
			this.delta = delta;
		}
		if (this.current == this.frames.length - 1) {
			this.disposable = true;
		}
	}

	@Override
	public void render() {
		this.frames[this.current].render();
	}
	
	@Override
	public Graphic clone() {
		Graphic[] clonedFrames = new Graphic[this.frames.length];
		int i = 0;
		while (i < this.frames.length) {
			clonedFrames[i] = this.frames[i].clone();
			i++;
		}
		return new GraphicAnimation(clonedFrames, this.delays.clone(), this.current, this.delta);
	}
	
	@Override
	public void destroy() {
		for (Graphic g : this.frames)
			g.destroy();
		this.frames = null;
		this.delays = null;
	}
}
