package graphics;
public interface Graphic {
	public void flipX();
	public void unflipX();
	
	public boolean disposable();
	public void reset();
	
	public Graphic clone();
	public void destroy();
	
	public void tick(int delta);
	public void render();
}