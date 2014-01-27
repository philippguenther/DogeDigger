import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.lwjgl.opengl.GL11;


public class Doge implements Entity {
	private Body body;
	private Graphic graphic;
	
	public boolean onGround = true;
	
	public Doge(Vec2 _pos, Graphic _g) {
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DYNAMIC;
		bdef.position = _pos;
		bdef.fixedRotation = true;
		bdef.allowSleep = false;
		this.body = DogeDriller.getGame().getLevel().getWorld().createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.5f, 0.5f);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 2.0f;
		fdef.friction = 0.5f;
		body.createFixture(fdef);
		this.graphic = _g;
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

	public void tick(int delta) {
		
	}

	public void render() {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.body.getPosition().x, this.body.getPosition().y, 0f);
			this.graphic.render();
		GL11.glPopMatrix();
	}
	
}
