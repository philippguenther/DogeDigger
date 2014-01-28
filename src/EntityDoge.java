import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;


public class EntityDoge implements Entity {
	private Body body;
	private Graphic graphic;
	
	public Sensor sensor;
	
	public EntityDoge(Vec2 _pos, Graphic _g) {
		// main body
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DYNAMIC;
		bdef.position = _pos;
		bdef.fixedRotation = true;
		bdef.bullet = true;
		this.body = DogeDriller.getGame().getLevel().getWorld().createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.5f, 0.5f);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 2.0f;
		fdef.friction = 0f;
		fdef.userData = this;
		this.body.createFixture(fdef);
		
		this.graphic = _g;
	}
	
	public void setSensor(Sensor s) {
		this.sensor = s;
	}
	
	public Vec2 getPosition() {
		return this.body.getPosition();
	}

	public Body getBody() {
		return this.body;
	}

	public Graphic getGraphic() {
		return this.graphic;
	}
	
	public boolean isOnGround() {
		if (this.sensor != null) {
			for (Entity e : this.sensor.getContacts()) {
				if (!(e instanceof EntityDoge))
					return true;
			}
		}
		return false;
	}
	
	public void input() {
		if (this.isOnGround()) {
			if (Keyboard.isKeyDown(Config.keyLeft)) {
				this.getBody().setLinearVelocity(new Vec2(-2f, -0.1f));
			} else if (Keyboard.isKeyDown(Config.keyLeft) && Keyboard.isKeyDown(Config.keySpace)) {
				this.getBody().setLinearVelocity(new Vec2(-2f, -5f));
			} else if (Keyboard.isKeyDown(Config.keyRight)) {
				this.getBody().setLinearVelocity(new Vec2(2f, -0.1f));
			} else if (Keyboard.isKeyDown(Config.keyRight) && Keyboard.isKeyDown(Config.keySpace)) {
				this.getBody().setLinearVelocity(new Vec2(2f, -5f));
			} else if (Keyboard.isKeyDown(Config.keySpace)) {
				this.getBody().applyLinearImpulse(new Vec2(0f, -6.5f), this.getBody().getPosition());
			} else {
				this.getBody().setLinearVelocity(new Vec2(0, 0));
			}
		}
	}

	public void tick(int delta) {
		
	}

	public void render() {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.body.getPosition().x, this.body.getPosition().y, 0f);
			this.graphic.render();
		GL11.glPopMatrix();
	}
	
}
