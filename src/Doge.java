import graphics.Graphic;
import graphics.GraphicAnimation;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import util.Vec2f;
import util.Vec2i;

public class Doge {
	private Level level;
	
	private Vec2i position;
	private Vec2f offset = Vec2f.nil();
	private Graphic[] graphics;
		// 0 -> body
		// 1 -> head
		// 2 -> hat
		// 3 -> whatever item
	private Mover mover;
	
	private DogeState state = DogeState.WAITING_LEFT;
	private boolean stateChanged = false;
	private boolean intense = false;
	
	private int deltaDig = Config.dogeDelayDig;
	private int moveDirection = 2;
	
	private Graphic[] graphicWaitingLeft;
	private Graphic[] graphicWaitingRight;
	private Graphic[] graphicWaitingUp;
	private Graphic[] graphicWaitingDown;
	
	private Graphic[] graphicWalkingLeft;
	private Graphic[] graphicWalkingRight;
	
	private Graphic[] graphicDiggingLeft;
	private Graphic[] graphicDiggingRight;
	private Graphic[] graphicDiggingUp;
	private Graphic[] graphicDiggingDown;
	
	private Graphic[] graphicDead;
	
	
	public Doge (Level _level, Vec2i _position) {
		this.level = _level;
		this.position = _position;
		
		this.graphicWaitingLeft = new Graphic[] {
				GraphicFactory.newDogeBodyWaiting(),
				GraphicFactory.newDogeHeadWaiting(),
				null,
				null
		};
		this.graphicWaitingRight = this.graphicWaitingLeft.clone();
		for (Graphic g : this.graphicWaitingRight) {
			if (g != null)
				g.flip();
		}
		this.graphicWaitingUp = this.graphicWaitingLeft.clone();
		this.graphicWaitingDown = this.graphicWaitingLeft.clone();
		
		this.graphicWalkingLeft = new Graphic[] {
				GraphicFactory.newDogeBodyWalking(),
				GraphicFactory.newDogeHeadWalking(),
				null,
				null
		};
		this.graphicWalkingRight = this.graphicWaitingLeft.clone();
		for (Graphic g : this.graphicWaitingRight) {
			if (g != null)
				g.flip();
		}
		
		this.graphicDiggingLeft = new Graphic[] {
				GraphicFactory.newDogeBodyDiggingSide(),
				GraphicFactory.newDogeHeadDiggingSide(),
				null,
				null
		};
		this.graphicWaitingRight = this.graphicWaitingLeft.clone();
		for (Graphic g : this.graphicWaitingRight) {
			if (g != null)
				g.flip();
		}
		this.graphicWaitingUp = this.graphicWaitingLeft.clone();
		this.graphicWaitingDown = new Graphic[] {
				GraphicFactory.newDogeBodyDiggingDown(),
				GraphicFactory.newDogeHeadDiggingDown(),
				null,
				null
		};

		this.graphicDead = this.graphicWaitingLeft.clone();
				
		this.graphics = this.graphicWaitingLeft;
	}
	
	public Vec2i getPosition() {
		return this.position;
	}
	
	public Vec2f getOffset() {
		return this.offset;
	}
	
	public void die() {
		this.state = DogeState.DEAD;
		System.out.println("DEATH!!!");
	}
	
