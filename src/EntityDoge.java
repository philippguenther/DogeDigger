import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;


public class EntityDoge implements Entity, ContactListener {
	private Body body;
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	private ArrayList<Entity> contacts = new ArrayList<Entity>();
	
	public EntityDoge(Vec2 _pos) {
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DYNAMIC;
		bdef.position = _pos;
		bdef.fixedRotation = true;
		bdef.bullet = true;
		this.body = DogeDriller.getGame().getLevel().getWorld().createBody(bdef);
		
		// main fixture
		PolygonShape mainshape = new PolygonShape();
		mainshape.setAsBox(0.3f, 0.4f);
		FixtureDef mainfdef = new FixtureDef();
		mainfdef.shape = mainshape;
		mainfdef.density = 10f;
		mainfdef.friction = 0f;
		this.body.createFixture(mainfdef);
		this.graphics.add(new GraphicQuad(0.6f, 0.8f, new Color3f(0f, 1f, 0f)));
		
		// foot fixture
		PolygonShape footshape = new PolygonShape();
		footshape.setAsBox(0.2f, 0.05f, new Vec2(0f, 0.4f), 0);
		FixtureDef footfdef = new FixtureDef();
		footfdef.shape = footshape;
		footfdef.density = 1f;
		footfdef.friction = 0f;
		footfdef.isSensor = true;
		footfdef.userData = this;
		this.body.createFixture(footfdef);
		this.graphics.add(new GraphicQuad(0.4f, 0.1f, new Color3f(0f, 1f, 0f), new Vec2(0f, 0.4f)));
		
		DogeDriller.getGame().getLevel().getWorld().setContactListener(this);
	}

	public ArrayList<Graphic> getGraphics() {
		return this.graphics;
	}
	
	public Vec2 getPosition() {
		return this.body.getPosition();
	}

	public Body getBody() {
		return this.body;
	}
	
	public boolean isOnGround() {
		for (Entity e : this.contacts) {
			if (!(e instanceof EntityDoge))
				return true;
		}
		return false;
	}
	
	public void input() {
		if (this.isOnGround()) {
			
			//left
			if (Keyboard.isKeyDown(Config.keyLeft)) {
				this.getBody().setLinearVelocity(new Vec2(-4f, -0.2f));
				
			// right
			} else if (Keyboard.isKeyDown(Config.keyRight)) {
				this.getBody().setLinearVelocity(new Vec2(4f, -0.2f));
			
			// stop moving
			} else {
				this.getBody().setLinearVelocity(new Vec2(0, 0));
			}
			
			// jump
			if (Keyboard.isKeyDown(Config.keySpace)) {
				this.getBody().applyLinearImpulse(new Vec2(0f, -23f), this.getBody().getPosition());
			}
		}
	}

	public void tick(int delta) {
		
	}

	public void render() {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.body.getPosition().x, this.body.getPosition().y, 0f);
			for (Graphic g : this.graphics) {
				g.render();
			}
		GL11.glPopMatrix();
	}

	@Override
	public void beginContact(Contact arg0) {
		Object a = arg0.getFixtureA().getUserData();
		Object b = arg0.getFixtureB().getUserData();
		Entity e = null;
		if (a == this && b instanceof Entity)
			e = (Entity) b;
		else if (b == this && a instanceof Entity)
			e = (Entity) a;
		if (e != null && !this.contacts.contains(e)) {
			this.contacts.add(e);
		}
	}

	@Override
	public void endContact(Contact arg0) {
		Object a = arg0.getFixtureA().getUserData();
		Object b = arg0.getFixtureB().getUserData();
		Entity e = null;
		if (a == this && b instanceof Entity)
			e = (Entity) b;
		else if (b == this && a instanceof Entity)
			e = (Entity) a;
		if (e != null) {
			this.contacts.remove(e);
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
