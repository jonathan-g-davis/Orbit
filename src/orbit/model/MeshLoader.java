package orbit.model;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.BufferUtils;

import orbit.math.Vector2f;
import orbit.math.Vector3f;
import orbit.render.AssetLoader;
import orbit.render.VertexArrayObject;
import orbit.render.VertexBufferObject;
import orbit.util.FileUtils;

public class MeshLoader {

	private static List<VertexArrayObject> vaos = new ArrayList<VertexArrayObject>();
	private static Map<String, Mesh> meshes = new HashMap<String, Mesh>();
	
	public static Mesh loadMesh(String file) {
		if (meshes.containsKey(file)) return meshes.get(file);
		
		List<String> lines = FileUtils.readAllLines("res/obj/" + file);
		
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textureCoords = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Face> faces = new ArrayList<Face>();
		
		for (String line : lines) {
			String[] tokens = line.split("\\s+");
			switch(tokens[0]) {
			case "v":
				Vector3f vertex = new Vector3f();
				vertex.x = Float.parseFloat(tokens[1]);
				vertex.y = Float.parseFloat(tokens[2]);
				vertex.z = Float.parseFloat(tokens[3]);
				vertices.add(vertex);
				break;
				
			case "vt":
				Vector2f textureCoord = new Vector2f();
				textureCoord.x = Float.parseFloat(tokens[1]);
				textureCoord.y = Float.parseFloat(tokens[2]);
				textureCoords.add(textureCoord);
				break;
				
			case "vn":
				Vector3f normal = new Vector3f();
				normal.x = Float.parseFloat(tokens[1]);
				normal.y = Float.parseFloat(tokens[2]);
				normal.z = Float.parseFloat(tokens[3]);
				normals.add(normal);
				break;
				
			case "f":
				Face face = new Face(tokens[1], tokens[2], tokens[3]);
				faces.add(face);
				break;
				
			default:
					
				break;
			}
		}
		
		Mesh mesh = null;
		try {
			mesh = createMeshFromLists(vertices, textureCoords, normals, faces);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		meshes.put(file, mesh);
		return mesh;
	}
	
	private static Mesh createMeshFromLists(List<Vector3f> vertices, List<Vector2f> textureCoords, List<Vector3f> normals, List<Face> faces) throws Exception {
		if (textureCoords.size() > vertices.size()) {
			throw new Exception("More than one texture coord mapped to the same vertex!");
		}
		
		float[] vertexArray = new float[vertices.size() * 3];
		float[] textureCoordArray = new float[vertices.size() * 2];
		float[] normalArray = new float[vertices.size() * 3];
		int[] indexArray = new int[faces.size() * 3];
		
		int i = 0;
		for (Vector3f v : vertices) {
			vertexArray[i * 3] = v.x;
			vertexArray[i * 3 + 1] = v.y;
			vertexArray[i * 3 + 2] = v.z;
			i++;
		}
		
		int faceCount = 0;
		for (Face face : faces) {
			IndexGroup[] indexGroups = face.getIndexGroups();
			
			int index = indexGroups[0].indexPos;
			indexArray[faceCount * 3] = index;
			Vector2f textureCoord = textureCoords.get(indexGroups[0].indexTextureCoord);
			textureCoordArray[index * 2] = textureCoord.x;
			textureCoordArray[index * 2 + 1] = 1 - textureCoord.y;
			Vector3f normal = normals.get(indexGroups[0].indexNormal);
			normalArray[index * 3] = normal.x;
			normalArray[index * 3 + 1] = normal.y;
			normalArray[index * 3 + 2] = normal.z;
			
			index = indexGroups[1].indexPos;
			indexArray[faceCount * 3 + 1] = index;
			textureCoord = textureCoords.get(indexGroups[1].indexTextureCoord);
			textureCoordArray[index * 2] = textureCoord.x;
			textureCoordArray[index * 2 + 1] = 1 - textureCoord.y;
			normal = normals.get(indexGroups[1].indexNormal);
			normalArray[index * 3] = normal.x;
			normalArray[index * 3 + 1] = normal.y;
			normalArray[index * 3 + 2] = normal.z;
			
			index = indexGroups[2].indexPos;
			indexArray[faceCount * 3 + 2] = index;
			textureCoord = textureCoords.get(indexGroups[2].indexTextureCoord);
			textureCoordArray[index * 2] = textureCoord.x;
			textureCoordArray[index * 2 + 1] = 1 - textureCoord.y;
			normal = normals.get(indexGroups[2].indexNormal);
			normalArray[index * 3] = normal.x;
			normalArray[index * 3 + 1] = normal.y;
			normalArray[index * 3 + 2] = normal.z;
			
			faceCount++;
		}
		
		return createMesh(vertexArray, textureCoordArray, normalArray, indexArray);
	}
	
	public static Mesh createMesh(float[] vertices, int[] indices) {
		VertexArrayObject vao = new VertexArrayObject();
		vaos.add(vao);
		
		vao.bind();
		VertexBufferObject vertexVBO, indexVBO;
		vertexVBO = storeAttribute(0, 3, vertices);
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
		vertexVBO = storeAttribute(0, 3, vertices);
		texVBO = storeAttribute(1, 2, texcoords);
		indicesVBO = storeIndices(indices);
		vao.addVBO(vertexVBO);
		vao.addVBO(texVBO);
		vao.addVBO(indicesVBO);
		vao.unbind();
		
		Mesh texturedModel = new Mesh(vao.getId(), indices.length);
		return texturedModel;
	}
	
	public static Mesh createMesh(float[] vertices, float[] texcoords, float[] normals, int[] indices) {
		VertexArrayObject vao = new VertexArrayObject();
		vaos.add(vao);
		
		vao.bind();
		VertexBufferObject vertexVBO, texVBO, normalVBO, indicesVBO;
		vertexVBO = storeAttribute(0, 3, vertices);
		texVBO = storeAttribute(1, 2, texcoords);
		normalVBO = storeAttribute(2, 3, normals);
		indicesVBO = storeIndices(indices);
		vao.addVBO(vertexVBO);
		vao.addVBO(texVBO);
		vao.addVBO(normalVBO);
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
	
	protected static class IndexGroup {
		
		public static final int NO_VALUE = -1;
		
		public int indexPos = NO_VALUE;
		public int indexTextureCoord = NO_VALUE;
		public int indexNormal = NO_VALUE;
	}
	
	protected static class Face {
		
		private IndexGroup[] indexGroups = new IndexGroup[3];
		
		public Face(String v1, String v2, String v3) {
			this.indexGroups[0] = parseGroup(v1);
			this.indexGroups[1] = parseGroup(v2);
			this.indexGroups[2] = parseGroup(v3);
		}
		
		private IndexGroup parseGroup(String indexGroup) {
			String[] indexes = indexGroup.split("/");
			IndexGroup group = new IndexGroup();
			group.indexPos = Integer.parseInt(indexes[0]) - 1;
			group.indexTextureCoord = Integer.parseInt(indexes[1]) - 1;
			group.indexNormal = Integer.parseInt(indexes[2]) - 1;
			return group;
		}
		
		public IndexGroup[] getIndexGroups() {
			return indexGroups;
		}
	}
	
	public static void dispose() {
		for (VertexArrayObject vao : vaos) {
			vao.delete();
		}
	}
}
