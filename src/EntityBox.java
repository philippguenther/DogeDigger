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
				b.graphics[2] = GraphicFactory.ENTITY_BOX_ACTIVE;
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
	public void moveX(int _d) {
		this.mover = new MoverLinear(new Vec2f(Math.round(_d), 0f), Math.round(_d * Config.boxMove * (1 / this.level.getGravity())) );
	}
	
	@Override
	public void moveY(int _d) {
		this.mover = new MoverLinear(new Vec2f(0f, Math.round(_d)), Math.round(_d * Config.boxMove * (1 / this.level.getGravity())) );
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
		
		
		/*if (this.deltaDecay > Config.boxDecay) {
			Entity bot = this.level.get(new Vec2i(this.position.x, this.position.y + 1));
			if (bot == null)
				return true;
			if (bot instanceof EntityBox) {
				EntityBox b = (EntityBox) bot;
				if (b.getType() == this.type)
					return true;
			}
		}
		return false;*/
	}
	
	public ArrayList<EntityBox> getBond() {
		ArrayList<EntityBox> bond = new ArrayList<EntityBox>();
		bond.add(this);
		return this.getBond(bond);
	}
	
	private ArrayList<EntityBox> getBond(ArrayList<EntityBox> bond) {
		Entity[] list = this.level.getEntitiesConnected(this.position);
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
				
				if (this.level.get(new Vec2i(this.position.x, this.position.y + 1)) == null)
					this.deltaDecay += delta;
				
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
					// sort bond first by y coord so it looks much nicer
					EntityBox[] bondArray = new EntityBox[bond.size()];
					bond.toArray(bondArray);
					for (int i = bondArray.length; i > 0; i--) {
						for (int j = 0; j < bondArray.length - 1; j++) {
							if (bondArray[j].getPosition().y < bondArray[j + 1].getPosition().y) {
								EntityBox tmp = bondArray[j];
								bondArray[j] = bondArray[j + 1];
								bondArray[j + 1] = tmp;
							}
						}
					}
					
					for (EntityBox eb : bondArray) {
						eb.moveY(1);
						eb.deltaDecay = delta;
					}
				}
				
			}
		}
	}
	
	@Override
	public void render(int delta) {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x + this.offset.x, this.position.y + this.offset.y, 0f);
			for (Graphic g : this.graphics) {
				if (g != null)
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
