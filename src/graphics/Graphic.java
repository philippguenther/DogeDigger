package graphics;
public interface Graphic {
	public void render(int delta);
	
	public void flip();
	public void unflip();
	
	public boolean disposable();
	public void reset();
	
	public void destroy();
}