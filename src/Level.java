import org.lwjgl.opengl.GL11;


public class Level {
	public Doge doge;
	private float gravity = 1f;
	private Box[][] boxes = new Box[Config.levelMaxX][Config.levelMaxY];
	
	public Vec2f view = new Vec2f(0f, 0f);
	
	public Level () {
		
	}
	
	public void setGravity (float _gravity) {
		this.gravity = _gravity;
	}
	
	public float getGravity () {
		return this.gravity;
	}
	
	public void put (Box box) {
		this.boxes[Math.round(box.getPosition().x)][Math.round(box.getPosition().y)] = box;
	}
	
	public Box get (Vec2f v) {
		return this.boxes[Math.round(v.x)][Math.round(v.y)];
	}
	
	public void remove (Box box) {
		this.boxes[Math.round(box.getPosition().x)][Math.round(box.getPosition().y)] = null;
	}
	
	public void tick (int delta) {
		//this.view.y -= 0.0005 * delta;
		
		for (Box[] bv : this.boxes) {
			for (Box bi : bv) {
				if (bi != null)
					bi.tick(delta);
			}
		}
		this.doge.tick(delta);
	}
	
	public void render (int delta) {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.view.x, this.view.y, 0f);
			
			int x0 = (int) Math.floor(this.view.x);
			int x1 = x0 + Config.boxesX + 1;
			int y0 = (int) Math.floor(this.view.y);
			int y1 = y0 + Config.boxesY + 1;
			
			for (int x = x0; x < x1 && x < Config.levelMaxX; x++) {
				for (int y = x0; y < y1 && y < Config.levelMaxY; y++) {
					if (this.boxes[x][y] != null)
						this.boxes[x][y].render(delta);
				}
			}
			
			this.doge.render(delta);
		GL11.glPopMatrix();
	}
}
