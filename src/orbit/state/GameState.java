package orbit.state;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import orbit.client.Player;
import orbit.engine.JiveEngine;
import orbit.entity.Spaceship;
import orbit.map.Map;
import orbit.math.Vector2f;
import orbit.render.Camera;
import orbit.render.Renderer;
import orbit.render.shader.StaticShader;

public class GameState implements State {
	
	private StaticShader shader;
	private Map map;
	private Camera camera;
	private Player player;
	
	public GameState() {
		shader = new StaticShader();
		
		camera = new Camera();
		camera.setPos(new Vector2f(0, 0));
		map = new Map();
		Spaceship ship = new Spaceship();
		ship.setPosition(new Vector2f(500, 250));
		player = new Player(ship);
		map.addObject(ship);
	}

	@Override
	public void enter() {
		//glfwSetInputMode(JiveEngine.getWindow().getId(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}

	@Override
	public void exit() {
		glfwSetInputMode(JiveEngine.getWindow().getId(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
	}

	@Override
	public void update(float dt) {
		camera.update(dt);
		player.update();
		map.update(dt);
	}

	@Override
	public void render(Renderer renderer) {
		renderer.clear();
		renderer.setStaticShader(shader);
		renderer.setCamera(camera);
		map.render(renderer);
	}
}
