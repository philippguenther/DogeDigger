import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.lwjgl.opengl.GL11;


public class EntityStatic implements Entity {
	public Body body;
	public Graphic graphic;
	
	public EntityStatic(Vec2 _pos, Shape _shape, Graphic _graphic) {
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.STATIC;
		bdef.position = _pos;
		this.body = DogeDriller.getGame().getLevel().getWorld().createBody(bdef);
		body.createFixture(_shape, 1f);
		
		this.graphic = _graphic;
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
