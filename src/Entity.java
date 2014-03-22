import util.Vec2i;


public interface Entity {
	public Vec2i getPosition();
	public void setPosition(Vec2i _position);
	
	public void takeHit();
	public void destroy();
	
	public void activate();
	public void deactivate();
	public boolean isActive();
	
	public boolean readyToFall();
	
	public void moveX(int _d);
	public void moveY(int _d);
	
	public void tick(int delta);
	public void render(int delta);
}
