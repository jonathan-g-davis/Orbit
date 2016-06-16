package orbit.state;

import java.util.HashMap;
import java.util.Map;

import orbit.render.Renderer;

public class StateController {
	
	Map<String, State> states;
	State currentState;
	
	public StateController() {
		states = new HashMap<String, State>();
		currentState = new DefaultState();
	}
	
	public void addState(String name, State state) {
		states.put(name, state);
	}
	
	public void switchState(String name) {
		currentState.exit();
		currentState = states.get(name);
		currentState.enter();
	}
	
	public void update(float dt) {
		currentState.update(dt);
	}
	
	public void render(Renderer renderer) {
		currentState.render(renderer);
	}
}
