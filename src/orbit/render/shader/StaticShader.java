package orbit.render.shader;

import orbit.math.Matrix4f;
import orbit.math.Vector3f;
import orbit.model.Material;
import orbit.render.Camera;

public class StaticShader extends ShaderProgram {
	
	protected final static String VERTEX_SHADER = "shader.vert";
	protected final static String FRAGMENT_SHADER = "shader.frag";
	
	private int modelMatrix_location;
	private int viewMatrix_location;
	private int projectionMatrix_location;
	
	private int viewPos_location;
	
	private int matDiffuse_location;
	private int matSpecular_location;
	private int matShininess_location;
	
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
		
		viewPos_location = getUniform("viewPos");
		
		matDiffuse_location = getUniform("material.diffuse");
		matSpecular_location = getUniform("material.specular");
		matShininess_location = getUniform("material.shininess");
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
	
	public void setCameraPosition(Vector3f pos) {
		setUniform(viewPos_location, pos);
	}
	
	public void setMaterial(Material material) {
		setUniform(matDiffuse_location, 0);
		setUniform(matSpecular_location, 1);
		setUniform(matShininess_location, material.getShininess());
	}
}
