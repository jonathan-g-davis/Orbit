package orbit.render.shader;

import java.awt.Color;

import orbit.math.Vector3f;

public class LightShader extends StaticShader {
	
	protected static final String VERTEX_SHADER = "lightshader.vert";
	protected static final String FRAGMENT_SHADER = "lightshader.frag";
	
	private int lightColor_location;
	
	public LightShader() {
		super();
	}
	
	@Override
	protected void getUniformLocations() {
		super.getUniformLocations();
		lightColor_location = getUniform("lightColor");
	}
	
	public void setLightColor(Vector3f color) {
		setUniform(lightColor_location, color);
	}
	
	public void setLightColor(Color color) {
		setLightColor(new Vector3f(color.getRed(), color.getGreen(), color.getBlue()));
	}
}
