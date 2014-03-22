import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import util.Vec2f;
import util.Vec2i;
import graphics.Graphic;

public class Doge {
	private Level level;
	
	private Vec2i position;
	private Vec2f offset;
	private DogeState state;
	private DogeState stateNext;
	
	private Mover mover;
	
	private Graphic[] graphics;
		// 0 -> body
		// 1 -> head
		// 2 -> hat
		// 3 -> whatever item
	
	private int deltaDig;
	private int deltaMove;
	
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
	
	
	public Doge(Level _level, Vec2i _position) {
		this.level = _level;
		
		this.position = _position;
		this.offset = new Vec2f(0f, 0f);
		this.state = DogeState.WAITING_LEFT;
		this.stateNext = null;
		
		this.deltaDig = Config.dogeDelayDig;
		this.deltaMove = Config.dogeDelayMove;
		
		this.graphicWaitingLeft = new Graphic[] {
				GraphicFactory.DOGE_BODY_WAITING.clone(),
				GraphicFactory.DOGE_HEAD_WAITING.clone(),
				null,
				null
		};
		this.graphicWaitingRight = new Graphic[] {
				GraphicFactory.DOGE_BODY_WAITING.clone(),
				GraphicFactory.DOGE_HEAD_WAITING.clone(),
				null,
				null
		};
		for (Graphic g : this.graphicWaitingRight) {
			if (g != null)
				g.flipX();
		}
		this.graphicWaitingUp = new Graphic[] {
				GraphicFactory.DOGE_BODY_WAITING.clone(),
				GraphicFactory.DOGE_HEAD_WAITING.clone(),
				null,
				null
		};
		this.graphicWaitingDown = new Graphic[] {
				GraphicFactory.DOGE_BODY_WAITING.clone(),
				GraphicFactory.DOGE_HEAD_WAITING.clone(),
				null,
				null
		};
		
		this.graphicWalkingLeft = new Graphic[] {
				GraphicFactory.DOGE_BODY_WALKING.clone(),
				GraphicFactory.DOGE_HEAD_WALKING.clone(),
				null,
				null
		};
		this.graphicWalkingRight = new Graphic[] {
				GraphicFactory.DOGE_BODY_WALKING.clone(),
				GraphicFactory.DOGE_HEAD_WALKING.clone(),
				null,
				null
		};
		for (Graphic g : this.graphicWalkingRight) {
			if (g != null)
				g.flipX();
		}
		
		this.graphicDiggingLeft = new Graphic[] {
				GraphicFactory.DOGE_BODY_DIGGING_SIDE.clone(),
				GraphicFactory.DOGE_HEAD_DIGGING_SIDE.clone(),
				null,
				null
		};
		this.graphicDiggingRight = new Graphic[] {
				GraphicFactory.DOGE_BODY_DIGGING_SIDE.clone(),
				GraphicFactory.DOGE_HEAD_DIGGING_SIDE.clone(),
				null,
				null
		};
		for (Graphic g : this.graphicDiggingRight) {
			if (g != null)
				g.flipX();
		}
		this.graphicDiggingUp = new Graphic[] {
				GraphicFactory.DOGE_BODY_WAITING.clone(),
				GraphicFactory.DOGE_HEAD_WAITING.clone(),
				null,
				null
		};
		this.graphicDiggingDown = new Graphic[] {
				GraphicFactory.DOGE_BODY_DIGGING_DOWN.clone(),
				GraphicFactory.DOGE_HEAD_DIGGING_DOWN.clone(),
				null,
				null
		};

		this.graphicDead = new Graphic[] {
				GraphicFactory.DOGE_BODY_WAITING.clone(),
				GraphicFactory.DOGE_HEAD_WAITING.clone(),
				null,
				null
		};
				
		this.graphics = this.graphicWaitingLeft;
	}
	
	public Vec2i getPosition() {
		return this.position;
	}
	
	public Vec2f getOffset() {
		return this.offset;
	}
	
