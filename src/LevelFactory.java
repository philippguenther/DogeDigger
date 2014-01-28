import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;


public class LevelFactory {
	
	public static Level randomLevel(Level lvl) {
		EntityDoge doge = new EntityDoge(new Vec2(3.5f, 1.5f), new GraphicQuad(new Color3f(1f, 0f, 0f)));
		Sensor sensor = new Sensor(doge);
		doge.setSensor(sensor);
		lvl.getWorld().setContactListener(sensor);
		lvl.setDoge(doge);
		
		for (float i = 0.5f; i < 7; i++) {
			PolygonShape s = new PolygonShape();
			s.setAsBox(0.5f, 0.5f);
			lvl.addEntity(new EntityBox(new Vec2(i, 7), s, new GraphicQuad()));
		}
		lvl.setHeight(8);
		return lvl;
	}
}
