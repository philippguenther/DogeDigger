import graphics.Graphic;

import org.lwjgl.opengl.GL11;

import util.Vec2i;


public class EntityStatic implements Entity {
	private Level level;
	
	private Vec2i position;
	private Graphic graphic;
	
	private int hits;
	
	public EntityStatic(Level _level, Vec2i _position) {
		this.level = _level;
		
		this.position = _position.clone();
		this.graphic = GraphicFactory.ENTITY_STATIC;
	}
	
	@Override
	public Vec2i getPosition() {
		return this.position;
	}

	@Override
	public void setPosition(Vec2i _position) {
		this.position = _position;
	}
	
	@Override
	public void takeHit() {
		this.hits++;
		if (this.hits > 3)
			this.destroy();
	}

	@Override
	public void destroy() {
		this.graphic = null;
		this.level.remove(this.position);
		Entity top = this.level.get(new Vec2i(this.position.x, this.position.y - 1));
		if (top != null)
			top.activate();
	}
	
	@Override
	public void activate() {
		
	}
	
	@Override
	public void deactivate() {
		
	}
	
	@Override
	public boolean isActive() {
		return false;
	}
	
	@Override
	public boolean readyToFall() {
		return false;
	}
	
	@Override
	public void moveDown() {
		//this.mover = new MoverLinear(new Vec2f(1f, 0f), Math.round(1f * Config.boxMove * (1 / this.level.getGravity())) );
	}

	@Override
	public void tick(int delta) {
		this.graphic.tick(delta);
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
		GL11.glTranslatef(this.position.x, this.position.y, 0f);
		this.graphic.render();
	GL11.glPopMatrix();
	}

}