	public void tick(int delta) {
		for (Graphic g : this.graphics)
			if (g != null)
				g.tick(delta);
		
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
			Entity deathEntity = this.level.get(this.position);
			if (deathEntity != null) {
				this.stateNext = DogeState.DEAD;
			}
			
			// activate
			Entity[] radius = this.level.getEntitiesInRadius(this.position, Config.dogeActivationRadius);
			for (Entity rad : radius) {
				if (rad != null)
					rad.activate();
			}
			Entity[] row = this.level.getRow(this.position.y - 1);
			for (Entity r : row) {
				if (r != null)
					r.activate();
			}
			
			// check to fall
			Entity bottom = this.level.get(new Vec2i(this.position.x, this.position.y + 1));
			if (bottom == null) {
				this.mover = new MoverLinear(new Vec2f(0f, 1f), Math.round(100 * (1 / this.level.getGravity())) );
				return;
			}
			
			this.deltaMove += delta;
			this.deltaDig += delta;
			
			boolean animationDisposable = true;
			for (Graphic g : this.graphics) {
				if (g != null && !g.disposable()) {
					animationDisposable = false;
					break;
				}
			}
			
			if (this.deltaDig > Config.dogeDelayDig && Keyboard.isKeyDown(Config.keyDig)) {
				// dig
				if (this.state == DogeState.WAITING_UP) {
					// up
					this.stateNext = DogeState.DIGGING_UP;
					Entity top = this.level.get(new Vec2i(this.position.x, this.position.y - 1));
					if (top != null) {
						this.deltaDig = delta;
						this.deltaMove = delta;
						top.takeHit();
					}
				} else if (this.state == DogeState.WAITING_RIGHT) {
					// right
					this.stateNext = DogeState.DIGGING_RIGHT;
					Entity right = this.level.get(new Vec2i(this.position.x + 1, this.position.y));
					if (right != null) {
						this.deltaDig = delta;
						this.deltaMove = delta;
						right.takeHit();
					}
					
				} else if (this.state == DogeState.WAITING_DOWN) {
					// down
					this.stateNext = DogeState.DIGGING_DOWN;
					Entity down = this.level.get(new Vec2i(this.position.x, this.position.y + 1));
					if (down != null) {
						this.deltaDig = delta;
						this.deltaMove = delta;
						down.takeHit();
					}
					
				} else if (this.state == DogeState.WAITING_LEFT) {
					// left
					this.stateNext = DogeState.DIGGING_LEFT;
					Entity left = this.level.get(new Vec2i(this.position.x - 1, this.position.y));
					if (left != null) {
						this.deltaDig = delta;
						this.deltaMove = delta;
						left.takeHit();
					}
				}
			} else if (this.deltaMove > Config.dogeDelayMove && 
					(Keyboard.isKeyDown(Config.keyLeft) || 
					Keyboard.isKeyDown(Config.keyRight) || 
					Keyboard.isKeyDown(Config.keyUp) || 
					Keyboard.isKeyDown(Config.keyDown) 
			)) {
				// move
				if (Keyboard.isKeyDown(Config.keyUp)) {
					// top
					this.stateNext = DogeState.WAITING_UP;
					
				} else if (Keyboard.isKeyDown(Config.keyRight)) {
					// right
					this.stateNext = DogeState.WAITING_RIGHT;
					
					Entity right = this.level.get(new Vec2i(this.position.x + 1, Math.round(this.position.y)));
					if (right == null) {
						this.stateNext = DogeState.WALKING_RIGHT;
						this.deltaMove = delta;
						this.mover = new MoverLinear(new Vec2f(1f, 0f), 100);
					} else {
						Entity up = this.level.get(new Vec2i(this.position.x, this.position.y - 1));
						if (up == null) {
							Entity rightup = this.level.get(new Vec2i(this.position.x + 1, this.position.y - 1));
							if (rightup == null) {
								this.stateNext = DogeState.WALKING_RIGHT;
								this.deltaMove = delta;
								this.mover = new MoverLinear(new Vec2f(1f, -1f), 200);
							}
						}
					}
					
				} else if (Keyboard.isKeyDown(Config.keyDown)) {
					// down
					this.stateNext = DogeState.WAITING_DOWN;
					
				} else if (Keyboard.isKeyDown(Config.keyLeft)) {
					// left
					this.stateNext = DogeState.WAITING_LEFT;
					
					Entity left = this.level.get(new Vec2i(this.position.x - 1, this.position.y));
					if (left == null) {
						this.stateNext = DogeState.WALKING_LEFT;
						this.deltaMove = delta;
						this.mover = new MoverLinear(new Vec2f(-1f, 0f), 100);
					} else {
						Entity up = this.level.get(new Vec2i(this.position.x, this.position.y - 1));
						if (up == null) {
							Entity leftup = this.level.get(new Vec2i(this.position.x - 1, this.position.y - 1));
							if (leftup == null) {
								this.state = DogeState.WALKING_LEFT;
								this.deltaMove = delta;
								this.mover = new MoverLinear(new Vec2f(-1f, -1f), 200);
							}
						}
					}
				}
			} else if (animationDisposable){
				switch (this.state) {
				case WALKING_LEFT:
				case DIGGING_LEFT:
					this.stateNext = DogeState.WAITING_LEFT;
					break;
				case WALKING_RIGHT:
				case DIGGING_RIGHT:
					this.stateNext = DogeState.WAITING_RIGHT;
					break;
				case DIGGING_UP:
					this.stateNext = DogeState.WAITING_UP;
					break;
				case DIGGING_DOWN:
					this.stateNext = DogeState.WAITING_DOWN;
					break;
				default:
					break;
				}
			}
			
			// graphics
			if (this.stateNext != null && this.stateNext != this.state) {
				switch (this.stateNext) {
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
				
				this.state = this.stateNext;
				this.stateNext = null;
			}
		}
	}
	
	public void render() {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x + this.offset.x, this.position.y + this.offset.y, 0f);
			for (Graphic g : this.graphics) {
				if (g != null)
					g.render();
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
