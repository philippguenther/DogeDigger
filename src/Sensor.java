import java.util.ArrayList;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;


public class Sensor implements ContactListener{
	private ArrayList<Entity> contacts = new ArrayList<Entity>();
	private Object desc;
	
	public Sensor(Object _desc) {
		this.desc = _desc;
	}
	
	public ArrayList<Entity> getContacts() {
		return this.contacts;
	}
	
	public Object getDesc() {
		return this.desc;
	}
	
	@Override
	public void beginContact(Contact arg0) {
		Object a = arg0.getFixtureA().getUserData();
		Object b = arg0.getFixtureB().getUserData();
		Entity e = null;
		if (a == this.desc && b instanceof Entity)
			e = (Entity) b;
		else if (b == this.desc && a instanceof Entity)
			e = (Entity) a;
		if (e != null && !this.contacts.contains(e)) {
			this.contacts.add(e);
		}
	}

	@Override
	public void endContact(Contact arg0) {
		Object a = arg0.getFixtureA().getUserData();
		Object b = arg0.getFixtureB().getUserData();
		Entity e = null;
		if (a == this.desc && b instanceof Entity)
			e = (Entity) b;
		else if (b == this.desc && a instanceof Entity)
			e = (Entity) a;
		if (e != null) {
			this.contacts.remove(e);
		}
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub
		
	}

}
