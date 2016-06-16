package orbit.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class KeyHandler extends GLFWKeyCallback {

	private static boolean[] keys = new boolean[65536];
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		if (key >= 0) keys[key] = action != GLFW.GLFW_RELEASE;
	}
	
	public static boolean isKeyDown(int key) {
		return keys[key];
	}
}
