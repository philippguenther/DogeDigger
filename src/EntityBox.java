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
	public EntityBox top = null;
	public EntityBox right = null;
	public EntityBox bottom = null;
	public EntityBox left = null;
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	
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
		fdef.density = 5f;
		fdef.friction = 1000f;
		fdef.restitution = 0f;
		fdef.userData = this;
		this.body.createFixture(fdef);
		this.graphics.add(new GraphicQuad(1, 1));
		
		// sensor fdef
		FixtureDef sensfdef = new FixtureDef();
		sensfdef.density = 0f;
		sensfdef.friction = 0f;
		sensfdef.isSensor = true;
		float d = 0.05f;
		
		// top fixture
		PolygonShape topshape = new PolygonShape();
		topshape.setAsBox(d, d, new Vec2(0f, -0.5f), 0);
		sensfdef.shape = topshape;
		sensfdef.userData = new SensorIdentity(this, Direction.TOP);
		this.body.createFixture(sensfdef);
		this.graphics.add(new GraphicQuad(2 * d, 2 * d, Config.getSensorColor(), new Vec2(0f, -0.5f)));
		
		// right fixture
		PolygonShape rightshape = new PolygonShape();
		rightshape.setAsBox(d, d, new Vec2(0.5f, 0f), 0);
		sensfdef.shape = rightshape;
		sensfdef.userData = new SensorIdentity(this, Direction.RIGHT);
		this.body.createFixture(sensfdef);
		this.graphics.add(new GraphicQuad(2 * d, 2 * d, Config.getSensorColor(), new Vec2(0.5f, 0f)));
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
		if (this.body.getLinearVelocity().y < 0.1f)
			this.body.setType(BodyType.STATIC);
		else
			this.body.setType(BodyType.DYNAMIC);
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

	@Override
	public void beginContact(Contact arg0) {
		Object a = arg0.getFixtureA().getUserData();
		Object b = arg0.getFixtureB().getUserData();
		
		EntityBox ent = null;
		SensorIdentity sens = null;
		if (a instanceof EntityBox && b instanceof SensorIdentity) {
			ent = (EntityBox) a;
			sens = (SensorIdentity) b;
		} else if (b instanceof EntityBox && a instanceof SensorIdentity) {
			ent = (EntityBox) b;
			sens = (SensorIdentity) a;
		} else {
			return;
		}
		if (sens.getEntity() instanceof EntityBox && sens.getEntity() == this) {
			if (sens.getDirection() == Direction.TOP) {
				this.top = ent;
				this.top.bottom = this;
			} else if (sens.getDirection() == Direction.RIGHT) {
				this.right = ent;
				this.right.left = this;
			}
		}	
	}

	@Override
	public void endContact(Contact arg0) {
		Object a = arg0.getFixtureA().getUserData();
		Object b = arg0.getFixtureB().getUserData();
		
		EntityBox ent = null;
		SensorIdentity sens = null;
		if (a instanceof EntityBox && b instanceof SensorIdentity) {
			ent = (EntityBox) a;
			sens = (SensorIdentity) b;
		} else if (b instanceof EntityBox && a instanceof SensorIdentity) {
			ent = (EntityBox) b;
			sens = (SensorIdentity) a;
		} else {
			return;
		}
		if (sens.getEntity() instanceof EntityBox && sens.getEntity() == this) {
			if (sens.getDirection() == Direction.TOP && this.top != null) {
				this.top.bottom = null;
				this.top = null;
			} else if (sens.getDirection() == Direction.RIGHT && this.right != null) {
				this.right.left = null;
				this.right = null;
			}
		}
	}
	
	public void print() {
		System.out.println("t:" + this.top + ", r:" + this.right + ", b:" + this.bottom + ", l:" + this.left);
	}

}