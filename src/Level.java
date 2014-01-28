import java.util.HashMap;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.lwjgl.input.Keyboard;


public class Level {
	private HashMap<Vec2, Entity> entities = new HashMap<Vec2, Entity>();
	
	private World world;
	private EntityDoge doge;
	
	private float depth;
	
	public Level() {
		this.world = new World(Config.getGravity());
	}
	
	public void setDoge(EntityDoge d) {
		this.doge = d;
	}
	
	public void setHeight(float _h) {
		this.depth = _h;
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Config.getBoxesX() / 2, 0.5f);
		Graphic g = new GraphicQuad(Config.getBoxesX(), 1f, new Color3f(1f, 1f, 1f));
		this.addEntity(new EntityStatic(new Vec2(Config.getBoxesX() / 2, this.depth + 0.5f), shape, g));
	}
	
	public void addEntity(Entity e) {
		this.entities.put(e.getPosition(), e);
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public void input() {
		if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
			this.entities.clear();
			this.world = new World(Config.getGravity());
			LevelFactory.randomLevel(this);
		}
		this.doge.input();
	}
	
	public void tick(int delta) {
		this.world.step(1f / 60f, 2, 6);
		
		for (Entity e : this.entities.values()) {
			e.tick(delta);
		}
		this.doge.tick(delta);
	}
	
	public void render() {
		for (Entity e : this.entities.values()) {
			e.render();
		}
		this.doge.render();
	}
}
