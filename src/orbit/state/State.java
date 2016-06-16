package orbit.state;

import orbit.render.Renderer;

public interface State {
	
	public void enter();
	
	public void exit();
	
	public void update(float dt);

	public void render(Renderer renderer);
}
