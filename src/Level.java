import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import util.Vec2i;


public class Level {
	public Doge doge;
	private float gravity = Config.levelStandardGravity;
	private Entity[][] entities = new Entity[Config.gameBoxesX][Config.levelMaxY];
	
	public float scroll = 0f;
	
	public void setGravity(float _gravity) {
		this.gravity = _gravity;
	}
	
	public float getGravity () {
		return this.gravity;
	}
	
	public void insert(Entity box) {
		if (this.entities[box.getPosition().x][box.getPosition().y] != null)
			System.out.println("WARNING: " + box.getPosition().x + "|" + box.getPosition().y + " already occupied!");
		this.entities[box.getPosition().x][box.getPosition().y] = box;
	}
	
	public Entity get(Vec2i v) {
		if (v.x > -1 && v.x < Config.gameBoxesX && v.y > -1 && v.y < Config.levelMaxY)
			return this.entities[v.x][v.y];
		else
			return new EntityStatic(this, v.clone());
	}
	
	public void remove(Vec2i v) {
		if (v.x > -1 && v.x < Config.gameBoxesX && v.y > -1 && v.y < Config.levelMaxY)
			this.entities[v.x][v.y] = null;
	}
	
	public void remove(Entity box) {
		this.remove(box.getPosition());
	}
	
	public Entity getEntityBeneath(Vec2i p) {
		if (this.get(p) != null) {
			return this.get(p);
		} else {
			return getEntityBeneath(Vec2i.add(p, new Vec2i(0, 1)));
		}
	}
	
	public ArrayList<Entity> getEntitiesInRadius(Vec2i p, int r) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		
		for (int x = Math.round(p.x) - r; x <= Math.round(p.x) + r; x++) {
			for (int y = Math.round(p.y) - r; y <= Math.round(p.y) + r; y++) {
				Entity e = this.get(new Vec2i(x, y));
				if (e != null)
					list.add(e);
			}
		}
		
		return list;
	}
	
	public ArrayList<Entity> getEntitiesConnected(Vec2i p) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		
		Entity t = this.get(new Vec2i(p.x, p.y - 1));
		if (t != null)
			list.add(t);
		Entity r = this.get(new Vec2i(p.x + 1, p.y));
		if (r != null)
			list.add(r);
		Entity b = this.get(new Vec2i(p.x, p.y + 1));
		if (b != null)
			list.add(b);
		Entity l = this.get(new Vec2i(p.x - 1, p.y));
		if (l != null)
			list.add(l);
		
		return list;
	}
	
	public void tick(int delta) {
		this.scroll = this.doge.getPosition().y + this.doge.getOffset().y - Config.gameBoxesY / 2;
		
		for (Entity[] bv : this.entities) {
			for (Entity bi : bv) {
				if (bi != null)
					bi.tick(delta);
			}
		}
		this.doge.tick(delta);
	}
	
	public void render(int delta) {
		GL11.glPushMatrix();
			GL11.glTranslatef(0f, -this.scroll, 0f);
			
			int y0 = (int) Math.floor(this.scroll);
			int y1 = y0 + Config.gameBoxesY + 2;
			
			for (int x = 0; x < Config.gameBoxesX; x++) {
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
