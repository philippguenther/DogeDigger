import java.util.ArrayList;

import org.lwjgl.opengl.GL11;


public class EntityStatic implements Entity {
	private Vec2f position;
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	
	public EntityStatic (Vec2f _position) {
		this.position = _position;
		this.graphics.add(GraphicFactory.newBoxStaticGraphic());
	}
	
	@Override
	public Vec2f getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(Vec2f _position) {
		this.position = _position;
	}

	@Override
	public void destroy() {
		// don't give a fuck
	}

	@Override
	public void addGraphic(Graphic g) {
		this.graphics.add(g);
	}

	@Override
	public void tick(int delta) {
		
	}

	@Override
	public void render(int delta) {
		GL11.glPushMatrix();
		GL11.glTranslatef(this.position.x, this.position.y, 0f);
		for (Graphic g : this.graphics) {
			g.render(delta);
		}
	GL11.glPopMatrix();
	}

}
