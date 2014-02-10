
public interface Entity {
	public Vec2f getPosition();
	public void setPosition(Vec2f _position);
	public void destroy();
	public void addGraphic(Graphic g);
	public void tick(int delta);
	public void render(int delta);
}
