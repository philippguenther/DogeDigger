import java.util.HashMap;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;


public class Level implements ContactListener {
	private HashMap<Vec2, Entity> entities = new HashMap<Vec2, Entity>();
	
	private World world;
	private EntityDoge doge;
	
	private float depth;
	
	public Level() {
		this.world = new World(Config.getGravity());
		this.world.setContactListener(this);
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
	
	public void tick(int delta) {
		this.world.step(1f / 60f, 10, 20);
		
		this.doge.tick(delta);
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

	@Override
	public void beginContact(Contact arg0) {
		this.doge.beginContact(arg0);
		for (Entity e : this.entities.values()) {
			e.beginContact(arg0);
		}
		
	}

	@Override
	public void endContact(Contact arg0) {
		this.doge.endContact(arg0);
		for (Entity e : this.entities.values()) {
			e.endContact(arg0);
		}
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}
}
