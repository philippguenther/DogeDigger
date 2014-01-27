import java.util.HashMap;
import org.jbox2d.common.Vec2;
//import org.lwjgl.input.Mouse;


public class Level {
	private HashMap<Vec2, Entity> entities = new HashMap<Vec2, Entity>();
	private Doge doge;
	
	public Level(Doge _doge) {
		this.doge = _doge;
	}
	
	public void addEntity(Entity e) {
		this.entities.put(e.getPosition(), e);
	}
	
	public void input() {
		
	}
	
	public void tick(int delta) {
		for (Entity e : this.entities.values()) {
			e.tick(delta);
		}
	}
	
	public void render() {
		for (Entity e : this.entities.values()) {
			e.render();
		}
		this.doge.render();
	}
}
