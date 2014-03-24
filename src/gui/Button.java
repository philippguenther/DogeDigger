package gui;

import org.lwjgl.opengl.GL11;

import graphics.Graphic;
import util.Vec2f;

public class Button {
	private String desc;
	
	private Graphic[] graphics;
	private Graphic[] activeGraphics;
	private Vec2f position;
	
	public Button(String _desc, Vec2f _pos, Graphic[] _graphics, Graphic[] _active) {
		this.desc = _desc;
		this.position = _pos.clone();
		this.graphics = _graphics;
		this.activeGraphics = _active;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
	public void tick(int delta) {
		for (Graphic g : this.graphics) {
			if (g != null)
				g.tick(delta);
		}
	}
	
	public void tickActive(int delta) {
		for (Graphic g : this.activeGraphics) {
			if (g != null)
				g.tick(delta);
		}
	}
	
	public void render() {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x, this.position.y, 0f);
			for (Graphic g : this.graphics) {
				if (g != null)
					g.render();
			}
		GL11.glPopMatrix();
	}
	
	public void renderActive() {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x, this.position.y, 0f);
			for (Graphic g : this.activeGraphics) {
				if (g != null)
					g.render();
			}
		GL11.glPopMatrix();
	}
}
