import java.util.ArrayList;

import org.lwjgl.opengl.GL11;


public class EntityBox implements Entity {
	private Level level;
	
	private Vec2i position;
	private Vec2f offset = Vec2f.nil();
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	private EntityBoxType type;
	private boolean active = false;
	private Mover mover;
	
	private int deltaDecay = 0;
	
	public EntityBox (Level _level, Vec2i _position, EntityBoxType _type) {
		this.level = _level;
		this.position = _position;
		this.type = _type;
		
		this.graphics.add(GraphicFactory.newBox());
		switch(this.type) {
		case RED:
			this.graphics.add(GraphicFactory.newBoxRed());
			break;
		case GREEN:
			this.graphics.add(GraphicFactory.newBoxGreen());
			break;
		case BLUE:
			this.graphics.add(GraphicFactory.newBoxBlue());
			break;
		default:
			this.graphics.add(GraphicFactory.newBoxYellow());
		}
	}
	
	@Override
	public void destroy () {
		this.graphics.clear();
		this.level.remove(this.position);
		
		// destroy connecting boxes of same type
		ArrayList<Entity> list = this.level.getEntitiesConnected(this.position);
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
	public Vec2i getPosition() {
		return this.position;
	}
	
	@Override
	public void setPosition(Vec2i _position) {
		this.level.remove(this);
		this.position = _position;
		this.level.put(this);
	}
	
	@Override
	public void activate() {
		if (!this.active) {
			this.active = true;
			this.graphics.add(GraphicFactory.newBoxActive());
		}
		
		for (EntityBox i : this.getBond(new ArrayList<EntityBox>())) {
			if (!i.isActive())
				i.activate();
		}
	}
	
	@Override
	public boolean isActive() {
		return this.active;
	}
	
	@Override
	public void moveX(int _d) {
		this.mover = new MoverLinear(new Vec2f(Math.round(_d), 0f), Math.round(_d * Config.boxMove * (1 / this.level.getGravity())) );
	}
	
	@Override
	public void moveY(int _d) {
		this.mover = new MoverLinear(new Vec2f(0f, Math.round(_d)), Math.round(_d * Config.boxMove * (1 / this.level.getGravity())) );
	}
	
	@Override
	public boolean readyToFall() {
		if (this.deltaDecay > Config.boxDecay) {
			Entity bot = this.level.get(new Vec2i(this.position.x, this.position.y + 1));
			if (bot == null)
				return true;
			if (bot instanceof EntityBox) {
				EntityBox b = (EntityBox) bot;
				if (b.getType() == this.type)
					return true;
			}
		}
		return false;
	}
	
	public ArrayList<EntityBox> getBond(ArrayList<EntityBox> bond) {
		if (bond.indexOf(this) < 0)
			bond.add(this);
		
		ArrayList<Entity> list = this.level.getEntitiesConnected(this.position);
		for (Entity e : list) {
			if (e instanceof EntityBox) {
				EntityBox b = (EntityBox) e;
				if (!bond.contains(b) && b.getType() == this.type) {
					bond.add(b);
					b.getBond(bond);
				}
			}
		}
		
		return bond;
	}
	
	@Override
	public void tick(int delta) {
		if (this.mover != null) {
			if (this.mover.disposable()) {
				// if mover is ready remove it
				this.mover = null;
			} else {
				// if mover != null move
				this.offset.add(this.mover.getVecDelta(delta));
			}
		} else {
			this.level.remove(this);
			this.position.x += Math.round(this.offset.x);
			this.position.y += Math.round(this.offset.y);
			this.offset = Vec2f.nil();
			this.level.put(this);
			
			if (this.active)
				this.deltaDecay += delta;
			
			// check to fall
			if (this.active && this.readyToFall()) {
				ArrayList<EntityBox> blob = new ArrayList<EntityBox>();
				blob = this.getBond(blob);
				
				boolean ready = true;
				for (Entity i : blob) {
					if (!i.readyToFall()) {
						ready = false;
						break;
					}
				}
				
				if (ready) {
					for (EntityBox eb : blob) {
						eb.moveY(1);
						eb.deltaDecay = delta;
					}
				}
				
			}
		}
	}
	
	@Override
	public void render (int delta) {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x + this.offset.x, this.position.y + this.offset.y, 0f);
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
