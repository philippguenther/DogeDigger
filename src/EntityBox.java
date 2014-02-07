import java.util.ArrayList;

import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
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
		
		FixtureDef fdef = new FixtureDef();
		fdef.density = 2.5f;
		fdef.friction = 100f;
		fdef.restitution = 0f;
		
		Vec2[] v = new Vec2[4];
		v[0] = new Vec2(-0.5f, -0.5f);
		v[1] = new Vec2(0.5f, -0.5f);
		v[2] = new Vec2(0.5f, 0.5f);
		v[3] = new Vec2(-0.5f, 0.5f);
		PolygonShape s = new PolygonShape();
		s.set(v, v.length);
		fdef.shape = s;
		fdef.userData = this;
		this.body.createFixture(fdef);
		this.graphics.add(new GraphicPolygon(v, new Color3f(1f, 1f, 0)));
	}
	
	public void fall() {
		this.body.setType(BodyType.DYNAMIC);
		if (this.top != null)
			this.top.fall();
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
			if (this.bottom != null) {
				this.body.setType(BodyType.STATIC);
			}
		} else {
			if (this.bottom == null) {
				if (this.timerDecay > 1000) {
					// let the box fall
					this.fall();
					this.timerDecay = 0;
				} else {
					this.timerDecay += delta;
				}
			}
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
		
		if (a instanceof EntityBox && b instanceof EntityBox) {
			EntityBox e0 = (EntityBox) a;
			EntityBox e1 = (EntityBox) b;
			Manifold m = arg0.getManifold();
			
			Vec2 vel1 = e0.body.getLinearVelocityFromWorldPoint( m.points[0].localPoint );
			Vec2 vel2 = e1.body.getLinearVelocityFromWorldPoint( m.points[0].localPoint );
			Vec2 impact = vel1.subLocal(vel2);
		}
	}

	@Override
	public void endContact(Contact arg0) {
		
	}
	
	public void print() {
		System.out.println("t:" + this.top + ", r:" + this.right + ", b:" + this.bottom + ", l:" + this.left);
	}

}