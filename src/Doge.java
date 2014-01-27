import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.lwjgl.opengl.GL11;


public class Doge implements Entity {
	private Vec2 position;
	private BodyDef body;
	private Graphic graphic;
	
	public Doge(Vec2 _pos, Graphic _g) {
		this.position = _pos;
		this.graphic = _g;
	}
	
	public Vec2 getPosition() {
		return this.position;
	}

	public BodyDef getBody() {
		return this.body;
	}

	public Graphic getGraphic() {
		return this.graphic;
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
