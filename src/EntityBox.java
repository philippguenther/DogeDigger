import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.lwjgl.opengl.GL11;


public class EntityBox implements Entity {
	public Vec2 position;
	public BodyDef body;
	public Graphic graphic;
	
	public Vec2 getPosition() {
		return this.position;
	}
	
	public BodyDef getBody() {
		return this.body;
	}
	
	public Graphic getGraphic() {
		return this.graphic;
	}

	public EntityBox(Vec2 _pos, BodyDef _body, Graphic _graphic) {
		this.position = _pos;
		this.body = _body;
		this.graphic = _graphic;
	}

	public void tick(int delta) {
		// TODO Auto-generated method stub
		
	}

	public void render() {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x, this.position.y, 0f);
			this.graphic.render();
		GL11.glPopMatrix();
	}

}
