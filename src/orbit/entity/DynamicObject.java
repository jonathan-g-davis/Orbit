package orbit.entity;

import orbit.math.Vector2f;
import orbit.model.Mesh;
import orbit.render.Renderer;
import orbit.texture.Texture;

public abstract class DynamicObject extends Entity {
	
	private int width;
	private int height;
	
	private Vector2f netForce = new Vector2f();
	private Vector2f velocity = new Vector2f();
	private float mass;
	private float netTorque = 0;
	private float angVelocity = 0;
	private float momentOfInertia;
	
	public DynamicObject(Mesh mesh, Texture texture, int width, int height, float mass) {
		super(mesh, texture);
		this.width = width;
		this.height = height;
		this.mass = mass;
		this.momentOfInertia = (2.0f/5.0f) * mass * (width * width + height * height);
	}
	
	public void applyForce(Vector2f force) {
		Vector2f.add(force, netForce, netForce);
	}
	
	public void applyAcceleration(Vector2f accel, float dt) {
		Vector2f.add(Vector2f.mul(dt, accel, null), velocity, velocity);
	}
	
	public void applyTorque(float newton_meters) {
		netTorque += newton_meters;
	}
	
	public void applyAngAcceleration(float rad_sec2, float dt) {
		angVelocity += rad_sec2 * dt;
	}
	
	public void update(float dt) {
		Vector2f acceleration = new Vector2f(netForce.x / mass, netForce.y / mass);
		netForce.x = 0;
		netForce.y = 0;
		Vector2f.add(Vector2f.mul(dt, acceleration, null), velocity, velocity);
		Vector2f.add(Vector2f.mul(dt, velocity, null), position, position);
		
		float angAcceleration = netTorque / momentOfInertia;
		angVelocity += angAcceleration * dt;
		rotation += Math.toDegrees(angVelocity * dt);
		rotation = rotation % 360f;
	}
	
	public void render(Renderer renderer) {
		renderer.render(this);
	}
	
	public Vector2f getNetForce() {
		return netForce;
	}
	
	public Vector2f getVelocity() {
		return velocity;
	}
	
	public float getNetTorque() {
		return netTorque;
	}
	
	public float getAngVelocity() {
		return angVelocity;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float getMass() {
		return mass;
	}
}
