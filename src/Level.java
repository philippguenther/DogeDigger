import java.util.ArrayList;

import org.lwjgl.opengl.GL11;


public class Level {
	public Doge doge;
	private float gravity = 1f;
	private Entity[][] entities = new Entity[Config.levelMaxX][Config.levelMaxY];
	
	public float scroll = 0f;
	
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
			return this.entities[(int)(v.x)][(int)(v.y)];
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
	
	public ArrayList<Entity> getActivationField(Vec2f p) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		
		for (int x = Math.round(p.x) - 1; x < Math.round(p.x) + 2; x++) {
			for (int y = Math.round(p.y) - 1; y < Math.round(p.y) + 2; y++) {
				Entity e = this.get(new Vec2f(x, y));
				if (e != null)
					list.add(e);
			}
		}
		
		return list;
	}
	
	public ArrayList<Entity> getDestroyField(Vec2f p) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		
		Entity t = this.get(new Vec2f(p.x, p.y - 1));
		if (t != null)
			list.add(t);
		Entity r = this.get(new Vec2f(p.x + 1, p.y));
		if (r != null)
			list.add(r);
		Entity b = this.get(new Vec2f(p.x, p.y + 1));
		if (b != null)
			list.add(b);
		Entity l = this.get(new Vec2f(p.x - 1, p.y));
		if (l != null)
			list.add(l);
		
		return list;
	}
	
	public void tick (int delta) {
		this.scroll = this.doge.getPosition().y - Config.gameBoxesY / 2;
		
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
			GL11.glTranslatef(0f, -this.scroll, 0f);
			
			int y0 = (int) this.scroll;
			int y1 = y0 + Config.gameBoxesY + 2;
			
			for (int x = 0; x < Config.gameBoxesX && x < Config.levelMaxX; x++) {
				for (int y = Math.max(y0, 0); y < y1 && y < Config.levelMaxY; y++) {
					if (this.entities[x][y] != null)
						this.entities[x][y].render(delta);
				}
			}
			
			this.doge.render(delta);
		GL11.glPopMatrix();
	}
}
