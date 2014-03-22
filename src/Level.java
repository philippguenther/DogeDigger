import org.lwjgl.opengl.GL11;

import util.Vec2f;
import util.Vec2i;


public class Level {
	public Doge doge;
	private float gravity = Config.levelStandardGravity;
	private Entity[][] entities = new Entity[Config.levelMaxX][Config.levelMaxY];
	
	public Vec2f scroll = new Vec2f(0f, 0f);
	
	public void setGravity(float _gravity) {
		this.gravity = _gravity;
	}
	
	public float getGravity () {
		return this.gravity;
	}
	
	public void insert(Vec2i v, Entity box) {
		this.entities[v.x][v.y] = box;
	}
	
	public Entity get(Vec2i v) {
		if (v.x >= 0 && v.x < Config.levelMaxX && v.y >= 0 && v.y < Config.levelMaxY) {
			Vec2i location = this.locate(v);
			if (location != null)
				return this.entities[location.x][location.y];
			else
				return null;
		} else {
			return new EntityStatic(this, v.clone());
		}
	}
	
	public void remove(Vec2i v) {
		if (v.x >= 0 && v.x < Config.levelMaxX && v.y >= 0 && v.y < Config.levelMaxY) {
			Vec2i location = this.locate(v);
			this.entities[location.x][location.y] = null;
		}
	}
	
	private Vec2i locate(Vec2i v) {
		// this only works because we know entities can only fall down
		int i = v.y;
		Entity e = null;
		while (i >= 0) {
			e = this.entities[v.x][i];
			if (e != null && v.equals(e.getPosition()))
				return new Vec2i(v.x, i);
			i--;
		}
		return null;
	}
	
	public Entity[] getRow(int y) {
		Entity[] row = new Entity[Config.levelMaxX];
		for (int x = 0; x < Config.levelMaxX; x++)
			row[x] = this.get(new Vec2i(x, y));
		return row;
	}
	
	public Entity[] getEntitiesInRadius(Vec2i p, int r) {
		Entity[] list = new Entity[1 + r * 8];
		int i = 0;
		for (int x = p.x - r; x <= p.x + r; x++) {
			for (int y = p.y - r; y <= p.y + r; y++) {
				list[i] = this.get(new Vec2i(x, y));
				i++;
			}
		}
		return list;
	}
	
	public Entity[] getEntitiesConnected(Vec2i p) {
		Entity[] list = new Entity[4];
		list[0] = this.get(new Vec2i(p.x, p.y - 1));
		list[1] = this.get(new Vec2i(p.x + 1, p.y));
		list[2] = this.get(new Vec2i(p.x, p.y + 1));
		list[3] = this.get(new Vec2i(p.x - 1, p.y));
		return list;
	}
	
	public void tick(int delta) {
		this.scroll.y = this.doge.getPosition().y + this.doge.getOffset().y - Config.windowBoxesY / 2;
		
		for (Entity[] es : this.entities)
			for (Entity e : es)
				if (e != null)
					e.tick(delta);
		
		this.doge.tick(delta);
	}
	
	public void render(int delta) {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.scroll.x, -this.scroll.y, 0f);
			
			int x0 = (int) Math.floor(this.scroll.x);
			int x1 = x0 + Config.windowBoxesX;
			
			int y0 = (int) Math.floor(this.scroll.y);
			int y1 = y0 + Config.windowBoxesY + 2;
			
			for (int x = x0; x < x1; x++) {
				for (int y = y0; y < y1; y++) {
					Entity e = this.get(new Vec2i(x, y));
					if (e != null)
						e.render(delta);
				}
			}
			
			this.doge.render(delta);
		GL11.glPopMatrix();
	}
}
