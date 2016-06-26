package orbit.engine;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class Clock {
	
	private double lastExecution = 0;
	private double timer = 0;
	private double setTime = 0;
	
	public static double getTime() {
		return glfwGetTime();
	}
	
	public double getDeltaTime() {
		double dt = getTime() - lastExecution;
		lastExecution = getTime();
		return dt;
	}
	
	public void setTimer(double time) {
		this.timer = time;
		this.setTime = getTime() + timer;
	}
	
	public boolean checkTimer() {
		if (getTime() > setTime) {
			setTime = getTime() + timer;
			return true;
		}
		
		return false;
	}
}
