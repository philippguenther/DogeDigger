import java.util.ArrayList;

import org.lwjgl.opengl.GL11;


public class Box {
	private Level level;
	
	private Vec2f position;
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	
	public Box (Level _level, Vec2f _position) {
		this.level = _level;
		this.position = _position;
		Vec2f[] v = new Vec2f[4];
		v[0] = new Vec2f(0f, 0f);
		v[1] = new Vec2f(1f, 0f);
		v[2] = new Vec2f(1f, 1f);
		v[3] = new Vec2f(0f, 1f);
		this.graphics.add(new GraphicPolygon(v, new Color4f(1f, 1f, 0f)));
	}
	
	public void destroy () {
		this.graphics.clear();
		this.level.remove(this.position.x + "|" + this.position.y);
	}
	
	public void addGraphic (Graphic _graphic) {
		this.graphics.add(_graphic);
	}
	
	public Vec2f getPosition () {
		return this.position;
	}
	
	public void tick (int delta) {
		
	}
	
	public void render (int delta) {
		GL11.glPushMatrix();
		GL11.glTranslatef(this.position.x, this.position.y, 0f);
		for (Graphic g : this.graphics) {
			g.render(delta);
		}
		GL11.glPopMatrix();
	}
}
