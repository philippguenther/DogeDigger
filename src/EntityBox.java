import java.util.ArrayList;

import org.lwjgl.opengl.GL11;


public class EntityBox implements Entity {
	private Level level;
	
	private Vec2f position;
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	
	public EntityBox (Level _level, Vec2f _position) {
		this.level = _level;
		this.position = _position;
		Vec2f[] v = new Vec2f[4];
		v[0] = new Vec2f(0f, 0f);
		v[1] = new Vec2f(1f, 0f);
		v[2] = new Vec2f(1f, 1f);
		v[3] = new Vec2f(0f, 1f);
		this.graphics.add(GraphicFactory.newBoxGraphic());
		this.level.put(this);
	}
	
	public void destroy () {
		this.graphics.clear();
		this.level.remove(this.position);
	}
	
	public void addGraphic (Graphic _graphic) {
		this.graphics.add(_graphic);
	}
	
	public Vec2f getPosition () {
		return this.position;
	}
	
	public void setPosition (Vec2f _position) {
		this.level.remove(this);
		this.position = _position;
		this.level.put(this);
	}
	
	public void tick (int delta) {
		
		//FALLING
		Entity bot = this.level.get(new Vec2f(this.position.x, this.position.y + 0.5f));
		if (bot == null || bot == this) {
			this.level.remove(this);
			this.position.y += this.level.getGravity() * 0.005f * delta;
			this.level.put(this);
		} else if (bot != this) {
			this.level.remove(this);
			this.position.y = bot.getPosition().y -1f;
			this.level.put(this);
		}
	}
	
	public void render (int delta) {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x, this.position.y, 0f);
			for (Graphic g : this.graphics) {
				g.render(delta);
			}
		GL11.glPopMatrix();
	}
}
