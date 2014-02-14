import java.util.ArrayList;

import org.lwjgl.opengl.GL11;


public class EntityBox implements Entity {
	private Level level;
	
	private Vec2f position;
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	private EntityBoxType type;
	private boolean active = false;
	private Mover mover;
	
	private int deltaDecay = 0;
	
	public EntityBox (Level _level, Vec2f _position, EntityBoxType _type) {
		this.level = _level;
		this.position = _position;
		this.type = _type;
		
		this.graphics.add(GraphicFactory.newBox());
		switch(this.type) {
		case RED:
			this.graphics.add(1, GraphicFactory.newBoxRed());
			break;
		case GREEN:
			this.graphics.add(1, GraphicFactory.newBoxGreen());
			break;
		case BLUE:
			this.graphics.add(1, GraphicFactory.newBoxBlue());
			break;
		default:
			this.graphics.add(1, GraphicFactory.newBoxYellow());
		}
		
		this.level.put(this);
	}
	
	@Override
	public void destroy () {
		this.graphics.clear();
		this.level.remove(this.position);
		Entity top = this.level.get(new Vec2f(this.position.x, this.position.y - 1));
		if (top != null)
			top.fall();
		
		ArrayList<Entity> list = this.level.getDestroyField(this.position);
		for (Entity e : list) {
			if (e instanceof EntityBox) {
				EntityBox b = (EntityBox) e;
				if (b.getType() == this.type)
					b.destroy();
			}
		}
	}
	
	public EntityBoxType getType() {
		return this.type;
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
		if (!this.active) {
			this.active = true;
			this.graphics.add(1, GraphicFactory.newBoxActive());
		}	
	}
	
	@Override
	public void deactivate() {
		if (this.active) {
		this.active = false;
		if (this.graphics.size() > 1)
			this.graphics.remove(1);
		}
	}
	
	@Override
	public void fall() {
		boolean b = true;
		for (Entity e : this.level.getDestroyField(this.position)) {
			if (e instanceof EntityBox) {
				EntityBox box = (EntityBox) e;
				if (box.getType() == this.type)
					b = false;
			}
		}
		if (b) {
			this.mover = new MoverLinear(new Vec2f(0f, 1f), Math.round(100 * (1 / this.level.getGravity())) );
			Entity top = this.level.get(new Vec2f(this.position.x, this.position.y - 1));
			if (top != null) {
				top.activate();
			}
		}
	}
	
	@Override
	public void tick(int delta) {
		
		// check for mover
		if (this.mover != null) {
			if (this.mover.ready()) {
				// if mover is ready remove it
				this.mover = null;
			} else {
				// if mover != null move
				this.level.remove(this);
				this.position.add(this.mover.getVecDelta(delta));
				this.level.put(this);
			}
		}
		
		// make sure position is integer
		if (this.mover == null) {
			this.position.round();
		}
		
		//FALLING
		this.deltaDecay += delta;
		if (this.mover == null && this.active && this.deltaDecay > Config.boxDecay) {
			Entity bot = this.level.get(new Vec2f(this.position.x, this.position.y + 1f));
			if (bot == null) {
				this.fall();
			}
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

enum EntityBoxType {
	RED,
	GREEN,
	BLUE,
	YELLOW
}
