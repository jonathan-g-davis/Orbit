package orbit.model;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import orbit.math.Vector2f;
import orbit.render.VertexArrayObject;
import orbit.render.VertexBufferObject;
import orbit.texture.Texture;

public class MeshLoader {

	private static List<VertexArrayObject> vaos = new ArrayList<VertexArrayObject>();
	
	public static Mesh createQuadFromTopLeft(Texture texture) {
		return createQuadFromTopLeft(new Vector2f(texture.getWidth(), texture.getHeight()));
	}
	
	public static Mesh createQuadFromTopLeft(Vector2f size) {
		return createQuad(new Vector2f(0, 0), size);
	}
	
	public static Mesh createQuadFromCenter(Texture texture) {
		return createQuadFromCenter(new Vector2f(texture.getWidth(), texture.getHeight()));
	}
	
	public static Mesh createQuadFromCenter(Vector2f size) {
		float[] vertices = {
				-size.x / 2, -size.y / 2,
				size.x / 2, -size.y / 2,
				-size.x / 2, size.y / 2,
				size.x / 2, size.y / 2
		};
		
		float[] texcoords = {
				0, 0,
				1, 0,
				0, 1,
				1, 1
		};
		
		int[] indices = {
				0, 2, 1,
				1, 2, 3
		};
		
		return createMesh(vertices, texcoords, indices);
	}
	
	public static Mesh createQuad(Vector2f topLeft, Vector2f bottomRight) {
		float[] vertices = {
				topLeft.x, topLeft.y,
				bottomRight.x, topLeft.y,
				topLeft.x, bottomRight.y,
				bottomRight.x, bottomRight.y
		};
		
		float[] texcoords = {
				0, 0,
				1, 0,
				0, 1,
				1, 1
		};
		
		int[] indices = {
				0, 2, 1,
				1, 2, 3
		};
		
		return createMesh(vertices, texcoords, indices);
	}
	
	public static Mesh createMesh(float[] vertices, int[] indices) {
		VertexArrayObject vao = new VertexArrayObject();
		vaos.add(vao);
		
		vao.bind();
		VertexBufferObject vertexVBO, indexVBO;
		vertexVBO = storeAttribute(0, 2, vertices);
		indexVBO = storeIndices(indices);
		vao.addVBO(vertexVBO);
		vao.addVBO(indexVBO);
		vao.unbind();
		
		Mesh mesh = new Mesh(vao.getId(), indices.length);
		return mesh;
	}
	
	public static Mesh createMesh(float[] vertices, float[] texcoords, int[] indices) {
		VertexArrayObject vao = new VertexArrayObject();
		vaos.add(vao);
		
		vao.bind();
		VertexBufferObject vertexVBO, texVBO, indicesVBO;
		vertexVBO = storeAttribute(0, 2, vertices);
		texVBO = storeAttribute(1, 2, texcoords);
		indicesVBO = storeIndices(indices);
		vao.addVBO(vertexVBO);
		vao.addVBO(texVBO);
		vao.addVBO(indicesVBO);
		vao.unbind();
		
		Mesh texturedModel = new Mesh(vao.getId(), indices.length);
		return texturedModel;
	}
	
	private static VertexBufferObject storeAttribute(int attributeId, int dataSize, float[] data) {
		VertexBufferObject vbo = new VertexBufferObject();
		vbo.bind(GL_ARRAY_BUFFER);
		vbo.uploadData(GL_ARRAY_BUFFER, convertToBuffer(data), GL_STATIC_DRAW);
		glVertexAttribPointer(attributeId, dataSize, GL_FLOAT, false, 0, 0);
		vbo.unbind(GL_ARRAY_BUFFER);
		
		return vbo;
	}
	
	private static VertexBufferObject storeIndices(int[] indices) {
		VertexBufferObject vbo = new VertexBufferObject();
		vbo.bind(GL_ELEMENT_ARRAY_BUFFER);
		vbo.uploadData(GL_ELEMENT_ARRAY_BUFFER, convertToBuffer(indices), GL_STATIC_DRAW);
		
		return vbo;
		
	}
	
	private static FloatBuffer convertToBuffer(float[] array) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		return buffer;
	}
	
	private static IntBuffer convertToBuffer(int[] array) {
		IntBuffer buffer = BufferUtils.createIntBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		return buffer;
	}
	
	public static void dispose() {
		for (VertexArrayObject vao : vaos) {
			vao.delete();
		}
	}
}
