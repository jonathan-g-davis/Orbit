package orbit.render;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;

import org.lwjgl.opengl.GL;

import orbit.entity.Entity;
import orbit.math.MathHelper;
import orbit.math.Matrix4f;
import orbit.model.Material;
import orbit.model.Mesh;
import orbit.render.shader.StaticShader;

public class Renderer {

	private Matrix4f projectionMatrix;
	
	public void init() {
		GL.createCapabilities();
		
		projectionMatrix = Matrix4f.perspective(120, 1280f/720f, 0.1f, 100);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glFrontFace(GL_CCW);
		
		glEnable(GL_DEPTH_TEST);
	}
	
	public void clear() {
		glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
	}
	
	public void render(Entity entity, Camera camera, StaticShader shader) {
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadProjectionMatrix(projectionMatrix);
		Mesh mesh = entity.getMesh();
		Matrix4f transformationMatrix = MathHelper.createTransformationMatrix(entity.getPos(), entity.getPitch(), entity.getYaw(), 
				entity.getRoll(), entity.getScale());
		shader.loadModelMatrix(transformationMatrix);
		Material material = entity.getMaterial();
		shader.setMaterial(material);
		shader.setCameraPosition(camera.getPos());
		
		mesh.bind();
		material.bind();
		shader.enableAttribute(0);
		shader.enableAttribute(1);
		shader.enableAttribute(2);
		
		glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
		
		shader.disableAttribute(0);
		shader.disableAttribute(1);
		shader.disableAttribute(2);
		mesh.unbind();
		material.unbind();
		
		shader.stop();
	}
	
	public void dispose() {
		
	}
}
