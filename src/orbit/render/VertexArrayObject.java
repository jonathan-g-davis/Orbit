package orbit.render;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.List;

public class VertexArrayObject {
	
	private final int id;
	private List<VertexBufferObject> vbos;
	
	public VertexArrayObject() {
		id = glGenVertexArrays();
		vbos = new ArrayList<VertexBufferObject>();
	}
	
	public int getId() {
		return id;
	}
	
	public void bind() {
		glBindVertexArray(id);
	}
	
	public void unbind() {
		glBindVertexArray(0);
	}
	
	public void addVBO(VertexBufferObject vbo) {
		if (vbo != null)
			vbos.add(vbo);
	}
	
	public void enableVertexAttribArray(int array) {
		glEnableVertexAttribArray(array);
	}
	
	public void disableVertexAttribArray(int array) {
		glDisableVertexAttribArray(array);
	}
	
	public void delete() {
		for (VertexBufferObject vbo : vbos) {
			vbo.delete();
		}
		glDeleteVertexArrays(id);
	}
}
