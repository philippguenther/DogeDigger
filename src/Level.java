import java.util.HashMap;

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
	
	public void setDepth(float d) {
		this.depth = d;
		
		this.addEntity(new EntityStatic(new Vec2(Config.getBoxesX() / 2, -0.5f), Config.getBoxesX() + 2f, 1f));
		this.addEntity(new EntityStatic(new Vec2(Config.getBoxesX() / 2, this.depth + 0.5f), Config.getBoxesX() + 2f, 1f));
		this.addEntity(new EntityStatic(new Vec2(-0.5f, this.depth / 2), 1f, this.depth));
		this.addEntity(new EntityStatic(new Vec2(Config.getBoxesX() + 0.5f, this.depth / 2), 1f, this.depth));
	}
	
	public void addEntity(Entity e) {
		this.entities.put(e.getPosition(), e);
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public void input() {
		if (Keyboard.isKeyDown(Config.keyReset)) {
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
