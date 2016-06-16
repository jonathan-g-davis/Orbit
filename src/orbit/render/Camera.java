package orbit.render;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import orbit.input.CursorHandler;
import orbit.input.KeyHandler;
import orbit.math.MathHelper;
import orbit.math.Matrix4f;
import orbit.math.Vector3f;

public class Camera {
	
	private Vector3f position;
	private float pitch;
	private float yaw;
	private float roll;
	
	private double mouseX = 0;
	private double mouseY = 0;
	
	public Camera() {
		position = new Vector3f();
	}
	
	public void update(float dt) {
		double dx = CursorHandler.getXpos() - mouseX;
		double dy = CursorHandler.getYpos() - mouseY;
		mouseX = CursorHandler.getXpos();
		mouseY = CursorHandler.getYpos();
		
		yaw += dx / 10;
		pitch = (float) MathHelper.clamp_d(pitch + dy / 10, -90, 90);
		
		if (KeyHandler.isKeyDown(GLFW_KEY_W)) {
			position.z -= Math.cos(Math.toRadians(yaw)) * dt;
			position.x += Math.sin(Math.toRadians(yaw)) * dt;
		}
		if (KeyHandler.isKeyDown(GLFW_KEY_S)) {
			position.z += Math.cos(Math.toRadians(yaw)) * dt;
			position.x -= Math.sin(Math.toRadians(yaw)) * dt;
		}
		if (KeyHandler.isKeyDown(GLFW_KEY_A)) {
			position.z -= Math.sin(Math.toRadians(yaw)) * dt;
			position.x -= Math.cos(Math.toRadians(yaw)) * dt;
		}
		if (KeyHandler.isKeyDown(GLFW_KEY_D)) {
			position.z += Math.sin(Math.toRadians(yaw)) * dt;
			position.x += Math.cos(Math.toRadians(yaw)) * dt;
		}
		if (KeyHandler.isKeyDown(GLFW_KEY_SPACE)) {
			position.y += dt;
		}
		if (KeyHandler.isKeyDown(GLFW_KEY_LEFT_CONTROL)) {
			position.y -= dt;
		}
	}
	
	public void setPos(Vector3f pos) {
		this.position = pos;
	}
	
	public Vector3f getPos() {
		return position;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public float getRoll() {
		return roll;
	}
	
	public Matrix4f getViewMatrix() {
		Vector3f negativePos = new Vector3f();
		Vector3f.copy(position, negativePos);
		negativePos.x *= -1;
		negativePos.y *= -1;
		negativePos.z *= -1;
		
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.rotate(pitch, yaw, roll, matrix, matrix);
		Matrix4f.translate(negativePos, matrix, matrix);
		
		return matrix;
	}
}
