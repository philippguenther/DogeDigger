import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.contacts.Contact;
import org.lwjgl.opengl.GL11;


public class EntityStatic implements Entity {
	private Body body;
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	private float width;
	private float height;
	
	public EntityStatic(Vec2 _pos, float w, float h) {
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.STATIC;
		bdef.position = _pos;
		bdef.allowSleep = true;
		this.body = DogeDriller.getGame().getLevel().getWorld().createBody(bdef);
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(w / 2, h / 2);
		fdef.friction = 0f;
		fdef.shape = shape;
		fdef.userData = this;
		this.body.createFixture(fdef);
		
		this.width = w;
		this.height = h;
		
		this.graphics.add(new GraphicQuad(this.width, this.height, new Color3f(1f, 0f, 0f)));
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endContact(Contact arg0) {
		// TODO Auto-generated method stub
		
	}

}