	public void tick (int delta) {
		
		// graphics
		boolean disposable = true;
		if (this.graphics[0] instanceof GraphicAnimation) {
			disposable = !((GraphicAnimation) this.graphics[0]).disposable();
		}
		
		if (this.stateChanged && disposable) {
			switch (this.state) {
			case WAITING_LEFT:
				this.graphics = this.graphicWaitingLeft;
				break;
			case WAITING_RIGHT:
				this.graphics = this.graphicWaitingRight;
				break;
			case WAITING_UP:
				this.graphics = this.graphicWaitingUp;
				break;
			case WAITING_DOWN:
				this.graphics = this.graphicWaitingDown;
				break;
			case WALKING_LEFT:
				this.graphics = this.graphicWalkingLeft;
				break;
			case WALKING_RIGHT:
				this.graphics = this.graphicWalkingRight;
				break;
			case DIGGING_LEFT:
				this.graphics = this.graphicDiggingLeft;
				break;
			case DIGGING_RIGHT:
				this.graphics = this.graphicDiggingRight;
				break;
			case DIGGING_UP:
				this.graphics = this.graphicDiggingUp;
				break;
			case DIGGING_DOWN:
				this.graphics = this.graphicDiggingDown;
				break;
			case DEAD:
				this.graphics = this.graphicDead;
				break;
			}
			for (Graphic g : this.graphics) {
				if (g != null)
					g.reset();
			}
			this.stateChanged = false;
		}
		
		// mover
		if (this.mover != null) {
			if (this.mover.disposable())
				this.mover = null;
			else
				this.offset.add(this.mover.getVecDelta(delta));				
		} else {
			
			if (this.offset.x > 0.99) {
				this.position.x += 1;
				this.offset.x -= 1;
			} else if (this.offset.x < -0.99) {
				this.position.x -= 1;
				this.offset.x += 1;
			}
			if (this.offset.y > 0.99) {
				this.position.y += 1;
				this.offset.y -= 1;
			} else if (this.offset.y < -0.99) {
				this.position.y -= 1;
				this.offset.y += 1;
			}
		
			// die
			if (this.level.get(this.position) != null)
				this.die();
			
			// [intensifies]
			if (!this.intense && this.position.y > Config.levelMaxY - 10) {
				this.intense = true;
				//this.graphicWaitingLeft[1] = GraphicFactory.newDogeHeadIntense();
				//TODO [intensifies]
			}
			
			// activate
			ArrayList<Entity> list = this.level.getEntitiesInRadius(this.position, 1);
			for (Entity e : list) {
				e.activate();
			}
			
			// check to fall
			Entity bot = this.level.get(new Vec2i(this.position.x, this.position.y + 1));
			if (bot == null) {
				this.mover = new MoverLinear(new Vec2f(0f, 1f), Math.round(100 * (1 / this.level.getGravity())) );
				return;
			}
			
			// move
			if (Keyboard.isKeyDown(Config.keyUp)) {
				// top
				this.moveDirection = 0;
				this.state = DogeState.WAITING_UP;
				this.stateChanged = true;
				return;
				
			} else if (Keyboard.isKeyDown(Config.keyRight)) {
				// right
				this.moveDirection = 1;
				this.state = DogeState.WAITING_RIGHT;
				this.stateChanged = true;
				
				Entity right = this.level.get(new Vec2i(this.position.x + 1, Math.round(this.position.y)));
				if (right == null) {
					this.mover = new MoverLinear(new Vec2f(1f, 0f), 100);
					this.state = DogeState.WALKING_RIGHT;
					return;
				} else {
					Entity up = this.level.get(new Vec2i(this.position.x, this.position.y - 1));
					if (up == null) {
						Entity rightup = this.level.get(new Vec2i(this.position.x + 1, this.position.y - 1));
						if (rightup == null) {
							this.mover = new MoverLinear(new Vec2f(1f, -1f), 200);
							this.state = DogeState.WALKING_RIGHT;
							return;
						}
					}
				}
				
			} else if (Keyboard.isKeyDown(Config.keyDown)) {
				// down
				this.moveDirection = 2;
				this.state = DogeState.WAITING_DOWN;
				this.stateChanged = true;
				return;
				
			} else if (Keyboard.isKeyDown(Config.keyLeft)) {
				// left
				this.moveDirection = 3;
				this.state = DogeState.WALKING_LEFT;
				this.stateChanged = true;
				
				Entity left = this.level.get(new Vec2i(this.position.x - 1, this.position.y));
				if (left == null) {
					this.mover = new MoverLinear(new Vec2f(-1f, 0f), 100);
					this.state = DogeState.WALKING_LEFT;
					return;
				} else {
					Entity up = this.level.get(new Vec2i(this.position.x, this.position.y - 1));
					if (up == null) {
						Entity leftup = this.level.get(new Vec2i(this.position.x - 1, this.position.y - 1));
						if (leftup == null) {
							this.mover = new MoverLinear(new Vec2f(-1f, -1f), 200);
							this.state = DogeState.WALKING_LEFT;
							return;
						}
					}
				}
			}
		}
		
		// dig
		deltaDig += delta;
		if (this.deltaDig > Config.dogeDelayDig && Keyboard.isKeyDown(Config.keyDig)) {
			if (this.moveDirection == 0) {
				// up
				Entity top = this.level.get(new Vec2i(this.position.x, this.position.y - 1));
				if (top != null) {
					top.destroy();
					this.deltaDig = delta;
					return;
				}
			} else if (this.moveDirection == 1) {
				// right
				Entity right = this.level.get(new Vec2i(this.position.x + 1, this.position.y));
				this.state = DogeState.DIGGING_RIGHT;
				this.stateChanged = true;
				if (right != null) {
					right.destroy();
					this.deltaDig = delta;
					return;
				}
				
			} else if (this.moveDirection == 2) {
				// down
				this.state = DogeState.DIGGING_DOWN;
				this.stateChanged = true;
				Entity bottom = this.level.get(new Vec2i(this.position.x, this.position.y + 1));
				if (bottom != null) {
					bottom.destroy();
					this.deltaDig = delta;
					return;
				}
				
			} else if (this.moveDirection == 3) {
				// left
				Entity left = this.level.get(new Vec2i(this.position.x - 1, this.position.y));
				this.state = DogeState.DIGGING_LEFT;
				this.stateChanged = true;
				if (left != null) {
					left.destroy();
					this.deltaDig = delta;
					return;
				}
			}
		}
	}
	
	public void render (int delta) {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x + this.offset.x, this.position.y + this.offset.y, 0f);
			for (Graphic g : this.graphics) {
				if (g != null)
					g.render(delta);
			}
		GL11.glPopMatrix();
	}
}

enum DogeState {
	WAITING_LEFT,
	WAITING_RIGHT,
	WAITING_UP,
	WAITING_DOWN,
	WALKING_LEFT,
	WALKING_RIGHT,
	DIGGING_LEFT,
	DIGGING_RIGHT,
	DIGGING_UP,
	DIGGING_DOWN,
	DEAD
}
