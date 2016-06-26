package orbit.entity;

import orbit.math.Circle;
import orbit.math.Vector2f;
import orbit.model.Mesh;
import orbit.texture.Texture;

public abstract class DynamicObject extends Entity {
	
	private Circle bounds;
	
	protected float health = 100;
	protected Vector2f netForce = new Vector2f();
	protected Vector2f acceleration = new Vector2f();
	protected Vector2f velocity = new Vector2f();
	protected float mass;
	protected float netTorque = 0;
	protected float angAcceleration = 0;
	protected float angVelocity = 0;
	protected float momentOfInertia;
	
	public DynamicObject(Mesh mesh, Texture texture, float collisionRadius, float mass) {
		super(mesh, texture);
		this.bounds = new Circle(position.x, position.y, collisionRadius);
		this.mass = mass;
		this.momentOfInertia = (2.0f/5.0f) * mass * collisionRadius * collisionRadius;
	}
	
	public void checkCollision(DynamicObject other) {
		if (this == other) return;
		if (other instanceof Projectile) {
			if (!((Projectile) other).isActive()) return;
		}
		
		float distance = Vector2f.sub(other.position, position, null).magnitude();
		boolean collide = distance < getRadius() || distance < other.getRadius();
		if (collide) {
			if (other instanceof Projectile) {
				this.health -= ((Projectile) other).getDamage();
				other.setHealth(0);
			} else {
				this.health = 0;
				other.health = 0;
			}
		}
	}
	
	public void applyForce(Vector2f force) {
		Vector2f.add(force, netForce, netForce);
	}
	
	public void applyAcceleration(Vector2f accel) {
		Vector2f.add(accel, acceleration, acceleration);
	}
	
	public void applyTorque(float newton_meters) {
		netTorque += newton_meters;
	}
	
	public void applyAngAcceleration(float rad_sec2) {
		angVelocity += rad_sec2;
	}
	
	public void update(float dt) {
		Vector2f accel = new Vector2f(netForce.x / mass, netForce.y / mass);
		applyAcceleration(accel);
		netForce.x = 0;
		netForce.y = 0;
		
		Vector2f.add(Vector2f.mul(dt, acceleration, null), velocity, velocity);
		acceleration.x = 0;
		acceleration.y = 0;
		Vector2f.add(Vector2f.mul(dt, velocity, null), position, position);
		
		float angAccel = netTorque / momentOfInertia;
		applyAngAcceleration(angAccel);
		netTorque = 0;

		angVelocity += angAcceleration * dt;
		angAcceleration = 0;
		rotation += Math.toDegrees(angVelocity * dt);
		rotation = rotation % 360f;
		
		bounds.setX(position.x);
		bounds.setY(position.y);
	}
	
	public float getHealth() {
		return health;
	}
	
	public Vector2f getNetForce() {
		return netForce;
	}
	
	public Vector2f getAcceleration() {
		return acceleration;
	}
	
	public Vector2f getVelocity() {
		return velocity;
	}
	
	public float getNetTorque() {
		return netTorque;
	}
	
	public float getAngAcceleration() {
		return angAcceleration;
	}
	
	public float getAngVelocity() {
		return angVelocity;
	}
	
	public float getRadius() {
		return bounds.getRadius();
	}
	
	public Circle getBounds() {
		return bounds;
	}
	
	public float getMass() {
		return mass;
	}
	
	public float getMomentOfInertia() {
		return momentOfInertia;
	}
	
	@Override
	public void setPosition(Vector2f position) {
		bounds.setX(position.x);
		bounds.setY(position.y);
		super.setPosition(position);
	}
	
	public void setHealth(float health) {
		this.health = health;
	}
}
