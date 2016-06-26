package orbit.state;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_NORMAL;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import orbit.client.Player;
import orbit.engine.JiveEngine;
import orbit.entity.Spaceship;
import orbit.entity.weapon.GatlingGun;
import orbit.entity.weapon.RocketLauncher;
import orbit.map.Map;
import orbit.math.Vector2f;
import orbit.render.Renderer;
import orbit.render.shader.StaticShader;

public class GameState implements State {
	
	private StaticShader shader;
	private Map map;
	private Player player;
	
	public GameState() {
		shader = new StaticShader();
		
		map = new Map();
		Spaceship ship = new Spaceship(new GatlingGun());
		ship.setPosition(new Vector2f(1000, 0));
		ship.applyForce(new Vector2f(0, -200000));
		player = new Player(ship);
		map.addObject(ship);
		ship = new Spaceship(new GatlingGun());
		ship.setPosition(new Vector2f(-1000, 0));
		ship.applyForce(new Vector2f(0, 200000));
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
		player.update(map);
		JiveEngine.getWindow().setTitle(""+player.getHealth());
		map.update(dt);
	}

	@Override
	public void render(Renderer renderer) {
		renderer.clear();
		renderer.setStaticShader(shader);
		renderer.setCamera(player.getCamera());
		map.render(renderer);
	}
}
