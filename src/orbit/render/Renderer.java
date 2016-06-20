package orbit.render;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
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
import orbit.model.Mesh;
import orbit.render.shader.StaticShader;

public class Renderer {

	private Matrix4f projectionMatrix;
	private StaticShader shader;
	private Camera camera;
	
	public void init() {
		GL.createCapabilities();
		
		projectionMatrix = Matrix4f.orthographic(1280, 720);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glFrontFace(GL_CCW);
	}
	
	public void setStaticShader(StaticShader shader) {
		this.shader = shader;
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void clear() {
		glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
	}
	
	public void render(Entity entity) {
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadProjectionMatrix(projectionMatrix);
		Mesh mesh = entity.getMesh();
		entity.getTexture().bind();
		Matrix4f transformationMatrix = MathHelper.createTransformationMatrix(entity.getPos(), entity.getRotation(), entity.getScale());
		shader.loadModelMatrix(transformationMatrix);
		
		mesh.bind();
		shader.enableAttribute(0);
		shader.enableAttribute(1);
		
		glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
		
		shader.disableAttribute(0);
		shader.disableAttribute(1);
		entity.getTexture().unbind();
		mesh.unbind();
		
		shader.stop();
	}
	
	public void dispose() {
		
	}
}
