package orbit.render;

import orbit.input.CursorHandler;
import orbit.math.Matrix4f;
import orbit.math.Vector2f;

public class Camera {
	
	private Vector2f position;
	
	private double mouseX = 0;
	private double mouseY = 0;
	
	public Camera() {
		position = new Vector2f();
	}
	
	public void update(float dt) {
		double dx = CursorHandler.getXpos() - mouseX;
		double dy = CursorHandler.getYpos() - mouseY;
		mouseX = CursorHandler.getXpos();
		mouseY = CursorHandler.getYpos();
	}
	
	public void setPos(Vector2f pos) {
		this.position = pos;
	}
	
	public Vector2f getPos() {
		return position;
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
