import java.util.HashMap;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.lwjgl.input.Keyboard;


public class Level {
	private HashMap<Vec2, Entity> entities = new HashMap<Vec2, Entity>();
	
	private World world;
	private Doge doge;
	
	private float depth;
	
	public Level() {
		this.world = new World(Config.getGravity());
	}
	
	public void setDoge(Doge d) {
		this.doge = d;
	}
	
	public void setHeight(float _h) {
		this.depth = _h;
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Config.getBoxesX() / 2, 0.5f);
		this.addEntity(new EntityStatic(new Vec2(Config.getBoxesX() / 2, this.depth + 0.5f), shape, new GraphicQuad(Config.getBoxesX(), 1f, new Color3f(1f, 1f, 1f))));
	}
	
	public void addEntity(Entity e) {
		this.entities.put(e.getPosition(), e);
	}
	
	public World getWorld() {
		return this.world;
	}
	
	public void input() {
		while (Keyboard.next()) {
			
			//move left
			if (Keyboard.getEventKey() == Config.keyLeft) {
				this.doge.getBody().applyLinearImpulse(new Vec2(-1f, 0f), this.doge.getBody().getPosition());
				
			//move right
			} else if (Keyboard.getEventKey() == Config.keyRight) {
				this.doge.getBody().applyLinearImpulse(new Vec2(1f, 0f), this.doge.getBody().getPosition());
				
			//jump
			} else if (Keyboard.getEventKey() == Config.keySpace && this.doge.onGround) {
				this.doge.getBody().applyLinearImpulse(new Vec2(0f, -10f), this.doge.getBody().getPosition());
				
			//reset
			} else if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
				this.entities.clear();
				this.world = new World(Config.getGravity());
				LevelFactory.randomLevel(this);
			}
		}
		
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
