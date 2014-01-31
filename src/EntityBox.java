import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;
import org.lwjgl.opengl.GL11;


public class EntityBox implements Entity {
	private Body body;
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	
	private int timerDecay = 0;
	
	public EntityBox top = null;
	public EntityBox right = null;
	public EntityBox bottom = null;
	public EntityBox left = null;
	
	public EntityBox(Vec2 _pos) {
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DYNAMIC;
		bdef.position = _pos;
		bdef.allowSleep = true;
		bdef.fixedRotation = true;
		this.body = DogeDriller.getGame().getLevel().getWorld().createBody(bdef);
		
		// main fixture
		FixtureDef fdef = new FixtureDef();
		PolygonShape mainshape = new PolygonShape();
		mainshape.setAsBox(0.49f, 0.49f);
		fdef.shape = mainshape;
		fdef.density = 10f;
		fdef.friction = 100f;
		fdef.restitution = 0f;
		fdef.userData = this;
		this.body.createFixture(fdef);
		this.graphics.add(new GraphicQuad(0.98f, 0.98f));
		
		// sensor fdef
		FixtureDef sensfdef = new FixtureDef();
		sensfdef.density = 0f;
		sensfdef.friction = 0f;
		sensfdef.isSensor = true;
		
		// top sensor
		PolygonShape topshape = new PolygonShape();
		topshape.setAsBox(0.05f, 0.001f, new Vec2(0f, -0.5f), 0);
		sensfdef.shape = topshape;
		sensfdef.userData = new SensorIdentity(this, Direction.TOP);
		this.body.createFixture(sensfdef);
		this.graphics.add(new GraphicQuad(0.1f, 0.002f, Config.getSensorColor(), new Vec2(0f, -0.50f)));
		
		// right sensor
		PolygonShape rightshape = new PolygonShape();
		rightshape.setAsBox(0.001f, 0.05f, new Vec2(0.5f, 0f), 0);
		sensfdef.shape = rightshape;
		sensfdef.userData = new SensorIdentity(this, Direction.RIGHT);
		this.body.createFixture(sensfdef);
		this.graphics.add(new GraphicQuad(0.002f, 0.1f, Config.getSensorColor(), new Vec2(0.52f, 0f)));
		
		// bottom sensor
		PolygonShape botshape = new PolygonShape();
		botshape.setAsBox(0.05f, 0.001f, new Vec2(0f, 0.5f), 0);
		sensfdef.shape = botshape;
		sensfdef.userData = new SensorIdentity(this, Direction.BOTTOM);
		this.body.createFixture(sensfdef);
		this.graphics.add(new GraphicQuad(0.1f, 0.002f, Config.getSensorColor(), new Vec2(0f, 0.5f)));
		
		// right sensor
		PolygonShape leftshape = new PolygonShape();
		leftshape.setAsBox(0.001f, 0.05f, new Vec2(-0.5f, 0f), 0);
		sensfdef.shape = leftshape;
		sensfdef.userData = new SensorIdentity(this, Direction.LEFT);
		this.body.createFixture(sensfdef);
		this.graphics.add(new GraphicQuad(0.002f, 0.1f, Config.getSensorColor(), new Vec2(-0.5f, 0f)));
	}
	
	public Vec2 getPosition() {
		return this.body.getPosition();
	}
	
	public Body getBody() {
		return this.body;
	}
	
	public ArrayList<Graphic> getGraphics() {
		return this.graphics;
	}

	public void tick(int delta) {
		if (this.body.getType() == BodyType.DYNAMIC) {
			if (this.bottom != null && this.bottom.getBody().getLinearVelocity().y < 0.01f) {
				this.body.setType(BodyType.STATIC);
			}
		} else {
			if (this.bottom == null) {
				if (this.timerDecay > 1000) {
					// let the box fall
					this.body.setType(BodyType.DYNAMIC);
					this.timerDecay = 0;
				} else {
					this.timerDecay += delta;
				}
			}
		}
		
		// check if neighbours are still there
		if (this.bottom != null && this.bottom.getBody().getLinearVelocity().y > 0.01f) {
			this.bottom = null;
		}
		if (this.right != null && Math.abs(this.getPosition().y - this.right.getPosition().y) > 0.1f) {
			this.right = null;
		}
		if (this.left != null && Math.abs(this.getPosition().y - this.left.getPosition().y) > 0.1f) {
			this.left = null;
		}
		
	}

	public void render() {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.body.getPosition().x, this.body.getPosition().y, 0f);
			GL11.glRotatef((float) (this.body.getAngle() * (360 / (2 * Math.PI))), 0f, 0f, 1f);
			for (Graphic g : this.graphics) {
				g.render();
			}
		GL11.glPopMatrix();
	}
	
	public void destroy() {
		if (this.top != null)
			this.top.bottom = null;
		if (this.right != null)
			this.right.left = null;
		if (this.bottom != null)
			this.bottom.top = null;
		if (this.left != null)
			this.left.right = null;
		this.body.destroyFixture(this.body.getFixtureList());
		DogeDriller.getGame().getLevel().getWorld().destroyBody(this.body);
		this.graphics.clear();
	}

	@Override
	public void beginContact(Contact arg0) {
		Object a = arg0.getFixtureA().getUserData();
		Object b = arg0.getFixtureB().getUserData();
		
		SensorIdentity s0 = null;
		SensorIdentity s1 = null;
		EntityBox e0 = null;
		EntityBox e1 = null;
		if (a instanceof SensorIdentity && b instanceof SensorIdentity) {
			s0 = (SensorIdentity) a;
			s1 = (SensorIdentity) b;
			if (s0.getEntity() instanceof EntityBox && s1.getEntity() instanceof EntityBox) {
				e0 = (EntityBox) s0.getEntity();
				e1 = (EntityBox) s1.getEntity();
			} else {
				return;
			}
		} else {
			return;
		}
		
		if (e0 == this && s1.getDirection() == Direction.TOP) {
			this.bottom = e1;
		} else if (e0 == this && s1.getDirection() == Direction.RIGHT) {
			this.left = e1;
		} else if (e0 == this && s1.getDirection() == Direction.BOTTOM) {
			this.top = e1;
		} else if (e0 == this && s1.getDirection() == Direction.LEFT) {
			this.right = e1;
		} else if (e1 == this && s0.getDirection() == Direction.TOP) {
			this.bottom = e0;
		} else if (e1 == this && s0.getDirection() == Direction.RIGHT) {
			this.left = e0;
		} else if (e1 == this && s0.getDirection() == Direction.BOTTOM) {
			this.top = e0;
		} else if (e1 == this && s0.getDirection() == Direction.LEFT) {
			this.right = e0;
		}
	}

	@Override
	public void endContact(Contact arg0) {
		
	}
	
	public void print() {
		System.out.println("t:" + this.top + ", r:" + this.right + ", b:" + this.bottom + ", l:" + this.left);
	}

}