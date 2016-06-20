package orbit.render;

import orbit.math.Matrix4f;
import orbit.math.Vector2f;

public class Camera {
	
	private Vector2f position;
	
	private float zoom = 1;
	
	public Camera() {
		position = new Vector2f();
	}
	
	public void update(float dt) {
		zoom += dt;
	}
	
	public void setPos(Vector2f pos) {
		this.position = pos;
	}
	
	public void setZoom(float zoom) {
		this.zoom = zoom;
	}
	
	public Vector2f getPos() {
		return position;
	}
	
	public float getZoom() {
		return zoom;
	}
	
	public Matrix4f getViewMatrix() {
		Vector2f negativePos = new Vector2f();
		Vector2f.copy(position, negativePos);
		negativePos.x *= -1;
		negativePos.y *= -1;
		
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(negativePos, matrix, matrix);
		
		return matrix;
	}
}
