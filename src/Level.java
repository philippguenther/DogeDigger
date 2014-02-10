import java.util.HashMap;


public class Level {
	public Doge doge;
	private HashMap<String,Box> boxes = new HashMap<String,Box>();
	
	private Vec2f gravity = new Vec2f(0f, 1f);
	
	public Level () {
		
	}
	
	public void setGravity (Vec2f _gravity) {
		this.gravity = _gravity;
	}
	
	public Vec2f getGravity () {
		return this.gravity;
	}
	
	public void put (Box box) {
		String key = (int)box.getPosition().x + "|" + (int)box.getPosition().y;
		this.boxes.put(key, box);
	}
	
	public Box get (String key) {
		return this.boxes.get(key);
	}
	
	public void remove (String key) {
		this.boxes.remove(key);
	}
	
	public void tick (int delta) {
		for (Box b : this.boxes.values()) {
			b.tick(delta);
		}
		this.doge.tick(delta);
	}
	
	public void render (int delta) {
		for (Box b : this.boxes.values()) {
			b.render(delta);
		}
		this.doge.render(delta);
	}
}
