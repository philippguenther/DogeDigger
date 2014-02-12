import java.util.ArrayList;

import org.lwjgl.opengl.GL11;


public class EntityBox implements Entity {
	private Level level;
	
	private Vec2f position;
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	private boolean active = false;
	private Mover mover;
	
	private int deltaDecay = 0;
	
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
	
	@Override
	public void destroy () {
		this.graphics.clear();
		this.level.remove(this.position);
		Entity top = this.level.get(new Vec2f(this.position.x, this.position.y - 1));
		if (top != null)
			top.fall();
	}
	
	@Override
	public void addGraphic (Graphic _graphic) {
		this.graphics.add(_graphic);
	}
	
	@Override
	public Vec2f getPosition() {
		return this.position;
	}
	
	@Override
	public void setPosition(Vec2f _position) {
		this.level.remove(this);
		this.position = _position;
		this.level.put(this);
	}
	
	@Override
	public void activate() {
		this.active = true;
	}
	
	@Override
	public void fall() {
		if (this.mover == null) {
			Entity bot = this.level.get(new Vec2f(this.position.x, this.position.y + 1f));
			if (bot == null) {
				this.mover = new MoverLinear(new Vec2f(0f, 1f), Math.round(100 * (1 / this.level.getGravity())) );
				Entity top = this.level.get(new Vec2f(this.position.x, this.position.y - 1));
				if (top != null) {
					top.fall();
				}
			}
		}
	}
	
	@Override
	public void tick(int delta) {
		
		// check for mover
		if (this.mover != null) {
			if (this.mover.ready()) {
				this.mover = null;
			} else {
				this.level.remove(this);
				this.position.add(this.mover.getVecDelta(delta));
				this.level.put(this);
			}
		}
		
		// make sure position is integer
		if (this.mover == null) {
			this.position.round();
		}
		
		if (!this.active) {
			if (this.graphics.size() > 1)
				this.graphics.remove(1);
			return;
		} else {
			this.graphics.add(1, new GraphicPolygon(GraphicFactory.box, new Color4f(1f, 0f, 0f, 0.5f)));
		}
		
		//FALLING
		this.deltaDecay += delta;
		if (this.mover == null && this.deltaDecay > Config.boxDecay) {
			this.fall();
		}
	}
	
	@Override
	public void render (int delta) {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x, this.position.y, 0f);
			for (Graphic g : this.graphics) {
				g.render(delta);
			}
		GL11.glPopMatrix();
	}
}
