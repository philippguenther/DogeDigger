import org.lwjgl.opengl.GL11;


public class Level {
	public Doge doge;
	private float gravity = 1f;
	private Entity[][] entities = new Entity[Config.levelMaxX][Config.levelMaxY];
	
	public Vec2f view = new Vec2f(0f, 0f);
	
	public void setGravity (float _gravity) {
		this.gravity = _gravity;
	}
	
	public float getGravity () {
		return this.gravity;
	}
	
	public void put (Entity box) {
		this.entities[Math.round(box.getPosition().x)][Math.round(box.getPosition().y)] = box;
	}
	
	public Entity get (Vec2f v) {
		if (v.x > -1 && v.x < Config.levelMaxX && v.y > -1 && v.y < Config.levelMaxY)
			return this.entities[Math.round(v.x)][Math.round(v.y)];
		else
			return new EntityStatic(v.clone());
	}
	
	public void remove (Vec2f v) {
		if (v.x > -1 && v.x < Config.levelMaxX && v.y > -1 && v.y < Config.levelMaxY)
			this.entities[Math.round(v.x)][Math.round(v.y)] = null;
	}
	
	public void remove (Entity box) {
		this.remove(box.getPosition());
	}
	
	public void tick (int delta) {
		//this.view.y -= 0.0005 * delta;
		
		for (Entity[] bv : this.entities) {
			for (Entity bi : bv) {
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
					if (this.entities[x][y] != null)
						this.entities[x][y].render(delta);
				}
			}
			
			this.doge.render(delta);
		GL11.glPopMatrix();
	}
}
