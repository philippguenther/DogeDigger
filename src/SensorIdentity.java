
public class SensorIdentity {
	private Entity entity;
	private Direction direction;
	
	public SensorIdentity(Entity e, Direction d) {
		this.entity = e;
		this.direction = d;
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	public Direction getDirection() {
		return this.direction;
	}
}

enum Direction {
	TOP,
	RIGHT,
	BOTTOM,
	LEFT
}
