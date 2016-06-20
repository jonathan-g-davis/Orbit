package orbit.engine;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

import orbit.input.CursorHandler;
import orbit.input.KeyHandler;
import orbit.model.MeshLoader;
import orbit.render.AssetLoader;
import orbit.render.Renderer;
import orbit.state.GameState;
import orbit.state.StateController;
import orbit.window.Window;

public class JiveEngine {
	
	private static GLFWErrorCallback errorCallback;
	private static GLFWKeyCallback keyCallback;
	private static GLFWCursorPosCallback cursorCallback;
	private static Clock clock;
	private static Window window;
	private static Renderer renderer;
	private static StateController stateController;
	
	private static boolean running;
	
	public static void start() {
		preInit();
		init();
		postInit();
		
		running = true;
		gameLoop();
	}
	
	public static void stop() {
		running = false;
	}
	
	public static Window getWindow() {
		return window;
	}
	
	private static void preInit() {
		errorCallback = GLFWErrorCallback.createPrint();
		glfwSetErrorCallback(errorCallback);
		
		if (glfwInit() != GL_TRUE)
			throw new IllegalStateException("Unable to initialize GLFW");
	}
	
	private static void init() {
		window = new Window(1280, 720, "Test", true);
		keyCallback = new KeyHandler();
		glfwSetKeyCallback(window.getId(), keyCallback);
		cursorCallback = new CursorHandler();
		glfwSetCursorPosCallback(window.getId(), cursorCallback);
		
		clock = new Clock();
		
		renderer = new Renderer();
		renderer.init();
		
		stateController = new StateController();
		stateController.addState("game", new GameState());
		stateController.switchState("game");
	}
	
	private static void postInit() {
		
	}
	
	private static void shutdown() {
		AssetLoader.dispose();
		MeshLoader.dispose();
		renderer.dispose();
		glfwTerminate();
	}
	
	private static void gameLoop() {
		while (running) {
			
			input();
			update((float) clock.getDeltaTime());
			render();
			
			if (window.shouldWindowClose()) {
				running = false;
			}
		}
		
		shutdown();
	}
	
	private static void input() {
		glfwPollEvents();
	}
	
	private static void update(float dt) {
		stateController.update(dt);
	}
	
	private static void render() {
		glfwSwapBuffers(window.getId());
		stateController.render(renderer);
	}
}
