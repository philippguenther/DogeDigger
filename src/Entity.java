import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;


public interface Entity {
	public Vec2 getPosition();
	public Body getBody();
	public ArrayList<Graphic> getGraphics();
	
	public void tick(int delta);
	public void render();
	public void destroy();
	public void print();
	public void beginContact(Contact arg0);
	public void endContact(Contact arg0);
}
