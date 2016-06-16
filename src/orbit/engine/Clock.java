package orbit.engine;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Clock {
	
	private static double lastExecution;
	
	public static double getTime() {
		return glfwGetTime();
	}
	
	public static double getDeltaTime() {
		double dt = getTime() - lastExecution;
		lastExecution = getTime();
		return dt;
	}
}
