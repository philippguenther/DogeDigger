
public class MoverLinear implements Mover {
	private Vec2f move;
	private int duration;
	
	private Vec2f todo;
	private int deltaSum = 0;
	
	public MoverLinear (Vec2f _move, int _duration) {
		this.move = _move.clone();
		this.todo = _move.clone();
		this.duration = _duration;
	}
	
	@Override
	public Vec2f getVecDelta (int delta) {
		this.deltaSum += delta;
		
		if (this.deltaSum < this.duration) {
			float dx = (this.move.x / this.duration) * delta;
			float dy = (this.move.y / this.duration) * delta;
			
			if (Math.abs(dx) > Math.abs(this.todo.x))
				dx = this.todo.x;
			if (Math.abs(dy) > Math.abs(this.todo.y))
				dy = this.todo.y;
			
			
			Vec2f d = new Vec2f(dx, dy);
			this.todo.sub(d);
			return d;
		} else {
			Vec2f d = this.todo.clone();
			this.todo = Vec2f.nil();
			return d;
		}
	}

	@Override
	public boolean ready () {
		if (this.deltaSum > this.duration)
			return true;
		else
			return false;
	}
	
	
}
