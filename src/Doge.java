import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Doge {
	private Level level;
	
	private Vec2f position;
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	
	private Mover mover;
	
	private int deltaMove = Config.dogeDelayMove;
	private int deltaDig = Config.dogeDelayDig;
	private int moveDirection = 2;
	
	private Graphic graphicLeft = GraphicFactory.newDogeLeftGraphic();
	private Graphic graphicRight = GraphicFactory.newDogeRightGraphic();
	
	public Doge (Level _level, Vec2f _position) {
		this.level = _level;
		this.position = _position;
		this.graphics.add(GraphicFactory.newDogeLeftGraphic());
	}
	
	public Vec2f getPosition() {
		return this.position;
	}
	
	public void tick (int delta) {
		
		// set graphic according to moveDirection
		switch (this.moveDirection) {
		case 1:
			this.graphics.set(0, this.graphicRight);
			break;
		default:
			this.graphics.set(0, this.graphicLeft);
		}
		
		//MOVER
		if (this.mover != null) {
			if (this.mover.ready()) {
				this.mover = null;
			} else {
				this.position.add(this.mover.getVecDelta(delta));
			}
		}
		
		// make sure position is integer
		if (this.mover == null) {
			this.position.round();
		}
		
		//ACTIVATE everything around me
		if (this.mover == null) {
			for (int x = Math.round(this.position.x) - 1; x < Math.round(this.position.x) + 2; x++) {
				for (int y = Math.round(this.position.y) - 1; y < Math.round(this.position.y) + 2; y++) {
					Entity e = this.level.get(new Vec2f(x, y));
					if (e != null)
						e.activate();
				}
			}
		}
		
		//FALLING
		if (this.mover == null) {
			Entity bot = this.level.get(new Vec2f(this.position.x, this.position.y + 1f));
			if (bot == null) {
				this.mover = new MoverLinear(new Vec2f(0f, 1f), Math.round(100 * (1 / this.level.getGravity())) );
			}
		}
		
		//MOVING
		deltaMove += delta;
		if (this.mover == null && this.deltaMove > Config.dogeDelayMove) {
			if (Keyboard.isKeyDown(Config.keyUp)) {
				// top
				this.moveDirection = 0;
				
			} else if (Keyboard.isKeyDown(Config.keyRight)) {
				// right
				this.moveDirection = 1;
				Entity right = this.level.get(new Vec2f(this.position.x + 1, Math.round(this.position.y)));
				this.deltaMove = 0;
				if (right == null) {
					this.mover = new MoverLinear(new Vec2f(1f, 0f), 100);
					return;
				} else {
					Entity up = this.level.get(new Vec2f(this.position.x, this.position.y - 1));
					if (up == null) {
						Entity rightup = this.level.get(new Vec2f(this.position.x + 1, this.position.y - 1));
						if (rightup == null) {
							this.mover = new MoverLinear(new Vec2f(1f, -1f), 200);
						}
					}
				}
				
			} else if (Keyboard.isKeyDown(Config.keyDown)) {
				// down
				this.moveDirection = 2;
				
			} else if (Keyboard.isKeyDown(Config.keyLeft)) {
				// left
				this.moveDirection = 3;
				Entity left = this.level.get(new Vec2f(this.position.x - 1, this.position.y));
				this.deltaMove = 0;
				if (left == null) {
					this.mover = new MoverLinear(new Vec2f(-1f, 0f), 100);
					return;
				} else {
					Entity up = this.level.get(new Vec2f(this.position.x, this.position.y - 1));
					if (up == null) {
						Entity leftup = this.level.get(new Vec2f(this.position.x - 1, this.position.y - 1));
						if (leftup == null) {
							this.mover = new MoverLinear(new Vec2f(-1f, -1f), 200);
						}
					}
				}
			}
		}
		
		// DIGGING
		deltaDig += delta;
		if (this.mover == null && this.deltaDig > Config.dogeDelayDig && Keyboard.isKeyDown(Config.keyDig)) {
			if (this.moveDirection == 0) {
				// up
				Entity top = this.level.get(new Vec2f(this.position.x, this.position.y - 1));
				if (top != null) {
					top.destroy();
					this.deltaDig = 0;
					this.deltaMove = 0;
				}
			} else if (this.moveDirection == 1) {
				// right
				Entity right = this.level.get(new Vec2f(this.position.x + 1, this.position.y));
				if (right != null) {
					right.destroy();
					this.deltaDig = 0;
					this.deltaMove = -Config.dogeDelayMove;
				}
				
			} else if (this.moveDirection == 2) {
				// down
				Entity bottom = this.level.get(new Vec2f(this.position.x, this.position.y + 1));
				if (bottom != null) {
					bottom.destroy();
					this.deltaDig = 0;
					this.deltaMove = 0;
				}
				
			} else if (this.moveDirection == 3) {
				// left
				Entity left = this.level.get(new Vec2f(this.position.x - 1, this.position.y));
				if (left != null) {
					left.destroy();
					this.deltaDig = 0;
					this.deltaMove = -Config.dogeDelayMove;
				}
			}
		}
	}
	
	public void render (int delta) {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x, this.position.y, 0f);
			for (Graphic g : this.graphics) {
				g.render(delta);
			}
		GL11.glPopMatrix();
	}
}
