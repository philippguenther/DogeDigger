
public class EntityStatic implements Entity {
	private Vec2f position;
	
	public EntityStatic (Vec2f _position) {
		this.position = _position;
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
		// don't give a fuck
	}

	@Override
	public void tick(int delta) {
		// don't give a fuck
	}

	@Override
	public void render(int delta) {
		// don't give a fuck
	}

}
