import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;


public class LevelFactory {
	
	public static Level randomLevel(Level lvl) {
		lvl.setDoge(new EntityDoge(new Vec2(3.5f, 1.5f)));
		lvl.setDepth(9f);
		for (float i = 0.5f; i < 7; i++) {
			PolygonShape s = new PolygonShape();
			s.setAsBox(0.49f, 0.49f);
			lvl.addEntity(new EntityBox(new Vec2(i, 7.5f), s));
		}
		return lvl;
	}
}
