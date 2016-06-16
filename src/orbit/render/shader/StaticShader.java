package orbit.render.shader;

import orbit.math.Matrix4f;
import orbit.render.Camera;

public class StaticShader extends ShaderProgram {
	
	protected final static String VERTEX_SHADER = "shader.vert";
	protected final static String FRAGMENT_SHADER = "shader.frag";
	
	private int modelMatrix_location;
	private int viewMatrix_location;
	private int projectionMatrix_location;
	
	public StaticShader() {
		super(VERTEX_SHADER, FRAGMENT_SHADER);
	}
	
	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
		bindAttribute(1, "texcoord");
	}
	
	@Override
	protected void getUniformLocations() {
		modelMatrix_location = getUniform("modelMatrix");
		viewMatrix_location = getUniform("viewMatrix");
		projectionMatrix_location = getUniform("projectionMatrix");
	}
	
	public void loadProjectionMatrix(Matrix4f projectionMatrix) {
		setUniform(projectionMatrix_location, projectionMatrix);
	}
	
	public void loadViewMatrix(Camera camera) {
		setUniform(viewMatrix_location, camera.getViewMatrix());
	}
	
	public void loadModelMatrix(Matrix4f modelMatrix) {
		setUniform(modelMatrix_location, modelMatrix);
	}
}
