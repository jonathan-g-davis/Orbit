package orbit.engine;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Clock {
	
	private double lastExecution = 0;
	
	public static double getTime() {
		return glfwGetTime();
	}
	
	public double getDeltaTime() {
		double dt = getTime() - lastExecution;
		lastExecution = getTime();
		return dt;
	}
}
