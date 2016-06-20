package orbit.window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;

import orbit.math.Vector2i;

public class Window {
	
	private final long id;
	
	private boolean vsync;
	
	public Window(int width, int height, String name, boolean vsync) {
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
	    glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
	    glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
	    glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
	    glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
	    
		id = glfwCreateWindow(width, height, name, NULL, NULL);
		if (id == NULL) {
			glfwTerminate();
			throw new RuntimeException("Failed to create a GLFW window");
		}
		
		glfwMakeContextCurrent(id);
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(id, (vidmode.width() - width) / 2, (vidmode.height() - height) / 2);
		
		setVSync(vsync);
	}
	
	public long getId() {
		return id;
	}
	
	public Vector2i getSize() {
		IntBuffer w = BufferUtils.createIntBuffer(1);
		IntBuffer h = BufferUtils.createIntBuffer(1);
		glfwGetWindowSize(id, w, h);
		return new Vector2i(w.get(), h.get());
	}
	
	public boolean isVsyncEnabled() {
		return vsync;
	}
	
	public boolean shouldWindowClose() {
		return glfwWindowShouldClose(id) == GLFW_TRUE;
	}
	
	public void setVSync(boolean vsync) {
		this.vsync = vsync;
		if (vsync) {
			glfwSwapInterval(1);
		} else {
			glfwSwapInterval(0);
		}
	}

	public void setTitle(String title) {
		glfwSetWindowTitle(id, title);
	}
}
