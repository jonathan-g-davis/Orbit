package orbit.state;

import static org.lwjgl.glfw.GLFW.GLFW_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_CURSOR_DISABLED;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

import orbit.engine.JiveEngine;
import orbit.entity.Entity;
import orbit.math.Vector3f;
import orbit.model.Material;
import orbit.model.Mesh;
import orbit.model.MeshLoader;
import orbit.render.AssetLoader;
import orbit.render.Camera;
import orbit.render.Renderer;
import orbit.render.shader.StaticShader;
import orbit.texture.Texture;

public class GameState implements State {
	
	StaticShader shader;
	Entity test;
	Camera camera;
	
	public GameState() {
		shader = new StaticShader();
		float[] vertices = {
						-1, 1, 0,
						1, 1, 0,
						0, 0, 0
		};
		float[] texcoords = {0, 0,
						1, 0,
						0.5f, 1};
		int[] indices = {0, 1, 2};
		Mesh model = MeshLoader.createMesh(vertices, texcoords, indices);
		model = MeshLoader.loadMesh("cube.obj");
		Texture tex = AssetLoader.createTexture("crate.png");
		Texture tex2 = AssetLoader.createTexture("crate_spec.png");
		Material material = new Material(tex.getId(), tex2.getId(), 1);
		test = new Entity(model, material, new Vector3f(0, -0.5f, -2), 0, 0, 0, 1f);
		
		camera = new Camera();
		camera.setPos(new Vector3f(0, 0, 0));
	}

	@Override
	public void enter() {
		glfwSetInputMode(JiveEngine.getWindow().getId(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);
	}

	@Override
	public void exit() {
		
	}

	@Override
	public void update(float dt) {
		camera.update(dt);
	}

	@Override
	public void render(Renderer renderer) {
		renderer.clear();
		renderer.render(test, camera, shader);
	}
}
