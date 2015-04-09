import graphics.Graphic;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import util.Vec2f;
import util.Vec2i;


public class EntityBox implements Entity {
	private Level level;
	
	private Vec2i position;
	private Vec2f offset = new Vec2f(0f, 0f);
	private Mover mover = null;
	private Graphic[] graphics = new Graphic[3];
	private EntityBoxType type;
	private boolean active = false;
	
	private int deltaDecay = 0;
	
	public EntityBox(Level _level, Vec2i _position, EntityBoxType _type) {
		this.level = _level;
		this.position = _position;
		this.type = _type;
		
		this.graphics[0] = GraphicFactory.ENTITY_BOX.clone();
		switch(this.type) {
		case RED:
			this.graphics[1] = GraphicFactory.ENTITY_BOX_RED.clone();
			break;
		case GREEN:
			this.graphics[1] = GraphicFactory.ENTITY_BOX_GREEN.clone();
			break;
		case BLUE:
			this.graphics[1] = GraphicFactory.ENTITY_BOX_BLUE.clone();
			break;
		default:
			this.graphics[1] = GraphicFactory.ENTITY_BOX_YELLOW.clone();
		}
	}
	
	@Override
	public void takeHit() {
		this.destroy();
	}
	
	@Override
	public void destroy() {
		// destroy connecting boxes of same type
		ArrayList<EntityBox> list = this.getBond();
		for (EntityBox b : list) {
			this.level.remove(b.getPosition());
			b.position = null;
			b.offset = null;
			b.graphics = null;
			b.active = false;
			b.mover = null;
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
		this.position = _position;
	}
	
	@Override
	public void activate() {
		for (EntityBox b : this.getBond()) {
			if (!b.active) {
				b.active = true;
				//b.graphics[2] = GraphicFactory.ENTITY_BOX_ACTIVE;
			}
		}
	}
	
	@Override
	public void deactivate() {
		this.active = false;
		this.deltaDecay = 0;
		this.graphics[2] = null;
	}
	
	@Override
	public boolean isActive() {
		return this.active;
	}
	
	@Override
	public void moveDown() {
		this.mover = new MoverLinear(new Vec2f(0f, 1f), Math.round(Config.boxMove * (1 / this.level.getGravity())) );
		Entity top = this.level.get(new Vec2i(this.position.x, this.position.y - 1));
		if (top != null && top instanceof EntityBox) {
			EntityBox eb = (EntityBox) top;
			eb.deltaDecay = Config.boxDecay;
		}
	}
	
	@Override
	public boolean readyToFall() {
		Entity botEnt = this.level.get(new Vec2i(this.position.x, this.position.y + 1));
		if (botEnt == null && this.deltaDecay > Config.boxDecay) {
			return true;
		} else if (botEnt instanceof EntityBox) {
			EntityBox botBox = (EntityBox)botEnt;
			if (botBox.getType() == this.type) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public ArrayList<EntityBox> getBond() {
		ArrayList<EntityBox> bond = new ArrayList<EntityBox>();
		bond.add(this);
		return this.getBond(bond);
	}
	
	private ArrayList<EntityBox> getBond(ArrayList<EntityBox> bond) {
		Entity[] list = this.level.getVonNeumannNeighbors(this.position);
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
		for (Graphic g : this.graphics)
			if (g != null)
				g.tick(delta);
		
		if (this.mover != null) {
			if (this.mover.disposable()) {
				// if mover is ready remove it
				this.mover = null;
			} else {
				// if mover != null move
				this.offset.add(this.mover.getVecDelta(delta));
			}
		} else {
			
			// offset must not be greater one
			if (this.offset.x > 0.99) {
				this.position.x += 1;
				this.offset.x -= 1;
			} else if (this.offset.x < -0.99) {
				this.position.x -= 1;
				this.offset.x += 1;
			}
			if (this.offset.y > 0.99) {
				this.position.y += 1;
				this.offset.y -= 1;
			} else if (this.offset.y < -0.99) {
				this.position.y -= 1;
				this.offset.y += 1;
			}
			
			if (this.active) {
				
				Entity bot = this.level.get(new Vec2i(this.position.x, this.position.y + 1));
				if (bot == null) {
					this.deltaDecay += delta;
				}
				
				// check to fall
				ArrayList<EntityBox> bond = this.getBond();
				boolean ready = true;
				for (Entity i : bond) {
					if (!i.readyToFall()) {
						ready = false;
						break;
					}
				}
				
				if (ready) {
					for (EntityBox eb : bond) {
						eb.moveDown();
						eb.deltaDecay = delta;
					}
				}
				
			}
		}
	}
	
	@Override
	public void render() {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x + this.offset.x, this.position.y + this.offset.y, 0f);
			for (Graphic g : this.graphics) {
				if (g != null)
					g.render();
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
