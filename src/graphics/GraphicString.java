package graphics;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import util.Vec2f;

public class GraphicString implements Graphic {
	
	private static GLImage fontTerminal = new GLImage("FontTerminal.png");
	private static HashMap<Character,Vec2f> lookup = new HashMap<Character,Vec2f>(62);
	
	private String string;
	private float size;
	private GraphicImage[] graphics;
	private Vec2f offset;
	
	public GraphicString(String _string, float _size, Vec2f _offset) {
		this.setString(_string);
		this.size = _size;
		this.offset = _offset;
	}
	
	public void setString(String _string) {
		this.string = _string;
		this.graphics = new GraphicImage[this.string.length()];
		for (int i = 0; i < this.string.length(); i++) {
			Vec2f start = GraphicString.lookup.get(this.string.charAt(i));
			float[] clipping = new float[] {
				start.x,
				start.y,
				start.x + 0.09375f,
				start.y + 0.125f
			};
			Vec2f offset = new Vec2f(i, 0f);
			this.graphics[i] = new GraphicImage(GraphicString.fontTerminal, clipping, offset, false);
		}
	}
	
	public String getString() {
		return this.string;
	}

	@Override
	public void flipX() {
		for (GraphicImage g : this.graphics)
			g.flipX();
	}

	@Override
	public void unflipX() {
		for (GraphicImage g : this.graphics)
			g.unflipX();
	}

	@Override
	public boolean disposable() {
		return true;
	}

	@Override
	public void reset() {
		
	}
	
	@Override
	public Graphic clone() {
		return this;
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public void tick(int delta) {
		
	}

	@Override
	public void render() {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.offset.x, this.offset.y, 0f);
			GL11.glScalef(this.size, this.size, 1f);
			for (GraphicImage g : this.graphics) {
				g.render();
			}
		GL11.glPopMatrix();
	}
	
	public static void init() {
		float x = 0.09375f;
		float y = 0.125f;
		GraphicString.lookup.put('0', new Vec2f(0 * x, 0 * y));
		GraphicString.lookup.put('1', new Vec2f(1 * x, 0 * y));
		GraphicString.lookup.put('2', new Vec2f(2 * x, 0 * y));
		GraphicString.lookup.put('3', new Vec2f(3 * x, 0 * y));
		GraphicString.lookup.put('4', new Vec2f(4 * x, 0 * y));
		GraphicString.lookup.put('5', new Vec2f(5 * x, 0 * y));
		GraphicString.lookup.put('6', new Vec2f(6 * x, 0 * y));
		GraphicString.lookup.put('7', new Vec2f(7 * x, 0 * y));
		GraphicString.lookup.put('8', new Vec2f(8 * x, 0 * y));
		GraphicString.lookup.put('9', new Vec2f(9 * x, 0 * y));
		GraphicString.lookup.put('A', new Vec2f(0 * x, 1 * y));
		GraphicString.lookup.put('B', new Vec2f(1 * x, 1 * y));
		GraphicString.lookup.put('C', new Vec2f(2 * x, 1 * y));
		GraphicString.lookup.put('D', new Vec2f(3 * x, 1 * y));
		GraphicString.lookup.put('E', new Vec2f(4 * x, 1 * y));
		GraphicString.lookup.put('F', new Vec2f(5 * x, 1 * y));
		GraphicString.lookup.put('G', new Vec2f(6 * x, 1 * y));
		GraphicString.lookup.put('H', new Vec2f(7 * x, 1 * y));
		GraphicString.lookup.put('I', new Vec2f(8 * x, 1 * y));
		GraphicString.lookup.put('J', new Vec2f(9 * x, 1 * y));
		GraphicString.lookup.put('K', new Vec2f(0 * x, 2 * y));
		GraphicString.lookup.put('L', new Vec2f(1 * x, 2 * y));
		GraphicString.lookup.put('M', new Vec2f(2 * x, 2 * y));
		GraphicString.lookup.put('N', new Vec2f(3 * x, 2 * y));
		GraphicString.lookup.put('O', new Vec2f(4 * x, 2 * y));
		GraphicString.lookup.put('P', new Vec2f(5 * x, 2 * y));
		GraphicString.lookup.put('Q', new Vec2f(6 * x, 2 * y));
		GraphicString.lookup.put('R', new Vec2f(7 * x, 2 * y));
		GraphicString.lookup.put('S', new Vec2f(8 * x, 2 * y));
		GraphicString.lookup.put('T', new Vec2f(9 * x, 2 * y));
		GraphicString.lookup.put('U', new Vec2f(0 * x, 3 * y));
		GraphicString.lookup.put('V', new Vec2f(1 * x, 3 * y));
		GraphicString.lookup.put('W', new Vec2f(2 * x, 3 * y));
		GraphicString.lookup.put('X', new Vec2f(3 * x, 3 * y));
		GraphicString.lookup.put('Y', new Vec2f(4 * x, 3 * y));
		GraphicString.lookup.put('Z', new Vec2f(5 * x, 3 * y));
		GraphicString.lookup.put('a', new Vec2f(6 * x, 3 * y));
		GraphicString.lookup.put('b', new Vec2f(7 * x, 3 * y));
		GraphicString.lookup.put('c', new Vec2f(8 * x, 3 * y));
		GraphicString.lookup.put('d', new Vec2f(9 * x, 3 * y));
		GraphicString.lookup.put('e', new Vec2f(0 * x, 4 * y));
		GraphicString.lookup.put('f', new Vec2f(1 * x, 4 * y));
		GraphicString.lookup.put('g', new Vec2f(2 * x, 4 * y));
		GraphicString.lookup.put('h', new Vec2f(3 * x, 4 * y));
		GraphicString.lookup.put('i', new Vec2f(4 * x, 4 * y));
		GraphicString.lookup.put('j', new Vec2f(5 * x, 4 * y));
		GraphicString.lookup.put('k', new Vec2f(6 * x, 4 * y));
		GraphicString.lookup.put('l', new Vec2f(7 * x, 4 * y));
		GraphicString.lookup.put('m', new Vec2f(8 * x, 4 * y));
		GraphicString.lookup.put('n', new Vec2f(9 * x, 4 * y));
		GraphicString.lookup.put('o', new Vec2f(0 * x, 5 * y));
		GraphicString.lookup.put('p', new Vec2f(1 * x, 5 * y));
		GraphicString.lookup.put('q', new Vec2f(2 * x, 5 * y));
		GraphicString.lookup.put('r', new Vec2f(3 * x, 5 * y));
		GraphicString.lookup.put('s', new Vec2f(4 * x, 5 * y));
		GraphicString.lookup.put('t', new Vec2f(5 * x, 5 * y));
		GraphicString.lookup.put('u', new Vec2f(6 * x, 5 * y));
		GraphicString.lookup.put('v', new Vec2f(7 * x, 5 * y));
		GraphicString.lookup.put('w', new Vec2f(8 * x, 5 * y));
		GraphicString.lookup.put('x', new Vec2f(9 * x, 5 * y));
		GraphicString.lookup.put('y', new Vec2f(0 * x, 6 * y));
		GraphicString.lookup.put('z', new Vec2f(1 * x, 6 * y));
	}

}
