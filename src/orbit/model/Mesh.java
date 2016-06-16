package orbit.model;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {
	
	private int vaoId;
	private int numVertices;
	
	public Mesh(int vaoId, int numVertices) {
		this.vaoId = vaoId;
		this.numVertices = numVertices;
	}
	
	public void bind() {
		glBindVertexArray(vaoId);
	}
	
	public void unbind() {
		glBindVertexArray(0);
	}
	
	public int getNumVertices() {
		return numVertices;
	}
}
