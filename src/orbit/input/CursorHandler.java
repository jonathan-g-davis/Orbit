package orbit.input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

public class CursorHandler extends GLFWCursorPosCallback {

	private static double xpos;
	private static double ypos;
	
	@Override
	public void invoke(long window, double xpos, double ypos) {
		CursorHandler.xpos = xpos;
		CursorHandler.ypos = ypos;
	}
	
	public static double getXpos() {
		return xpos;
	}
	
	public static double getYpos() {
		return ypos;
	}
}
